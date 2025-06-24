package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.HRPolicyRequest;
import com.qaid.hrms.model.dto.response.HRPolicyResponse;
import com.qaid.hrms.service.HRPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/hrpolicies")
@RequiredArgsConstructor
public class HRPolicyController {
    private final HRPolicyService hrPolicyService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HRPolicyResponse> createPolicy(@RequestBody HRPolicyRequest request) {
        return ResponseEntity.ok(hrPolicyService.createPolicy(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{allocatedId}")
    public ResponseEntity<HRPolicyResponse> updatePolicyByAllocatedId(@PathVariable String allocatedId, @RequestBody HRPolicyRequest request) {
        return ResponseEntity.ok(hrPolicyService.updatePolicyByAllocatedId(allocatedId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{allocatedId}")
    public ResponseEntity<Void> deletePolicyByAllocatedId(@PathVariable String allocatedId) {
        hrPolicyService.deletePolicyByAllocatedId(allocatedId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/{allocatedId}")
    public ResponseEntity<HRPolicyResponse> getPolicyByAllocatedId(@PathVariable String allocatedId) {
        return ResponseEntity.ok(hrPolicyService.getPolicyByAllocatedId(allocatedId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<HRPolicyResponse>> getAllPolicies() {
        return ResponseEntity.ok(hrPolicyService.getAllPolicies());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/category/{category}")
    public ResponseEntity<List<HRPolicyResponse>> getPoliciesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(hrPolicyService.getPoliciesByCategory(category));
    }
}
