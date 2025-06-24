package com.qaid.hrms.service.impl;

import com.qaid.hrms.exception.BadRequestException;
import com.qaid.hrms.exception.ResourceNotFoundException;
import com.qaid.hrms.model.dto.request.TravelRequest;
import com.qaid.hrms.model.dto.response.TravelResponse;
import com.qaid.hrms.model.entity.Employee;
import com.qaid.hrms.model.entity.Travel;
import com.qaid.hrms.repository.EmployeeRepository;
import com.qaid.hrms.repository.TravelRepository;
import com.qaid.hrms.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {

    @Autowired
    private TravelRepository travelRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public TravelResponse createTravel(TravelRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        // Validate date range
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new BadRequestException("Start date cannot be after end date");
        }

        Travel travel = new Travel();
        travel.setEmployee(employee);
        travel.setTravelType(request.getTravelType());
        travel.setPurpose(request.getPurpose());
        travel.setStartDate(request.getStartDate());
        travel.setEndDate(request.getEndDate());
        travel.setDestination(request.getDestination());
        travel.setModeOfTravel(request.getModeOfTravel());
        travel.setEstimatedCost(request.getEstimatedCost());
        travel.setAdvanceAmount(request.getAdvanceAmount());
        travel.setComments(request.getComments());
        travel.setStatus("Pending");

        return mapToResponse(travelRepository.save(travel));
    }

    @Override
    public List<TravelResponse> getAllTravels() {
        return travelRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    @Override
    public TravelResponse updateTravelByEmployeeId(String employeeId, TravelRequest request) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Travel> travels = travelRepository.findByEmployeeId(employee.getId());
        if (travels.isEmpty()) {
            throw new ResourceNotFoundException("No travel records found for employee");
        }
        Travel travel = travels.get(0); // For demo, update the first travel
        if (request.getTravelType() != null) {
            travel.setTravelType(request.getTravelType());
        }
        if (request.getPurpose() != null) {
            travel.setPurpose(request.getPurpose());
        }
        if (request.getStartDate() != null) {
            travel.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            travel.setEndDate(request.getEndDate());
        }
        if (request.getDestination() != null) {
            travel.setDestination(request.getDestination());
        }
        if (request.getModeOfTravel() != null) {
            travel.setModeOfTravel(request.getModeOfTravel());
        }
        if (request.getEstimatedCost() != null) {
            travel.setEstimatedCost(request.getEstimatedCost());
        }
        if (request.getAdvanceAmount() != null) {
            travel.setAdvanceAmount(request.getAdvanceAmount());
        }
        if (request.getComments() != null) {
            travel.setComments(request.getComments());
        }
        return mapToResponse(travelRepository.save(travel));
    }

    @Override
    public void deleteTravelByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Travel> travels = travelRepository.findByEmployeeId(employee.getId());
        for (Travel travel : travels) {
            if (!travel.getStatus().equals("Pending")) {
                throw new BadRequestException("Cannot delete approved or rejected travel request");
            }
            travelRepository.deleteById(travel.getId());
        }
    }

    @Override
    public TravelResponse getTravelByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Travel> travels = travelRepository.findByEmployeeId(employee.getId());
        if (travels.isEmpty()) {
            throw new ResourceNotFoundException("No travel records found for employee");
        }
        return mapToResponse(travels.get(0)); // For demo, return the first travel
    }

    @Override
    public List<TravelResponse> getTravelsByEmployee(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return travelRepository.findByEmployeeId(employee.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TravelResponse> getTravelsByDateRange(LocalDate startDate, LocalDate endDate) {
        return travelRepository.findByStartDateBetweenOrEndDateBetween(startDate, endDate, startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TravelResponse> getTravelsByEmployeeAndDateRange(String employeeId, LocalDate startDate, LocalDate endDate) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return travelRepository.findByEmployeeIdAndStartDateBetweenOrEndDateBetween(
                employee.getId(), startDate, endDate, startDate, endDate)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TravelResponse approveTravelByEmployeeId(String employeeId, String comments) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Travel> travels = travelRepository.findByEmployeeId(employee.getId());
        if (travels.isEmpty()) {
            throw new ResourceNotFoundException("No travel records found for employee");
        }
        Travel travel = travels.get(0); // For demo, approve the first travel
        if (!travel.getStatus().equals("Pending")) {
            throw new BadRequestException("Travel request is not in pending status");
        }
        travel.setStatus("Approved");
        travel.setComments(comments);
        return mapToResponse(travelRepository.save(travel));
    }

    @Override
    public TravelResponse rejectTravelByEmployeeId(String employeeId, String comments) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Travel> travels = travelRepository.findByEmployeeId(employee.getId());
        if (travels.isEmpty()) {
            throw new ResourceNotFoundException("No travel records found for employee");
        }
        Travel travel = travels.get(0); // For demo, reject the first travel
        if (!travel.getStatus().equals("Pending")) {
            throw new BadRequestException("Travel request is not in pending status");
        }
        travel.setStatus("Rejected");
        travel.setComments(comments);
        return mapToResponse(travelRepository.save(travel));
    }

    @Override
    public TravelResponse updateActualCostByEmployeeId(String employeeId, BigDecimal actualCost, String comments) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        List<Travel> travels = travelRepository.findByEmployeeId(employee.getId());
        if (travels.isEmpty()) {
            throw new ResourceNotFoundException("No travel records found for employee");
        }
        Travel travel = travels.get(0); // For demo, update the first travel
        if (!travel.getStatus().equals("Approved")) {
            throw new BadRequestException("Travel request must be approved before updating actual cost");
        }
        travel.setActualCost(actualCost);
        travel.setComments(comments);
        return mapToResponse(travelRepository.save(travel));
    }

    private TravelResponse mapToResponse(Travel travel) {
        TravelResponse response = new TravelResponse();
        response.setId(travel.getId());
        response.setEmployeeId(travel.getEmployee().getEmployeeId());
        response.setEmployeeName(travel.getEmployee().getFirstName() + " " + travel.getEmployee().getLastName());
        response.setTravelType(travel.getTravelType());
        response.setPurpose(travel.getPurpose());
        response.setStartDate(travel.getStartDate());
        response.setEndDate(travel.getEndDate());
        response.setDestination(travel.getDestination());
        response.setModeOfTravel(travel.getModeOfTravel());
        response.setEstimatedCost(travel.getEstimatedCost());
        response.setActualCost(travel.getActualCost());
        response.setAdvanceAmount(travel.getAdvanceAmount());
        response.setStatus(travel.getStatus());
        response.setComments(travel.getComments());
        response.setCreatedAt(travel.getCreatedAt());
        response.setUpdatedAt(travel.getUpdatedAt());
        return response;
    }
}