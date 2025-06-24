package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.TrainingModuleRequest;
import com.qaid.hrms.model.dto.response.TrainingModuleResponse;

import java.util.List;

public interface TrainingManagementService {

    TrainingModuleResponse createTrainingModule(TrainingModuleRequest request);

    TrainingModuleResponse updateTrainingModule(Long id, TrainingModuleRequest request);

    void deleteTrainingModule(Long id);

    TrainingModuleResponse getTrainingModuleById(Long id);

    List<TrainingModuleResponse> getAllTrainingModules();
}
