package com.qaid.hrms.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveBalanceDto {

    private Long id;
    private Long employeeId;
    private Long leaveTypeId;
    private Integer totalDays;
    private Integer usedDays = 0;
    private Integer remainingDays;
    private Integer year;

}
