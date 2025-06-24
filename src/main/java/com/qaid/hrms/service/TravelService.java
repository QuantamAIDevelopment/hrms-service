package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.TravelRequest;
import com.qaid.hrms.model.dto.response.TravelResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TravelService {
    TravelResponse createTravel(TravelRequest travelRequest);
    TravelResponse updateTravelByEmployeeId(String employeeId, TravelRequest travelRequest);
    void deleteTravelByEmployeeId(String employeeId);
    TravelResponse getTravelByEmployeeId(String employeeId);
    List<TravelResponse> getAllTravels();
    List<TravelResponse> getTravelsByEmployee(String employeeId);
    List<TravelResponse> getTravelsByDateRange(LocalDate startDate, LocalDate endDate);
    List<TravelResponse> getTravelsByEmployeeAndDateRange(String employeeId, LocalDate startDate, LocalDate endDate);
    TravelResponse approveTravelByEmployeeId(String employeeId, String comments);
    TravelResponse rejectTravelByEmployeeId(String employeeId, String comments);
    TravelResponse updateActualCostByEmployeeId(String employeeId, BigDecimal actualCost, String comments);
}