package com.qaid.hrms.service.impl;

import com.qaid.hrms.model.dto.request.SelfServicePortalRequest;
import com.qaid.hrms.model.dto.response.SelfServicePortalResponse;
import com.qaid.hrms.service.SelfServicePortalService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class SelfServicePortalServiceImpl implements SelfServicePortalService {
    // In-memory store for demonstration
    private final List<SelfServicePortalResponse> portalList = new ArrayList<>();
    private long idCounter = 1;

    @Override
    public SelfServicePortalResponse createSelfServicePortal(SelfServicePortalRequest request) {
        SelfServicePortalResponse response = new SelfServicePortalResponse();
        response.setId(idCounter++);
        response.setEmployeeId(request.getEmployeeId());
        response.setFeature(request.getFeature());
        response.setDescription(request.getDescription());
        portalList.add(response);
        return response;
    }

    @Override
    public SelfServicePortalResponse updateSelfServicePortalByEmployeeId(String employeeId, SelfServicePortalRequest request) {
        Long empId = Long.parseLong(employeeId);
        for (SelfServicePortalResponse portal : portalList) {
            if (portal.getEmployeeId().equals(empId)) {
                portal.setFeature(request.getFeature());
                portal.setDescription(request.getDescription());
                return portal;
            }
        }
        // If not found, create new
        SelfServicePortalResponse response = new SelfServicePortalResponse();
        response.setId(idCounter++);
        response.setEmployeeId(empId);
        response.setFeature(request.getFeature());
        response.setDescription(request.getDescription());
        portalList.add(response);
        return response;
    }

    @Override
    public void deleteSelfServicePortalByEmployeeId(String employeeId) {
        Long empId = Long.parseLong(employeeId);
        portalList.removeIf(portal -> portal.getEmployeeId().equals(empId));
    }

    @Override
    public SelfServicePortalResponse getSelfServicePortalByEmployeeId(String employeeId) {
        Long empId = Long.parseLong(employeeId);
        for (SelfServicePortalResponse portal : portalList) {
            if (portal.getEmployeeId().equals(empId)) {
                return portal;
            }
        }
        return null;
    }

    @Override
    public List<SelfServicePortalResponse> getSelfServicePortalsByEmployee(String employeeId) {
        Long empId = Long.parseLong(employeeId);
        List<SelfServicePortalResponse> result = new ArrayList<>();
        for (SelfServicePortalResponse portal : portalList) {
            if (portal.getEmployeeId().equals(empId)) {
                result.add(portal);
            }
        }
        return result;
    }

    @Override
    public List<SelfServicePortalResponse> getAllSelfServicePortals() {
        return new ArrayList<>(portalList);
    }
}
