package com.qaid.hrms.model.dto.response;

import com.qaid.hrms.model.enums.CalendarEventType;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CalendarEventResponse {
    private Long id;
    private String title;
    private LocalDate eventDate;
    private String description;
    private String location;
    private CalendarEventType type;
}
