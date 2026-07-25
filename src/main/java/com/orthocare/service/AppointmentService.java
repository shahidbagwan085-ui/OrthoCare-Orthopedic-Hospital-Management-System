package com.orthocare.service;

import com.orthocare.dao.AppointmentDAO;
import com.orthocare.model.Appointment;
import com.orthocare.util.CodeGenerator;
import com.orthocare.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

public class AppointmentService {
    private final AppointmentDAO dao = new AppointmentDAO();

    public int save(Appointment a) throws SQLException {
        if (ValidationUtil.isBlank(a.getAppointmentCode())) a.setAppointmentCode(CodeGenerator.appointmentCode());
        if (ValidationUtil.isBlank(a.getStatus())) a.setStatus("PENDING");
        return dao.insert(a);
    }
    public void update(Appointment a) throws SQLException { dao.update(a); }
    public void updateStatus(int id, String status) throws SQLException { dao.updateStatus(id, status); }
    public void cancel(int id) throws SQLException { dao.updateStatus(id, "CANCELLED"); }
    public void delete(int id) throws SQLException { dao.delete(id); }
    public Appointment get(int id) throws SQLException { return dao.findById(id); }
    public List<Appointment> list() throws SQLException { return dao.findAll(); }
}
