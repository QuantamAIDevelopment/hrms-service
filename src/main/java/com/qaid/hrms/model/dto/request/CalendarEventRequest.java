package com.qaid.hrms.model.dto.request;

import com.qaid.hrms.model.enums.CalendarEventType;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CalendarEventRequest {
    private String title;
    private LocalDate eventDate;
    private String description;
    private String location;
    private CalendarEventType type;
}
