package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.FlowRequest;
import com.qaid.hrms.model.dto.response.FlowResponse;
import java.util.List;

public interface FlowService {
    FlowResponse createFlow(FlowRequest request);
    FlowResponse updateFlow(Long id, FlowRequest request);
    void deleteFlow(Long id);
    FlowResponse getFlowById(Long id);
    List<FlowResponse> getAllFlows();
    List<FlowResponse> getFlowsByStatus(String status);
}
