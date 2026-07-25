package com.orthocare.service;

import com.orthocare.dao.AppointmentDAO;
import com.orthocare.dao.BillDAO;
import com.orthocare.dao.DoctorDAO;
import com.orthocare.dao.PatientDAO;
import com.orthocare.model.DashboardStats;

import java.sql.SQLException;

public class DashboardService {
    private final PatientDAO patientDAO = new PatientDAO();
    private final DoctorDAO doctorDAO = new DoctorDAO();
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final BillDAO billDAO = new BillDAO();

    public DashboardStats getStats() throws SQLException {
        DashboardStats s = new DashboardStats();
        s.setTotalPatients(patientDAO.count());
        s.setTotalDoctors(doctorDAO.count());
        s.setTotalAppointments(appointmentDAO.count());
        s.setTodaysAppointments(appointmentDAO.countToday());
        s.setTotalBills(billDAO.count());
        s.setTotalRevenue(billDAO.totalRevenue());
        return s;
    }
}
