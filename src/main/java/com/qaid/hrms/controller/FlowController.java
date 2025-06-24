package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.FlowRequest;
import com.qaid.hrms.model.dto.response.FlowResponse;
import com.qaid.hrms.service.FlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/flows")
@RequiredArgsConstructor
public class FlowController {
    private final FlowService flowService;

    @PostMapping
    public ResponseEntity<FlowResponse> createFlow(@RequestBody FlowRequest request) {
        return ResponseEntity.ok(flowService.createFlow(request));
    }

    @PutMapping("/{allocatedId}")
    public ResponseEntity<FlowResponse> updateFlowByAllocatedId(@PathVariable String allocatedId, @RequestBody FlowRequest request) {
        return ResponseEntity.ok(flowService.updateFlowByAllocatedId(allocatedId, request));
    }

    @DeleteMapping("/{allocatedId}")
    public ResponseEntity<Void> deleteFlowByAllocatedId(@PathVariable String allocatedId) {
        flowService.deleteFlowByAllocatedId(allocatedId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{allocatedId}")
    public ResponseEntity<FlowResponse> getFlowByAllocatedId(@PathVariable String allocatedId) {
        return ResponseEntity.ok(flowService.getFlowByAllocatedId(allocatedId));
    }

    @GetMapping
    public ResponseEntity<List<FlowResponse>> getAllFlows() {
        return ResponseEntity.ok(flowService.getAllFlows());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<FlowResponse>> getFlowsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(flowService.getFlowsByStatus(status));
    }
}
