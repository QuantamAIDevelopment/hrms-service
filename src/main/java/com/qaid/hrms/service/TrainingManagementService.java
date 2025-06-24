package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.TrainingModuleRequest;
import com.qaid.hrms.model.dto.response.TrainingModuleResponse;

import java.util.List;

public interface TrainingManagementService {

    TrainingModuleResponse createTrainingModule(TrainingModuleRequest request);

    TrainingModuleResponse updateTrainingModuleByAllocatedId(String allocatedId, TrainingModuleRequest request);

    void deleteTrainingModuleByAllocatedId(String allocatedId);

    TrainingModuleResponse getTrainingModuleByAllocatedId(String allocatedId);

    List<TrainingModuleResponse> getAllTrainingModules();
}
