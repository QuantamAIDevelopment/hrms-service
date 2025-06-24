package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.VibeRequest;
import com.qaid.hrms.model.dto.response.VibeResponse;
import com.qaid.hrms.service.VibeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vibes")
@RequiredArgsConstructor
public class VibeController {
    private final VibeService vibeService;

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PostMapping
    public ResponseEntity<VibeResponse> createVibe(@RequestBody VibeRequest request) {
        return ResponseEntity.ok(vibeService.createVibe(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PutMapping("/{allocatedId}")
    public ResponseEntity<VibeResponse> updateVibeByAllocatedId(@PathVariable String allocatedId, @RequestBody VibeRequest request) {
        return ResponseEntity.ok(vibeService.updateVibeByAllocatedId(allocatedId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{allocatedId}")
    public ResponseEntity<Void> deleteVibeByAllocatedId(@PathVariable String allocatedId) {
        vibeService.deleteVibeByAllocatedId(allocatedId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/{allocatedId}")
    public ResponseEntity<VibeResponse> getVibeByAllocatedId(@PathVariable String allocatedId) {
        return ResponseEntity.ok(vibeService.getVibeByAllocatedId(allocatedId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<VibeResponse>> getAllVibes() {
        return ResponseEntity.ok(vibeService.getAllVibes());
    }
}
