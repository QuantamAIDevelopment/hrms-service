package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.CalendarEventRequest;
import com.qaid.hrms.model.dto.response.CalendarEventResponse;
import java.time.LocalDate;
import java.util.List;

public interface CalendarService {
    CalendarEventResponse createEvent(CalendarEventRequest request);
    CalendarEventResponse updateEventByAllocatedId(String allocatedId, CalendarEventRequest request);
    void deleteEventByAllocatedId(String allocatedId);
    CalendarEventResponse getEventByAllocatedId(String allocatedId);
    List<CalendarEventResponse> getAllEvents();
    List<CalendarEventResponse> getEventsByDate(LocalDate date);
    List<CalendarEventResponse> getEventsByType(String type);
}
