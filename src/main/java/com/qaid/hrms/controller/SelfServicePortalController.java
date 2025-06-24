package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.SelfServicePortalRequest;
import com.qaid.hrms.model.dto.response.SelfServicePortalResponse;
import com.qaid.hrms.service.SelfServicePortalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/self-service-portals")
@RequiredArgsConstructor
public class SelfServicePortalController {
    private final SelfServicePortalService selfServicePortalService;

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PostMapping
    public ResponseEntity<SelfServicePortalResponse> createSelfServicePortal(@RequestBody SelfServicePortalRequest request) {
        return ResponseEntity.ok(selfServicePortalService.createSelfServicePortal(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PutMapping("/{employeeId}")
    public ResponseEntity<SelfServicePortalResponse> updateSelfServicePortalByEmployeeId(@PathVariable String employeeId, @RequestBody SelfServicePortalRequest request) {
        return ResponseEntity.ok(selfServicePortalService.updateSelfServicePortalByEmployeeId(employeeId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteSelfServicePortalByEmployeeId(@PathVariable String employeeId) {
        selfServicePortalService.deleteSelfServicePortalByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/{employeeId}")
    public ResponseEntity<SelfServicePortalResponse> getSelfServicePortalByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(selfServicePortalService.getSelfServicePortalByEmployeeId(employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SelfServicePortalResponse>> getSelfServicePortalsByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(selfServicePortalService.getSelfServicePortalsByEmployee(employeeId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<SelfServicePortalResponse>> getAllSelfServicePortals() {
        return ResponseEntity.ok(selfServicePortalService.getAllSelfServicePortals());
    }
}
