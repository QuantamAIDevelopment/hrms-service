package com.qaid.hrms.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class EmployeeRequest {
    @NotBlank(message = "Employee ID is required")
    private String employeeId;

    @NotBlank(message = "First name is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private Long nationality;
    private LocalDate birthday;
    private String gender;
    private String maritalStatus;
    private String ssnNum;
    private String nicNum;
    private String otherId;

    @NotNull(message = "Employment status is required")
    private Long employmentStatus;

    @NotNull(message = "Job title is required")
    private Long jobTitle;

    private Long payGrade;
    private String workStationId;
    private String address1;
    private String address2;
    private String city;
    private String country;
    private Long province;
    private String postalCode;
    private String homePhone;
    private String mobilePhone;
    private String workPhone;

    @Email(message = "Invalid work email format")
    private String workEmail;

    @Email(message = "Invalid private email format")
    private String privateEmail;

    private LocalDate joinedDate;
    private LocalDate confirmationDate;
    private String supervisor;
    private Map<String, Object> indirectSupervisors;
    private Long department;
    private String timezone;
    private String profileImage;

}