package com.orthocare.dao;

import com.orthocare.model.Appointment;
import com.orthocare.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public int insert(Appointment a) throws SQLException {
        String sql = "INSERT INTO appointments(appointment_code,patient_id,doctor_id,department,appointment_dt,reason,status) " +
                "VALUES(?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bind(ps, a);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) { a.setId(rs.getInt(1)); return a.getId(); }
            }
        }
        return -1;
    }

    public void update(Appointment a) throws SQLException {
        String sql = "UPDATE appointments SET appointment_code=?, patient_id=?, doctor_id=?, department=?, " +
                "appointment_dt=?, reason=?, status=? WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            bind(ps, a);
            ps.setInt(8, a.getId());
            ps.executeUpdate();
        }
    }

    public void updateStatus(int id, String status) throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE appointments SET status=? WHERE id=?")) {
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM appointments WHERE id=?")) {
            ps.setInt(1, id); ps.executeUpdate();
        }
    }

    public Appointment findById(int id) throws SQLException {
        String sql = base() + " WHERE a.id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    public List<Appointment> findAll() throws SQLException {
        List<Appointment> list = new ArrayList<>();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(base() + " ORDER BY a.appointment_dt DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public long count() throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM appointments");
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getLong(1) : 0;
        }
    }

    public long countToday() throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM appointments WHERE DATE(appointment_dt)=?")) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? rs.getLong(1) : 0; }
        }
    }

    private String base() {
        return "SELECT a.*, p.full_name AS patient_name, u.full_name AS doctor_name " +
               "FROM appointments a " +
               "JOIN patients p ON p.id = a.patient_id " +
               "JOIN doctors d ON d.id = a.doctor_id " +
               "JOIN users u ON u.id = d.user_id";
    }

    private void bind(PreparedStatement ps, Appointment a) throws SQLException {
        ps.setString(1, a.getAppointmentCode());
        ps.setInt(2, a.getPatientId());
        ps.setInt(3, a.getDoctorId());
        ps.setString(4, a.getDepartment());
        ps.setTimestamp(5, Timestamp.valueOf(a.getAppointmentDt()));
        ps.setString(6, a.getReason());
        ps.setString(7, a.getStatus());
    }

    private Appointment map(ResultSet rs) throws SQLException {
        Appointment a = new Appointment();
        a.setId(rs.getInt("id"));
        a.setAppointmentCode(rs.getString("appointment_code"));
        a.setPatientId(rs.getInt("patient_id"));
        a.setDoctorId(rs.getInt("doctor_id"));
        a.setDepartment(rs.getString("department"));
        Timestamp ts = rs.getTimestamp("appointment_dt");
        if (ts != null) a.setAppointmentDt(ts.toLocalDateTime());
        a.setReason(rs.getString("reason"));
        a.setStatus(rs.getString("status"));
        a.setPatientName(rs.getString("patient_name"));
        a.setDoctorName(rs.getString("doctor_name"));
        return a;
    }
}
