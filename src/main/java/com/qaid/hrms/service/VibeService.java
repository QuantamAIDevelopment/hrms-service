package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.VibeRequest;
import com.qaid.hrms.model.dto.response.VibeResponse;
import java.util.List;

public interface VibeService {
    VibeResponse createVibe(VibeRequest request);
    VibeResponse updateVibeByAllocatedId(String allocatedId, VibeRequest request);
    void deleteVibeByAllocatedId(String allocatedId);
    VibeResponse getVibeByAllocatedId(String allocatedId);
    List<VibeResponse> getAllVibes();
}
