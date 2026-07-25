package com.orthocare.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Bill {
    private int id;
    private String billNumber;
    private int patientId;
    private Integer appointmentId;
    private BigDecimal totalAmount;
    private BigDecimal gstAmount;
    private BigDecimal discount;
    private BigDecimal netAmount;
    private String status; // UNPAID, PARTIAL, PAID
    private LocalDateTime createdAt;
    private String patientName;
    private List<Payment> payments = new ArrayList<>();

    public int getId(){return id;} public void setId(int v){id=v;}
    public String getBillNumber(){return billNumber;} public void setBillNumber(String v){billNumber=v;}
    public int getPatientId(){return patientId;} public void setPatientId(int v){patientId=v;}
    public Integer getAppointmentId(){return appointmentId;} public void setAppointmentId(Integer v){appointmentId=v;}
    public BigDecimal getTotalAmount(){return totalAmount;} public void setTotalAmount(BigDecimal v){totalAmount=v;}
    public BigDecimal getGstAmount(){return gstAmount;} public void setGstAmount(BigDecimal v){gstAmount=v;}
    public BigDecimal getDiscount(){return discount;} public void setDiscount(BigDecimal v){discount=v;}
    public BigDecimal getNetAmount(){return netAmount;} public void setNetAmount(BigDecimal v){netAmount=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
    public String getPatientName(){return patientName;} public void setPatientName(String v){patientName=v;}
    public List<Payment> getPayments(){return payments;} public void setPayments(List<Payment> v){payments=v;}
}
