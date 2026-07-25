package com.orthocare.dao;

import com.orthocare.model.Doctor;
import com.orthocare.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    public int insert(Doctor d) throws SQLException {
        String sql = "INSERT INTO doctors(user_id,qualification,experience_years,specialization,consultation_fee," +
                "available_days,available_times,department,contact,email) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bind(ps, d);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) { d.setId(rs.getInt(1)); return d.getId(); }
            }
        }
        return -1;
    }

    public void update(Doctor d) throws SQLException {
        String sql = "UPDATE doctors SET user_id=?, qualification=?, experience_years=?, specialization=?, " +
                "consultation_fee=?, available_days=?, available_times=?, department=?, contact=?, email=? WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            bind(ps, d);
            ps.setInt(11, d.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM doctors WHERE id=?")) {
            ps.setInt(1, id); ps.executeUpdate();
        }
    }

    public Doctor findById(int id) throws SQLException {
        String sql = "SELECT d.*, u.full_name AS user_full_name FROM doctors d JOIN users u ON u.id=d.user_id WHERE d.id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    public List<Doctor> findAll() throws SQLException {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT d.*, u.full_name AS user_full_name FROM doctors d JOIN users u ON u.id=d.user_id ORDER BY d.id DESC";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public long count() throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM doctors");
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getLong(1) : 0;
        }
    }

    private void bind(PreparedStatement ps, Doctor d) throws SQLException {
        ps.setInt(1, d.getUserId());
        ps.setString(2, d.getQualification());
        if (d.getExperienceYears()==null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, d.getExperienceYears());
        ps.setString(4, d.getSpecialization());
        ps.setBigDecimal(5, d.getConsultationFee());
        ps.setString(6, d.getAvailableDays());
        ps.setString(7, d.getAvailableTimes());
        ps.setString(8, d.getDepartment());
        ps.setString(9, d.getContact());
        ps.setString(10, d.getEmail());
    }

    private Doctor map(ResultSet rs) throws SQLException {
        Doctor d = new Doctor();
        d.setId(rs.getInt("id"));
        d.setUserId(rs.getInt("user_id"));
        d.setFullName(rs.getString("user_full_name"));
        d.setQualification(rs.getString("qualification"));
        int y = rs.getInt("experience_years"); if (!rs.wasNull()) d.setExperienceYears(y);
        d.setSpecialization(rs.getString("specialization"));
        d.setConsultationFee(rs.getBigDecimal("consultation_fee"));
        d.setAvailableDays(rs.getString("available_days"));
        d.setAvailableTimes(rs.getString("available_times"));
        d.setDepartment(rs.getString("department"));
        d.setContact(rs.getString("contact"));
        d.setEmail(rs.getString("email"));
        return d;
    }
}
