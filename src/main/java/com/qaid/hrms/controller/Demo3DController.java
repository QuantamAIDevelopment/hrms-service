package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.Demo3DRequest;
import com.qaid.hrms.model.dto.response.Demo3DResponse;
import com.qaid.hrms.service.Demo3DService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/demo3d")
@RequiredArgsConstructor
public class Demo3DController {
    private final Demo3DService demo3DService;

    @PostMapping
    public ResponseEntity<Demo3DResponse> createDemo(@RequestBody Demo3DRequest request) {
        return ResponseEntity.ok(demo3DService.createDemo(request));
    }

    @PutMapping("/{allocatedId}")
    public ResponseEntity<Demo3DResponse> updateDemoByAllocatedId(@PathVariable String allocatedId, @RequestBody Demo3DRequest request) {
        return ResponseEntity.ok(demo3DService.updateDemoByAllocatedId(allocatedId, request));
    }

    @DeleteMapping("/{allocatedId}")
    public ResponseEntity<Void> deleteDemoByAllocatedId(@PathVariable String allocatedId) {
        demo3DService.deleteDemoByAllocatedId(allocatedId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{allocatedId}")
    public ResponseEntity<Demo3DResponse> getDemoByAllocatedId(@PathVariable String allocatedId) {
        return ResponseEntity.ok(demo3DService.getDemoByAllocatedId(allocatedId));
    }

    @GetMapping
    public ResponseEntity<List<Demo3DResponse>> getAllDemos() {
        return ResponseEntity.ok(demo3DService.getAllDemos());
    }
}
