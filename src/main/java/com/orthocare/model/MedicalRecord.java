package com.orthocare.model;

import java.time.LocalDate;

public class MedicalRecord {
    private int id;
    private int patientId;
    private LocalDate recordDate;
    private String title;
    private String description;
    private Integer createdBy;
    private String createdByName;

    public int getId(){return id;} public void setId(int v){id=v;}
    public int getPatientId(){return patientId;} public void setPatientId(int v){patientId=v;}
    public LocalDate getRecordDate(){return recordDate;} public void setRecordDate(LocalDate v){recordDate=v;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public Integer getCreatedBy(){return createdBy;} public void setCreatedBy(Integer v){createdBy=v;}
    public String getCreatedByName(){return createdByName;} public void setCreatedByName(String v){createdByName=v;}
}
