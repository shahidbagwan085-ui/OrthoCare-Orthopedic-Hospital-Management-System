package com.orthocare.dao;

import com.orthocare.model.Medicine;
import com.orthocare.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAO {

    public List<Medicine> findAll() throws SQLException {
        List<Medicine> list = new ArrayList<>();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM medicines ORDER BY name");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Medicine m = new Medicine();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                m.setUnit(rs.getString("unit"));
                m.setDescription(rs.getString("description"));
                list.add(m);
            }
        }
        return list;
    }
}
