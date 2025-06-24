package com.qaid.hrms.service.impl;

import com.qaid.hrms.exception.ResourceNotFoundException;
import com.qaid.hrms.model.dto.request.DepartmentRequestDTO;
import com.qaid.hrms.model.dto.response.DepartmentResponseDTO;
import com.qaid.hrms.model.dto.request.DesignationRequestDTO;
import com.qaid.hrms.model.dto.response.DesignationResponseDTO;
import com.qaid.hrms.model.entity.Department;
import com.qaid.hrms.model.entity.Designation;
import com.qaid.hrms.model.entity.Employee;
import com.qaid.hrms.repository.DepartmentRepository;
import com.qaid.hrms.repository.DesignationRepository;
import com.qaid.hrms.repository.EmployeeRepository;
import com.qaid.hrms.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private DesignationRepository designationRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    // --- Department Methods ---
    @Override
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto) {
        // Validate department code
        if (departmentRepository.existsByCode(dto.getCode())) {
            throw new ResourceNotFoundException("Department code already exists");
        }
        
        Department department = new Department();
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        department.setCode(dto.getCode());
        department.setLocation(dto.getLocation());
        department.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        department.setCreatedAt(LocalDate.now());
        department.setUpdatedAt(LocalDate.now());
        
        // Set parent department if provided
        if (dto.getParentDepartmentId() != null) {
            Department parentDepartment = departmentRepository.findById(dto.getParentDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent department not found"));
            department.setParentDepartment(parentDepartment);
        }
        
        // Set manager if provided
        if (dto.getManagerId() != null) {
            Employee manager = employeeRepository.findById(dto.getManagerId())
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));
            department.setManager(manager);
        }
        
        department = departmentRepository.save(department);
        return convertToDepartmentResponseDTO(department);
    }

    @Override
    public DepartmentResponseDTO updateDepartmentByAllocatedId(String departmentId, DepartmentRequestDTO dto) {
        Department department = departmentRepository.findById(Long.parseLong(departmentId))
            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            
        // Validate department code if changed
        if (!department.getCode().equals(dto.getCode()) && departmentRepository.existsByCode(dto.getCode())) {
            throw new ResourceNotFoundException("Department code already exists");
        }
        
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        department.setCode(dto.getCode());
        department.setLocation(dto.getLocation());
        department.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : department.getIsActive());
        department.setUpdatedAt(LocalDate.now());
        
        // Update parent department if changed
        if (dto.getParentDepartmentId() != null && 
            (department.getParentDepartment() == null || 
             !department.getParentDepartment().getId().equals(dto.getParentDepartmentId()))) {
            Department parentDepartment = departmentRepository.findById(dto.getParentDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent department not found"));
            department.setParentDepartment(parentDepartment);
        }
        
        // Update manager if changed
        if (dto.getManagerId() != null && 
            (department.getManager() == null || 
             !department.getManager().getId().equals(dto.getManagerId()))) {
            Employee manager = employeeRepository.findById(dto.getManagerId())
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));
            department.setManager(manager);
        }
        
        department = departmentRepository.save(department);
        return convertToDepartmentResponseDTO(department);
    }

    @Override
    public DepartmentResponseDTO getDepartmentByAllocatedId(String departmentId) {
        Department department = departmentRepository.findById(Long.parseLong(departmentId))
            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return convertToDepartmentResponseDTO(department);
    }

    @Override
    public Page<DepartmentResponseDTO> getAllDepartments(Pageable pageable) {
        return departmentRepository.findByIsActive(true, pageable)
            .map(this::convertToDepartmentResponseDTO);
    }

    @Override
    public List<DepartmentResponseDTO> getDepartmentHierarchy() {
        List<Department> rootDepartments = departmentRepository.findRootDepartments();
        return rootDepartments.stream()
            .map(this::convertToDepartmentHierarchy)
            .collect(Collectors.toList());
    }

    private DepartmentResponseDTO convertToDepartmentHierarchy(Department department) {
        DepartmentResponseDTO dto = convertToDepartmentResponseDTO(department);
        // Recursively set subDepartments
        List<Department> subDepartments = departmentRepository.findSubDepartments(department.getId());
        dto.setSubDepartments(subDepartments.stream()
            .map(this::convertToDepartmentHierarchy)
            .collect(Collectors.toList()));
        // Set designations
        List<Designation> designations = designationRepository.findByDepartment(department.getId());
        dto.setDesignations(designations.stream()
            .map(this::convertToDesignationResponseDTO)
            .collect(Collectors.toList()));
        return dto;
    }

    @Override
    public Object getOrganizationChart() {
        List<Department> rootDepartments = departmentRepository.findRootDepartments();
        Map<String, Object> chart = new HashMap<>();
        chart.put("departments", rootDepartments.stream()
            .map(this::convertToDepartmentHierarchy)
            .collect(Collectors.toList()));
        return chart;
    }

    @Override
    public Object getDepartmentStructure(String departmentId) {
        Department department = departmentRepository.findById(Long.parseLong(departmentId))
            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        Map<String, Object> structure = new HashMap<>();
        structure.put("department", convertToDepartmentResponseDTO(department));
        structure.put("subDepartments", getSubDepartments(departmentId));
        structure.put("designations", getDepartmentDesignations(departmentId));
        structure.put("employees", employeeRepository.findByDepartment(department).stream()
            .map(employee -> {
                Map<String, Object> emp = new HashMap<>();
                emp.put("id", employee.getId());
                emp.put("name", employee.getFirstName() + " " + employee.getLastName());
                emp.put("designation", employee.getDesignation().getTitle());
                emp.put("email", employee.getEmail());
                emp.put("phone", employee.getPhone());
                return emp;
            })
            .collect(Collectors.toList()));
        return structure;
    }

    @Override
    public Object getDepartmentStatistics(String departmentId) {
        Department department = departmentRepository.findById(Long.parseLong(departmentId))
            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalEmployees", employeeRepository.countByDepartment(department));
        stats.put("activeEmployees", employeeRepository.countByDepartmentAndIsActive(department, true));
        stats.put("designations", designationRepository.countByDepartment(Long.parseLong(departmentId)));
        stats.put("subDepartments", departmentRepository.countSubDepartments(Long.parseLong(departmentId)));
        return stats;
    }

    @Override
    public Object getOrganizationStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDepartments", departmentRepository.count());
        stats.put("activeDepartments", departmentRepository.countByIsActive(true));
        stats.put("totalEmployees", employeeRepository.count());
        stats.put("activeEmployees", employeeRepository.countByIsActive(true));
        stats.put("totalDesignations", designationRepository.count());
        return stats;
    }

    @Override
    public Object getEmployeeDistributionByDepartment() {
        return departmentRepository.findAll().stream()
            .map(dept -> {
                Map<String, Object> dist = new HashMap<>();
                dist.put("departmentId", dept.getId());
                dist.put("departmentName", dept.getName());
                dist.put("employeeCount", employeeRepository.countByDepartment(dept));
                dist.put("activeEmployeeCount", employeeRepository.countByDepartmentAndIsActive(dept, true));
                return dist;
            })
            .collect(Collectors.toList());
    }

    @Override
    public Object getEmployeeDistributionByDesignation() {
        return designationRepository.findAll().stream()
            .map(desig -> {
                Map<String, Object> dist = new HashMap<>();
                dist.put("designationId", desig.getId());
                dist.put("designationTitle", desig.getTitle());
                dist.put("employeeCount", employeeRepository.countByDesignation(desig));
                dist.put("activeEmployeeCount", employeeRepository.countByDesignationAndIsActive(desig, true));
                return dist;
            })
            .collect(Collectors.toList());
    }

    // TODO: Replace all DepartmentDTO/DesignationDTO usage with DepartmentRequestDTO, DepartmentResponseDTO, DesignationRequestDTO, DesignationResponseDTO
    // and update method signatures and logic to match the OrganizationService interface.
    // --- Conversion Helpers ---
    private DepartmentResponseDTO convertToDepartmentResponseDTO(Department department) {
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        dto.setCode(department.getCode());
        dto.setLocation(department.getLocation());
        dto.setIsActive(department.getIsActive());
        dto.setCreatedAt(department.getCreatedAt());
        dto.setUpdatedAt(department.getUpdatedAt());
        if (department.getParentDepartment() != null) {
            dto.setParentDepartmentId(department.getParentDepartment().getId());
            dto.setParentDepartmentName(department.getParentDepartment().getName());
        }
        if (department.getManager() != null) {
            dto.setManagerId(department.getManager().getId());
            dto.setManagerName(department.getManager().getFirstName() + " " + department.getManager().getLastName());
        }
        // Set subDepartments and designations as needed
        return dto;
    }

    private DesignationResponseDTO convertToDesignationResponseDTO(Designation designation) {
        DesignationResponseDTO dto = new DesignationResponseDTO();
        dto.setId(designation.getId());
        dto.setTitle(designation.getTitle());
        dto.setDescription(designation.getDescription());
        dto.setLevel(designation.getLevel());
        dto.setCode(designation.getCode());
        dto.setIsActive(designation.getIsActive());
        dto.setCreatedAt(designation.getCreatedAt());
        dto.setUpdatedAt(designation.getUpdatedAt());
        if (designation.getDepartment() != null) {
            dto.setDepartmentId(designation.getDepartment().getId());
            dto.setDepartmentName(designation.getDepartment().getName());
        }
        return dto;
    }

    @Override
    public DesignationResponseDTO createDesignation(DesignationRequestDTO dto) {
        // Validate designation code
        if (designationRepository.existsByCode(dto.getCode())) {
            throw new ResourceNotFoundException("Designation code already exists");
        }
        
        Designation designation = new Designation();
        designation.setTitle(dto.getTitle());
        designation.setDescription(dto.getDescription());
        designation.setLevel(dto.getLevel());
        designation.setCode(dto.getCode());
        designation.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        designation.setCreatedAt(LocalDate.now());
        designation.setUpdatedAt(LocalDate.now());
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            designation.setDepartment(department);
        }
        designation = designationRepository.save(designation);
        return convertToDesignationResponseDTO(designation);
    }

    @Override
    public DesignationResponseDTO updateDesignationByAllocatedId(String designationId, DesignationRequestDTO dto) {
        Designation designation = designationRepository.findById(Long.parseLong(designationId))
            .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));
        designation.setTitle(dto.getTitle());
        designation.setDescription(dto.getDescription());
        designation.setLevel(dto.getLevel());
        designation.setCode(dto.getCode());
        designation.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : designation.getIsActive());
        designation.setUpdatedAt(LocalDate.now());
        if (dto.getDepartmentId() != null && (designation.getDepartment() == null || !designation.getDepartment().getId().equals(dto.getDepartmentId()))) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            designation.setDepartment(department);
        }
        designation = designationRepository.save(designation);
        return convertToDesignationResponseDTO(designation);
    }

    @Override
    public DesignationResponseDTO getDesignationByAllocatedId(String designationId) {
        Designation designation = designationRepository.findById(Long.parseLong(designationId))
            .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));
        return convertToDesignationResponseDTO(designation);
    }

    @Override
    public List<DesignationResponseDTO> getDepartmentDesignations(String departmentId) {
        List<Designation> designations = designationRepository.findByDepartment(Long.parseLong(departmentId));
        return designations.stream().map(this::convertToDesignationResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<DepartmentResponseDTO> getSubDepartments(String departmentId) {
        List<Department> subDepartments = departmentRepository.findSubDepartments(Long.parseLong(departmentId));
        return subDepartments.stream().map(this::convertToDepartmentResponseDTO).collect(Collectors.toList());
    }

    @Override
    public DepartmentResponseDTO assignManager(String departmentId, String employeeId) {
        Department department = departmentRepository.findById(Long.parseLong(departmentId))
            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        Employee manager = employeeRepository.findById(Long.parseLong(employeeId))
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        department.setManager(manager);
        department.setUpdatedAt(LocalDate.now());
        department = departmentRepository.save(department);
        return convertToDepartmentResponseDTO(department);
    }

    @Override
    public DepartmentResponseDTO updateDepartmentLocation(String departmentId, String location) {
        Department department = departmentRepository.findById(Long.parseLong(departmentId))
            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        department.setLocation(location);
        department.setUpdatedAt(LocalDate.now());
        department = departmentRepository.save(department);
        return convertToDepartmentResponseDTO(department);
    }

    @Override
    public void deactivateDepartment(String departmentId) {
        Department department = departmentRepository.findById(Long.parseLong(departmentId))
            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        if (employeeRepository.existsByDepartmentAndIsActive(department, true)) {
            throw new ResourceNotFoundException("Cannot deactivate department with active employees");
        }
        department.setIsActive(false);
        department.setUpdatedAt(LocalDate.now());
        departmentRepository.save(department);
    }
}