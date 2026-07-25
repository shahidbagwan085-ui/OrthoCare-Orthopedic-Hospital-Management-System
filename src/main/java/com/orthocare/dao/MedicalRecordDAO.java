package com.orthocare.dao;

import com.orthocare.model.MedicalRecord;
import com.orthocare.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDAO {

    public int insert(MedicalRecord r) throws SQLException {
        String sql = "INSERT INTO medical_records(patient_id,record_date,title,description,created_by) VALUES(?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getPatientId());
            ps.setDate(2, Date.valueOf(r.getRecordDate()));
            ps.setString(3, r.getTitle());
            ps.setString(4, r.getDescription());
            if (r.getCreatedBy()==null) ps.setNull(5, Types.INTEGER); else ps.setInt(5, r.getCreatedBy());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) { r.setId(rs.getInt(1)); return r.getId(); } }
        }
        return -1;
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM medical_records WHERE id=?")) {
            ps.setInt(1, id); ps.executeUpdate();
        }
    }

    public List<MedicalRecord> findByPatient(int patientId) throws SQLException {
        List<MedicalRecord> list = new ArrayList<>();
        String sql = "SELECT r.*, u.full_name AS creator_name FROM medical_records r " +
                     "LEFT JOIN users u ON u.id=r.created_by WHERE r.patient_id=? ORDER BY r.record_date DESC, r.id DESC";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MedicalRecord r = new MedicalRecord();
                    r.setId(rs.getInt("id"));
                    r.setPatientId(rs.getInt("patient_id"));
                    r.setRecordDate(rs.getDate("record_date").toLocalDate());
                    r.setTitle(rs.getString("title"));
                    r.setDescription(rs.getString("description"));
                    int cb = rs.getInt("created_by"); if (!rs.wasNull()) r.setCreatedBy(cb);
                    r.setCreatedByName(rs.getString("creator_name"));
                    list.add(r);
                }
            }
        }
        return list;
    }
}
