package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.HRPolicyRequest;
import com.qaid.hrms.model.dto.response.HRPolicyResponse;
import com.qaid.hrms.service.HRPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/hrpolicies")
@RequiredArgsConstructor
public class HRPolicyController {
    private final HRPolicyService hrPolicyService;

    @PostMapping
    public ResponseEntity<HRPolicyResponse> createPolicy(@RequestBody HRPolicyRequest request) {
        return ResponseEntity.ok(hrPolicyService.createPolicy(request));
    }

    @PutMapping("/{allocatedId}")
    public ResponseEntity<HRPolicyResponse> updatePolicyByAllocatedId(@PathVariable String allocatedId, @RequestBody HRPolicyRequest request) {
        return ResponseEntity.ok(hrPolicyService.updatePolicyByAllocatedId(allocatedId, request));
    }

    @DeleteMapping("/{allocatedId}")
    public ResponseEntity<Void> deletePolicyByAllocatedId(@PathVariable String allocatedId) {
        hrPolicyService.deletePolicyByAllocatedId(allocatedId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{allocatedId}")
    public ResponseEntity<HRPolicyResponse> getPolicyByAllocatedId(@PathVariable String allocatedId) {
        return ResponseEntity.ok(hrPolicyService.getPolicyByAllocatedId(allocatedId));
    }

    @GetMapping
    public ResponseEntity<List<HRPolicyResponse>> getAllPolicies() {
        return ResponseEntity.ok(hrPolicyService.getAllPolicies());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<HRPolicyResponse>> getPoliciesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(hrPolicyService.getPoliciesByCategory(category));
    }
}
