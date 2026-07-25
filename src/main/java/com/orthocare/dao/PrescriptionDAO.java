package com.orthocare.dao;

import com.orthocare.model.Prescription;
import com.orthocare.model.PrescriptionItem;
import com.orthocare.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO {

    public int insert(Prescription p) throws SQLException {
        String sql = "INSERT INTO prescriptions(appointment_id,patient_id,doctor_id,prescribed_date,notes) VALUES(?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, p.getAppointmentId());
                ps.setInt(2, p.getPatientId());
                ps.setInt(3, p.getDoctorId());
                ps.setDate(4, Date.valueOf(p.getPrescribedDate()));
                ps.setString(5, p.getNotes());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) p.setId(rs.getInt(1)); }
            }
            String ins = "INSERT INTO prescription_items(prescription_id,medicine_id,dosage,morning,afternoon,night,duration_days,instructions) VALUES(?,?,?,?,?,?,?,?)";
            try (PreparedStatement ps = c.prepareStatement(ins)) {
                for (PrescriptionItem it : p.getItems()) {
                    ps.setInt(1, p.getId());
                    ps.setInt(2, it.getMedicineId());
                    ps.setString(3, it.getDosage());
                    ps.setBoolean(4, it.isMorning());
                    ps.setBoolean(5, it.isAfternoon());
                    ps.setBoolean(6, it.isNight());
                    if (it.getDurationDays()==null) ps.setNull(7, Types.INTEGER); else ps.setInt(7, it.getDurationDays());
                    ps.setString(8, it.getInstructions());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            c.commit();
            return p.getId();
        }
    }

    public Prescription findById(int id) throws SQLException {
        String head = "SELECT pr.*, pat.full_name AS patient_name, u.full_name AS doctor_name " +
                "FROM prescriptions pr " +
                "JOIN patients pat ON pat.id=pr.patient_id " +
                "JOIN doctors d ON d.id=pr.doctor_id " +
                "JOIN users u ON u.id=d.user_id WHERE pr.id=?";
        Prescription p = null;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(head)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Prescription();
                    p.setId(rs.getInt("id"));
                    p.setAppointmentId(rs.getInt("appointment_id"));
                    p.setPatientId(rs.getInt("patient_id"));
                    p.setDoctorId(rs.getInt("doctor_id"));
                    p.setPrescribedDate(rs.getDate("prescribed_date").toLocalDate());
                    p.setNotes(rs.getString("notes"));
                    p.setPatientName(rs.getString("patient_name"));
                    p.setDoctorName(rs.getString("doctor_name"));
                }
            }
        }
        if (p == null) return null;
        String items = "SELECT i.*, m.name AS medicine_name FROM prescription_items i JOIN medicines m ON m.id=i.medicine_id WHERE prescription_id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(items)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PrescriptionItem it = new PrescriptionItem();
                    it.setId(rs.getInt("id"));
                    it.setPrescriptionId(rs.getInt("prescription_id"));
                    it.setMedicineId(rs.getInt("medicine_id"));
                    it.setMedicineName(rs.getString("medicine_name"));
                    it.setDosage(rs.getString("dosage"));
                    it.setMorning(rs.getBoolean("morning"));
                    it.setAfternoon(rs.getBoolean("afternoon"));
                    it.setNight(rs.getBoolean("night"));
                    int dd = rs.getInt("duration_days"); if (!rs.wasNull()) it.setDurationDays(dd);
                    it.setInstructions(rs.getString("instructions"));
                    p.getItems().add(it);
                }
            }
        }
        return p;
    }

    public List<Prescription> findAll() throws SQLException {
        List<Prescription> list = new ArrayList<>();
        String sql = "SELECT pr.id, pr.prescribed_date, pat.full_name AS patient_name, u.full_name AS doctor_name " +
                "FROM prescriptions pr JOIN patients pat ON pat.id=pr.patient_id " +
                "JOIN doctors d ON d.id=pr.doctor_id JOIN users u ON u.id=d.user_id ORDER BY pr.id DESC";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Prescription p = new Prescription();
                p.setId(rs.getInt("id"));
                p.setPrescribedDate(rs.getDate("prescribed_date").toLocalDate());
                p.setPatientName(rs.getString("patient_name"));
                p.setDoctorName(rs.getString("doctor_name"));
                list.add(p);
            }
        }
        return list;
    }
}
