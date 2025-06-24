package com.qaid.hrms.service.impl;

import com.qaid.hrms.model.dto.request.FlowRequest;
import com.qaid.hrms.model.dto.response.FlowResponse;
import com.qaid.hrms.service.FlowService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FlowServiceImpl implements FlowService {
    @Override
    public FlowResponse createFlow(FlowRequest request) {
        FlowResponse response = new FlowResponse();
        response.setId(1L);
        response.setName(request.getName());
        response.setStatus("Active");
        return response;
    }
    @Override
    public FlowResponse updateFlowByAllocatedId(String allocatedId, FlowRequest request) {
        FlowResponse response = new FlowResponse();
        response.setId(Long.parseLong(allocatedId));
        response.setName(request.getName());
        response.setStatus(request.getStatus());
        return response;
    }
    @Override
    public void deleteFlowByAllocatedId(String allocatedId) {
        // Example business logic: delete operation
    }
    @Override
    public FlowResponse getFlowByAllocatedId(String allocatedId) {
        FlowResponse response = new FlowResponse();
        response.setId(Long.parseLong(allocatedId));
        response.setName("Sample Flow");
        response.setStatus("Active");
        return response;
    }
    @Override
    public List<FlowResponse> getAllFlows() {
        return List.of(getFlowByAllocatedId("1"));
    }
    @Override
    public List<FlowResponse> getFlowsByStatus(String status) {
        FlowResponse response = new FlowResponse();
        response.setId(1L);
        response.setName("Sample Flow");
        response.setStatus(status);
        return List.of(response);
    }
}
