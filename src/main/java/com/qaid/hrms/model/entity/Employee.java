package com.qaid.hrms.model.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false, unique = true, length = 50)
    private String employeeId;

    @Column(nullable = false)
    private String firstName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "nationality")
    private Country nationality;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(length = 10)
    private String gender;

    @Column(name = "marital_status", length = 20)
    private String maritalStatus;

    @Column(name = "ssn_num", length = 30)
    private String ssnNum;

    @Column(name = "nic_num", length = 30)
    private String nicNum;

    @Column(name = "other_id", length = 30)
    private String otherId;

    @ManyToOne
    @JoinColumn(name = "employment_status")
    private EmploymentStatus employmentStatus;

    @ManyToOne
    @JoinColumn(name = "job_title")
    private JobTitle jobTitle;

    @ManyToOne
    @JoinColumn(name = "pay_grade")
    private PayGrade payGrade;

    @Column(name = "work_station_id", length = 50)
    private String workStationId;

    @Column(length = 100)
    private String address1;

    @Column(length = 100)
    private String address2;

    @Column(length = 50)
    private String city;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "province")
    private Province province;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "home_phone", length = 20)
    private String homePhone;

    @Column(name = "mobile_phone", length = 20)
    private String mobilePhone;

    @Column(name = "work_phone", length = 20)
    private String workPhone;

    @Column(name = "work_email", length = 100)
    private String workEmail;

    @Column(name = "private_email", length = 100)
    private String privateEmail;

    @Column(nullable = false)
    private LocalDate joinedDate;

    @Column(nullable = false)
    private LocalDate confirmationDate;

    @ManyToOne
    @JoinColumn(name = "supervisor")
    private Employee supervisor;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb",  name = "indirect_supervisors")
    private Map<String, Object> indirectSupervisors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designation_id", nullable = false)
    private Designation designation;

    @Column(nullable = false, length = 20)
    private String status = "Active";

    @Column(length = 50)
    private String timezone;

    @Column(name = "profile_image", length = 100)
    private String profileImage;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}