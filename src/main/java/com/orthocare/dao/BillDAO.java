package com.orthocare.dao;

import com.orthocare.model.Bill;
import com.orthocare.model.Payment;
import com.orthocare.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    public int insert(Bill b) throws SQLException {
        String sql = "INSERT INTO bills(bill_number,patient_id,appointment_id,total_amount,gst_amount,discount,net_amount,status) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, b.getBillNumber());
            ps.setInt(2, b.getPatientId());
            if (b.getAppointmentId()==null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, b.getAppointmentId());
            ps.setBigDecimal(4, b.getTotalAmount());
            ps.setBigDecimal(5, b.getGstAmount());
            ps.setBigDecimal(6, b.getDiscount());
            ps.setBigDecimal(7, b.getNetAmount());
            ps.setString(8, b.getStatus() == null ? "UNPAID" : b.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) { b.setId(rs.getInt(1)); return b.getId(); } }
        }
        return -1;
    }

    public void updateStatus(int billId, String status) throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE bills SET status=? WHERE id=?")) {
            ps.setString(1, status); ps.setInt(2, billId); ps.executeUpdate();
        }
    }

    public Bill findById(int id) throws SQLException {
        String sql = "SELECT b.*, p.full_name AS patient_name FROM bills b JOIN patients p ON p.id=b.patient_id WHERE b.id=?";
        Bill b = null;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) b = map(rs); }
        }
        if (b == null) return null;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM payments WHERE bill_id=? ORDER BY paid_at")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Payment p = new Payment();
                    p.setId(rs.getInt("id"));
                    p.setBillId(rs.getInt("bill_id"));
                    p.setAmount(rs.getBigDecimal("amount"));
                    Timestamp ts = rs.getTimestamp("paid_at"); if (ts != null) p.setPaidAt(ts.toLocalDateTime());
                    p.setMethod(rs.getString("method"));
                    p.setReferenceId(rs.getString("reference_id"));
                    b.getPayments().add(p);
                }
            }
        }
        return b;
    }

    public List<Bill> findAll() throws SQLException {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT b.*, p.full_name AS patient_name FROM bills b JOIN patients p ON p.id=b.patient_id ORDER BY b.id DESC";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public long count() throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM bills");
             ResultSet rs = ps.executeQuery()) { return rs.next() ? rs.getLong(1) : 0; }
    }

    public BigDecimal totalRevenue() throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COALESCE(SUM(amount),0) FROM payments");
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getBigDecimal(1) : BigDecimal.ZERO;
        }
    }

    public int insertPayment(Payment p) throws SQLException {
        String sql = "INSERT INTO payments(bill_id,amount,method,reference_id) VALUES(?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getBillId());
            ps.setBigDecimal(2, p.getAmount());
            ps.setString(3, p.getMethod());
            ps.setString(4, p.getReferenceId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) { p.setId(rs.getInt(1)); return p.getId(); } }
        }
        return -1;
    }

    public BigDecimal totalPaid(int billId) throws SQLException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COALESCE(SUM(amount),0) FROM payments WHERE bill_id=?")) {
            ps.setInt(1, billId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? rs.getBigDecimal(1) : BigDecimal.ZERO; }
        }
    }

    private Bill map(ResultSet rs) throws SQLException {
        Bill b = new Bill();
        b.setId(rs.getInt("id"));
        b.setBillNumber(rs.getString("bill_number"));
        b.setPatientId(rs.getInt("patient_id"));
        int ap = rs.getInt("appointment_id"); if (!rs.wasNull()) b.setAppointmentId(ap);
        b.setTotalAmount(rs.getBigDecimal("total_amount"));
        b.setGstAmount(rs.getBigDecimal("gst_amount"));
        b.setDiscount(rs.getBigDecimal("discount"));
        b.setNetAmount(rs.getBigDecimal("net_amount"));
        b.setStatus(rs.getString("status"));
        Timestamp ts = rs.getTimestamp("created_at"); if (ts!=null) b.setCreatedAt(ts.toLocalDateTime());
        b.setPatientName(rs.getString("patient_name"));
        return b;
    }
}
