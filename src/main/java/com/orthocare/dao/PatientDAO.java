package com.orthocare.dao;

import com.orthocare.model.Patient;
import com.orthocare.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public int insert(Patient p) throws SQLException {
        String sql = "INSERT INTO patients(patient_code,user_id,full_name,age,gender,dob,blood_group," +
                "height_cm,weight_kg,phone,email,address,emergency_contact,allergies,existing_diseases," +
                "insurance_details) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bind(ps, p);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) { p.setId(rs.getInt(1)); return p.getId(); }
            }
        }
        return -1;
    }

    public void update(Patient p) throws SQLException {
        String sql = "UPDATE patients SET patient_code=?, user_id=?, full_name=?, age=?, gender=?, dob=?, blood_group=?, " +
                "height_cm=?, weight_kg=?, phone=?, email=?, address=?, emergency_contact=?, allergies=?, " +
                "existing_diseases=?, insurance_details=? WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            bind(ps, p);
            ps.setInt(17, p.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM patients WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Patient findById(int id) throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM patients WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    public List<Patient> findAll() throws SQLException {
        List<Patient> list = new ArrayList<>();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM patients ORDER BY id DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public long count() throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM patients");
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getLong(1) : 0;
        }
    }

    private void bind(PreparedStatement ps, Patient p) throws SQLException {
        ps.setString(1, p.getPatientCode());
        if (p.getUserId() == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, p.getUserId());
        ps.setString(3, p.getFullName());
        if (p.getAge() == null) ps.setNull(4, Types.INTEGER); else ps.setInt(4, p.getAge());
        ps.setString(5, p.getGender());
        ps.setDate(6, p.getDob() == null ? null : Date.valueOf(p.getDob()));
        ps.setString(7, p.getBloodGroup());
        ps.setBigDecimal(8, p.getHeightCm());
        ps.setBigDecimal(9, p.getWeightKg());
        ps.setString(10, p.getPhone());
        ps.setString(11, p.getEmail());
        ps.setString(12, p.getAddress());
        ps.setString(13, p.getEmergencyContact());
        ps.setString(14, p.getAllergies());
        ps.setString(15, p.getExistingDiseases());
        ps.setString(16, p.getInsuranceDetails());
    }

    private Patient map(ResultSet rs) throws SQLException {
        Patient p = new Patient();
        p.setId(rs.getInt("id"));
        p.setPatientCode(rs.getString("patient_code"));
        int uid = rs.getInt("user_id"); if (!rs.wasNull()) p.setUserId(uid);
        p.setFullName(rs.getString("full_name"));
        int age = rs.getInt("age"); if (!rs.wasNull()) p.setAge(age);
        p.setGender(rs.getString("gender"));
        Date d = rs.getDate("dob"); if (d != null) p.setDob(d.toLocalDate());
        p.setBloodGroup(rs.getString("blood_group"));
        p.setHeightCm(rs.getBigDecimal("height_cm"));
        p.setWeightKg(rs.getBigDecimal("weight_kg"));
        p.setPhone(rs.getString("phone"));
        p.setEmail(rs.getString("email"));
        p.setAddress(rs.getString("address"));
        p.setEmergencyContact(rs.getString("emergency_contact"));
        p.setAllergies(rs.getString("allergies"));
        p.setExistingDiseases(rs.getString("existing_diseases"));
        p.setInsuranceDetails(rs.getString("insurance_details"));
        return p;
    }
}
