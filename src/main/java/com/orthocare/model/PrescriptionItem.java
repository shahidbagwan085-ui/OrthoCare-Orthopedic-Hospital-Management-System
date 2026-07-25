package com.orthocare.model;

public class PrescriptionItem {
    private int id;
    private int prescriptionId;
    private int medicineId;
    private String medicineName;
    private String dosage;
    private boolean morning;
    private boolean afternoon;
    private boolean night;
    private Integer durationDays;
    private String instructions;

    public int getId(){return id;} public void setId(int v){id=v;}
    public int getPrescriptionId(){return prescriptionId;} public void setPrescriptionId(int v){prescriptionId=v;}
    public int getMedicineId(){return medicineId;} public void setMedicineId(int v){medicineId=v;}
    public String getMedicineName(){return medicineName;} public void setMedicineName(String v){medicineName=v;}
    public String getDosage(){return dosage;} public void setDosage(String v){dosage=v;}
    public boolean isMorning(){return morning;} public void setMorning(boolean v){morning=v;}
    public boolean isAfternoon(){return afternoon;} public void setAfternoon(boolean v){afternoon=v;}
    public boolean isNight(){return night;} public void setNight(boolean v){night=v;}
    public Integer getDurationDays(){return durationDays;} public void setDurationDays(Integer v){durationDays=v;}
    public String getInstructions(){return instructions;} public void setInstructions(String v){instructions=v;}
}
