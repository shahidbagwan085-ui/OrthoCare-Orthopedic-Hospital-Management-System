package com.orthocare.model;

import java.math.BigDecimal;

public class Doctor {
    private int id;
    private int userId;
    private String fullName;       // joined from users
    private String qualification;
    private Integer experienceYears;
    private String specialization;
    private BigDecimal consultationFee;
    private String availableDays;
    private String availableTimes;
    private String department;     // ORTHOPEDICS | PHYSIOTHERAPY
    private String contact;
    private String email;

    public int getId() { return id; } public void setId(int v) { id = v; }
    public int getUserId() { return userId; } public void setUserId(int v) { userId = v; }
    public String getFullName() { return fullName; } public void setFullName(String v) { fullName = v; }
    public String getQualification() { return qualification; } public void setQualification(String v){qualification=v;}
    public Integer getExperienceYears() { return experienceYears; } public void setExperienceYears(Integer v){experienceYears=v;}
    public String getSpecialization() { return specialization; } public void setSpecialization(String v){specialization=v;}
    public BigDecimal getConsultationFee() { return consultationFee; } public void setConsultationFee(BigDecimal v){consultationFee=v;}
    public String getAvailableDays() { return availableDays; } public void setAvailableDays(String v){availableDays=v;}
    public String getAvailableTimes() { return availableTimes; } public void setAvailableTimes(String v){availableTimes=v;}
    public String getDepartment() { return department; } public void setDepartment(String v){department=v;}
    public String getContact() { return contact; } public void setContact(String v){contact=v;}
    public String getEmail() { return email; } public void setEmail(String v){email=v;}
}
