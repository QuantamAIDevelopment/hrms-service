package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.SelfServicePortalRequest;
import com.qaid.hrms.model.dto.response.SelfServicePortalResponse;

import java.util.List;

public interface SelfServicePortalService {
    SelfServicePortalResponse createSelfServicePortal(SelfServicePortalRequest request);
    SelfServicePortalResponse updateSelfServicePortalByEmployeeId(String employeeId, SelfServicePortalRequest request);
    void deleteSelfServicePortalByEmployeeId(String employeeId);
    SelfServicePortalResponse getSelfServicePortalByEmployeeId(String employeeId);
    List<SelfServicePortalResponse> getSelfServicePortalsByEmployee(String employeeId);
    List<SelfServicePortalResponse> getAllSelfServicePortals();
}
