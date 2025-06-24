package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.VibeRequest;
import com.qaid.hrms.model.dto.response.VibeResponse;
import java.util.List;

public interface VibeService {
    VibeResponse createVibe(VibeRequest request);
    VibeResponse updateVibe(Long id, VibeRequest request);
    void deleteVibe(Long id);
    VibeResponse getVibeById(Long id);
    List<VibeResponse> getAllVibes();
}
