package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.VibeRequest;
import com.qaid.hrms.model.dto.response.VibeResponse;
import com.qaid.hrms.service.VibeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vibes")
@RequiredArgsConstructor
public class VibeController {
    private final VibeService vibeService;

    @PostMapping
    public ResponseEntity<VibeResponse> createVibe(@RequestBody VibeRequest request) {
        return ResponseEntity.ok(vibeService.createVibe(request));
    }

    @PutMapping("/{allocatedId}")
    public ResponseEntity<VibeResponse> updateVibeByAllocatedId(@PathVariable String allocatedId, @RequestBody VibeRequest request) {
        return ResponseEntity.ok(vibeService.updateVibeByAllocatedId(allocatedId, request));
    }

    @DeleteMapping("/{allocatedId}")
    public ResponseEntity<Void> deleteVibeByAllocatedId(@PathVariable String allocatedId) {
        vibeService.deleteVibeByAllocatedId(allocatedId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{allocatedId}")
    public ResponseEntity<VibeResponse> getVibeByAllocatedId(@PathVariable String allocatedId) {
        return ResponseEntity.ok(vibeService.getVibeByAllocatedId(allocatedId));
    }

    @GetMapping
    public ResponseEntity<List<VibeResponse>> getAllVibes() {
        return ResponseEntity.ok(vibeService.getAllVibes());
    }
}
