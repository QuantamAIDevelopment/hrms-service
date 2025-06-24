package com.qaid.hrms.service.impl;

import com.qaid.hrms.model.dto.request.CalendarEventRequest;
import com.qaid.hrms.model.dto.response.CalendarEventResponse;
import com.qaid.hrms.service.CalendarService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {
    @Override
    public CalendarEventResponse createEvent(CalendarEventRequest request) {
        CalendarEventResponse response = new CalendarEventResponse();
        response.setId(1L);
        response.setTitle(request.getTitle());
        response.setType(request.getType());
        response.setEventDate(request.getEventDate());
        response.setDescription(request.getDescription());
        response.setLocation(request.getLocation());
        return response;
    }
    @Override
    public CalendarEventResponse updateEventByAllocatedId(String allocatedId, CalendarEventRequest request) {
        CalendarEventResponse response = new CalendarEventResponse();
        response.setId(Long.parseLong(allocatedId));
        response.setTitle(request.getTitle());
        response.setType(request.getType());
        response.setEventDate(request.getEventDate());
        response.setDescription(request.getDescription());
        response.setLocation(request.getLocation());
        return response;
    }
    @Override
    public void deleteEventByAllocatedId(String allocatedId) {
        // Example business logic: delete operation
    }
    @Override
    public CalendarEventResponse getEventByAllocatedId(String allocatedId) {
        CalendarEventResponse response = new CalendarEventResponse();
        response.setId(Long.parseLong(allocatedId));
        response.setTitle("Sample Event");
        response.setType(null);
        response.setEventDate(LocalDate.now());
        response.setDescription("Sample Description");
        response.setLocation("Sample Location");
        return response;
    }
    @Override
    public List<CalendarEventResponse> getAllEvents() {
        return List.of(getEventByAllocatedId("1"));
    }
    @Override
    public List<CalendarEventResponse> getEventsByDate(LocalDate date) {
        CalendarEventResponse response = new CalendarEventResponse();
        response.setId(1L);
        response.setTitle("Event on Date");
        response.setType(null);
        response.setEventDate(date);
        response.setDescription("Event Description");
        response.setLocation("Event Location");
        return List.of(response);
    }
    @Override
    public List<CalendarEventResponse> getEventsByType(String type) {
        CalendarEventResponse response = new CalendarEventResponse();
        response.setId(1L);
        response.setTitle("Event of Type");
        response.setType(null);
        response.setEventDate(LocalDate.now());
        response.setDescription("Event Description");
        response.setLocation("Event Location");
        return List.of(response);
    }
}
