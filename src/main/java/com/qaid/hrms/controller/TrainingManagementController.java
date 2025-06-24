package com.qaid.hrms.controller;

import com.qaid.hrms.model.dto.request.TrainingModuleRequest;
import com.qaid.hrms.model.dto.response.TrainingModuleResponse;
import com.qaid.hrms.service.TrainingManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/training-modules")
@RequiredArgsConstructor
public class TrainingManagementController {
    private final TrainingManagementService trainingManagementService;

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PostMapping
    public ResponseEntity<TrainingModuleResponse> createTrainingModule(@RequestBody TrainingModuleRequest request) {
        return ResponseEntity.ok(trainingManagementService.createTrainingModule(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @PutMapping("/{allocatedId}")
    public ResponseEntity<TrainingModuleResponse> updateTrainingModuleByAllocatedId(@PathVariable String allocatedId, @RequestBody TrainingModuleRequest request) {
        return ResponseEntity.ok(trainingManagementService.updateTrainingModuleByAllocatedId(allocatedId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{allocatedId}")
    public ResponseEntity<Void> deleteTrainingModuleByAllocatedId(@PathVariable String allocatedId) {
        trainingManagementService.deleteTrainingModuleByAllocatedId(allocatedId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @GetMapping("/{allocatedId}")
    public ResponseEntity<TrainingModuleResponse> getTrainingModuleByAllocatedId(@PathVariable String allocatedId) {
        return ResponseEntity.ok(trainingManagementService.getTrainingModuleByAllocatedId(allocatedId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<TrainingModuleResponse>> getAllTrainingModules() {
        return ResponseEntity.ok(trainingManagementService.getAllTrainingModules());
    }
}
