package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.HRPolicyRequest;
import com.qaid.hrms.model.dto.response.HRPolicyResponse;
import java.util.List;

public interface HRPolicyService {
    HRPolicyResponse createPolicy(HRPolicyRequest request);
    HRPolicyResponse updatePolicy(Long id, HRPolicyRequest request);
    void deletePolicy(Long id);
    HRPolicyResponse getPolicyById(Long id);
    List<HRPolicyResponse> getAllPolicies();
    List<HRPolicyResponse> getPoliciesByCategory(String category);
}
