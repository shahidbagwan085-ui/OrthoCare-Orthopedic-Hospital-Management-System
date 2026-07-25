package com.orthocare.model;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private String appointmentCode;
    private int patientId;
    private int doctorId;
    private String department;
    private LocalDateTime appointmentDt;
    private String reason;
    private String status; // PENDING, CONFIRMED, COMPLETED, CANCELLED
    // Display-only fields
    private String patientName;
    private String doctorName;

    public int getId() { return id; } public void setId(int v){id=v;}
    public String getAppointmentCode(){return appointmentCode;} public void setAppointmentCode(String v){appointmentCode=v;}
    public int getPatientId(){return patientId;} public void setPatientId(int v){patientId=v;}
    public int getDoctorId(){return doctorId;} public void setDoctorId(int v){doctorId=v;}
    public String getDepartment(){return department;} public void setDepartment(String v){department=v;}
    public LocalDateTime getAppointmentDt(){return appointmentDt;} public void setAppointmentDt(LocalDateTime v){appointmentDt=v;}
    public String getFormattedDt() {
        if (appointmentDt == null) return "";
        return appointmentDt.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }
    public String getReason(){return reason;} public void setReason(String v){reason=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public String getPatientName(){return patientName;} public void setPatientName(String v){patientName=v;}
    public String getDoctorName(){return doctorName;} public void setDoctorName(String v){doctorName=v;}
}
