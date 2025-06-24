package com.qaid.hrms.repository;

import com.qaid.hrms.model.dto.request.TravelRequest;
import com.qaid.hrms.model.dto.response.TravelResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TravelService {
    TravelResponse createTravel(TravelRequest travelRequest);
    TravelResponse updateTravel(Long id, TravelRequest travelRequest);
    void deleteTravel(Long id);
    TravelResponse getTravelById(Long id);
    List<TravelResponse> getAllTravels();
    List<TravelResponse> getTravelsByEmployee(Long employeeId);
    List<TravelResponse> getTravelsByDateRange(LocalDate startDate, LocalDate endDate);
    List<TravelResponse> getTravelsByEmployeeAndDateRange(Long employeeId, LocalDate startDate, LocalDate endDate);
    TravelResponse approveTravel(Long id, String comments);
    TravelResponse rejectTravel(Long id, String comments);
    TravelResponse updateActualCost(Long id, BigDecimal actualCost, String comments);
} 