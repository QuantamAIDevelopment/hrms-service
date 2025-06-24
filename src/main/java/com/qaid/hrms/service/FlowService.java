package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.FlowRequest;
import com.qaid.hrms.model.dto.response.FlowResponse;
import java.util.List;

public interface FlowService {
    FlowResponse createFlow(FlowRequest request);
    FlowResponse updateFlowByAllocatedId(String allocatedId, FlowRequest request);
    void deleteFlowByAllocatedId(String allocatedId);
    FlowResponse getFlowByAllocatedId(String allocatedId);
    List<FlowResponse> getAllFlows();
    List<FlowResponse> getFlowsByStatus(String status);
}
