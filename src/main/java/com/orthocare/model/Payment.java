package com.orthocare.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private int id;
    private int billId;
    private BigDecimal amount;
    private LocalDateTime paidAt;
    private String method; // CASH, CARD, UPI
    private String referenceId;

    public int getId(){return id;} public void setId(int v){id=v;}
    public int getBillId(){return billId;} public void setBillId(int v){billId=v;}
    public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;}
    public LocalDateTime getPaidAt(){return paidAt;} public void setPaidAt(LocalDateTime v){paidAt=v;}
    public String getMethod(){return method;} public void setMethod(String v){method=v;}
    public String getReferenceId(){return referenceId;} public void setReferenceId(String v){referenceId=v;}
}
