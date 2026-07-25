package com.orthocare.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prescription {
    private int id;
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private LocalDate prescribedDate;
    private String notes;
    private String patientName;
    private String doctorName;
    private List<PrescriptionItem> items = new ArrayList<>();

    public int getId(){return id;} public void setId(int v){id=v;}
    public int getAppointmentId(){return appointmentId;} public void setAppointmentId(int v){appointmentId=v;}
    public int getPatientId(){return patientId;} public void setPatientId(int v){patientId=v;}
    public int getDoctorId(){return doctorId;} public void setDoctorId(int v){doctorId=v;}
    public LocalDate getPrescribedDate(){return prescribedDate;} public void setPrescribedDate(LocalDate v){prescribedDate=v;}
    public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
    public String getPatientName(){return patientName;} public void setPatientName(String v){patientName=v;}
    public String getDoctorName(){return doctorName;} public void setDoctorName(String v){doctorName=v;}
    public List<PrescriptionItem> getItems(){return items;} public void setItems(List<PrescriptionItem> v){items=v;}
}
