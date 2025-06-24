package com.qaid.hrms.service.impl;

import com.qaid.hrms.model.dto.request.TrainingModuleRequest;
import com.qaid.hrms.model.dto.response.TrainingModuleResponse;
import com.qaid.hrms.service.TrainingManagementService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrainingManagementServiceImpl implements TrainingManagementService {
    @Override
    public TrainingModuleResponse createTrainingModule(TrainingModuleRequest request) {
        TrainingModuleResponse response = new TrainingModuleResponse();
        response.setId(1L);
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        return response;
    }
    @Override
    public TrainingModuleResponse updateTrainingModuleByAllocatedId(String allocatedId, TrainingModuleRequest request) {
        TrainingModuleResponse response = new TrainingModuleResponse();
        response.setId(Long.parseLong(allocatedId));
        response.setTitle(request.getTitle());
        response.setDescription(request.getDescription());
        return response;
    }
    @Override
    public void deleteTrainingModuleByAllocatedId(String allocatedId) {
        // Example business logic: delete operation
    }
    @Override
    public TrainingModuleResponse getTrainingModuleByAllocatedId(String allocatedId) {
        TrainingModuleResponse response = new TrainingModuleResponse();
        response.setId(Long.parseLong(allocatedId));
        response.setTitle("Sample Training");
        response.setDescription("Sample Description");
        return response;
    }
    @Override
    public List<TrainingModuleResponse> getAllTrainingModules() {
        return List.of(getTrainingModuleByAllocatedId("1"));
    }
}
