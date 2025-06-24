package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.SelfServicePortalRequest;
import com.qaid.hrms.model.dto.response.SelfServicePortalResponse;

import java.util.List;

public interface SelfServicePortalService {

    SelfServicePortalResponse createSelfServicePortal(SelfServicePortalRequest request);

    SelfServicePortalResponse updateSelfServicePortal(Long id, SelfServicePortalRequest request);

    void deleteSelfServicePortal(Long id);

    SelfServicePortalResponse getSelfServicePortalById(Long id);

    List<SelfServicePortalResponse> getSelfServicePortalsByEmployee(Long employeeId);

    List<SelfServicePortalResponse> getAllSelfServicePortals();
}
