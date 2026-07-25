package com.orthocare.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Patient {
    private int id;
    private String patientCode;
    private Integer userId;
    private String fullName;
    private Integer age;
    private String gender;
    private LocalDate dob;
    private String bloodGroup;
    private BigDecimal heightCm;
    private BigDecimal weightKg;
    private String phone;
    private String email;
    private String address;
    private String emergencyContact;
    private String allergies;
    private String existingDiseases;
    private String insuranceDetails;

    public int getId() { return id; } public void setId(int id) { this.id = id; }
    public String getPatientCode() { return patientCode; } public void setPatientCode(String v){patientCode=v;}
    public Integer getUserId() { return userId; } public void setUserId(Integer v) { this.userId = v; }
    public String getFullName() { return fullName; } public void setFullName(String v) { this.fullName = v; }
    public Integer getAge() { return age; } public void setAge(Integer v) { this.age = v; }
    public String getGender() { return gender; } public void setGender(String v) { this.gender = v; }
    public LocalDate getDob() { return dob; } public void setDob(LocalDate v) { this.dob = v; }
    public String getBloodGroup() { return bloodGroup; } public void setBloodGroup(String v) { this.bloodGroup = v; }
    public BigDecimal getHeightCm() { return heightCm; } public void setHeightCm(BigDecimal v) { this.heightCm = v; }
    public BigDecimal getWeightKg() { return weightKg; } public void setWeightKg(BigDecimal v) { this.weightKg = v; }
    public String getPhone() { return phone; } public void setPhone(String v) { this.phone = v; }
    public String getEmail() { return email; } public void setEmail(String v) { this.email = v; }
    public String getAddress() { return address; } public void setAddress(String v) { this.address = v; }
    public String getEmergencyContact() { return emergencyContact; } public void setEmergencyContact(String v) { this.emergencyContact = v; }
    public String getAllergies() { return allergies; } public void setAllergies(String v) { this.allergies = v; }
    public String getExistingDiseases() { return existingDiseases; } public void setExistingDiseases(String v) { this.existingDiseases = v; }
    public String getInsuranceDetails() { return insuranceDetails; } public void setInsuranceDetails(String v) { this.insuranceDetails = v; }
}
