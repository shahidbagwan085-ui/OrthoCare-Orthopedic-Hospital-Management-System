package com.orthocare.model;

import java.math.BigDecimal;

public class DashboardStats {
    private long totalPatients;
    private long totalDoctors;
    private long totalAppointments;
    private long todaysAppointments;
    private long totalBills;
    private BigDecimal totalRevenue = BigDecimal.ZERO;

    public long getTotalPatients(){return totalPatients;} public void setTotalPatients(long v){totalPatients=v;}
    public long getTotalDoctors(){return totalDoctors;} public void setTotalDoctors(long v){totalDoctors=v;}
    public long getTotalAppointments(){return totalAppointments;} public void setTotalAppointments(long v){totalAppointments=v;}
    public long getTodaysAppointments(){return todaysAppointments;} public void setTodaysAppointments(long v){todaysAppointments=v;}
    public long getTotalBills(){return totalBills;} public void setTotalBills(long v){totalBills=v;}
    public BigDecimal getTotalRevenue(){return totalRevenue;} public void setTotalRevenue(BigDecimal v){totalRevenue=v;}
}
