package com.qaid.hrms.model.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class EmployeeResponse {
    private Long id;
    private String employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nationality;
    private LocalDate birthday;
    private String gender;
    private String maritalStatus;
    private String ssnNum;
    private String nicNum;
    private String otherId;
    private String employmentStatus;
    private String jobTitle;
    private String payGrade;
    private String workStationId;
    private String address1;
    private String address2;
    private String city;
    private String country;
    private String province;
    private String postalCode;
    private String homePhone;
    private String mobilePhone;
    private String workPhone;
    private String workEmail;
    private String privateEmail;
    private LocalDate joinedDate;
    private LocalDate confirmationDate;
    private String supervisor;
    private Map<String, Object> indirectSupervisors;
    private String department;
    private String status;
    private String timezone;
    private String profileImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}