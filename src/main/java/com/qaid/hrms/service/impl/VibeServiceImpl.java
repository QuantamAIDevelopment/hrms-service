package com.qaid.hrms.service.impl;

import com.qaid.hrms.model.dto.request.VibeRequest;
import com.qaid.hrms.model.dto.response.VibeResponse;
import com.qaid.hrms.service.VibeService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VibeServiceImpl implements VibeService {
    @Override
    public VibeResponse createVibe(VibeRequest request) {
        VibeResponse response = new VibeResponse();
        response.setId(1L);
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        return response;
    }
    @Override
    public VibeResponse updateVibeByAllocatedId(String allocatedId, VibeRequest request) {
        VibeResponse response = new VibeResponse();
        response.setId(Long.parseLong(allocatedId));
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        return response;
    }
    @Override
    public void deleteVibeByAllocatedId(String allocatedId) {
        // Example business logic: delete operation
    }
    @Override
    public VibeResponse getVibeByAllocatedId(String allocatedId) {
        VibeResponse response = new VibeResponse();
        response.setId(Long.parseLong(allocatedId));
        response.setTitle("Sample Vibe");
        response.setDescription("Sample Description");
        return response;
    }
    @Override
    public List<VibeResponse> getAllVibes() {
        return List.of(getVibeByAllocatedId("1"));
    }
}
