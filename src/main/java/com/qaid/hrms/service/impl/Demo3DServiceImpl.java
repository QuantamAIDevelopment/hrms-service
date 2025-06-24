package com.qaid.hrms.service.impl;

import com.qaid.hrms.model.dto.request.Demo3DRequest;
import com.qaid.hrms.model.dto.response.Demo3DResponse;
import com.qaid.hrms.service.Demo3DService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Demo3DServiceImpl implements Demo3DService {
    @Override
    public Demo3DResponse createDemo(Demo3DRequest request) {
        Demo3DResponse response = new Demo3DResponse();
        response.setId(1L); // Example static ID, replace with actual logic
        response.setName(request.getName());
        response.setDescription(request.getDescription());
        return response;
    }
    @Override
    public Demo3DResponse updateDemoByAllocatedId(String allocatedId, Demo3DRequest request) {
        Demo3DResponse response = new Demo3DResponse();
        response.setId(Long.parseLong(allocatedId));
        response.setName(request.getName());
        response.setDescription(request.getDescription());
        return response;
    }
    @Override
    public void deleteDemoByAllocatedId(String allocatedId) {
        // Example business logic: delete operation
    }
    @Override
    public Demo3DResponse getDemoByAllocatedId(String allocatedId) {
        Demo3DResponse response = new Demo3DResponse();
        response.setId(Long.parseLong(allocatedId));
        response.setName("Demo Name");
        response.setDescription("Demo Description");
        return response;
    }
    @Override
    public List<Demo3DResponse> getAllDemos() {
        return List.of(getDemoByAllocatedId("1"));
    }
}
