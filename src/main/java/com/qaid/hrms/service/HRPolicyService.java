package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.HRPolicyRequest;
import com.qaid.hrms.model.dto.response.HRPolicyResponse;
import java.util.List;

public interface HRPolicyService {
    HRPolicyResponse createPolicy(HRPolicyRequest request);
    HRPolicyResponse updatePolicyByAllocatedId(String allocatedId, HRPolicyRequest request);
    void deletePolicyByAllocatedId(String allocatedId);
    HRPolicyResponse getPolicyByAllocatedId(String allocatedId);
    List<HRPolicyResponse> getAllPolicies();
    List<HRPolicyResponse> getPoliciesByCategory(String category);
}
