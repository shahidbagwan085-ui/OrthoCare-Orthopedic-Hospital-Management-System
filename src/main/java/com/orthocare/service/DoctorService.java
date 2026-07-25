package com.orthocare.service;

import com.orthocare.dao.DoctorDAO;
import com.orthocare.model.Doctor;

import java.sql.SQLException;
import java.util.List;

public class DoctorService {
    private final DoctorDAO dao = new DoctorDAO();

    public int save(Doctor d) throws SQLException { return dao.insert(d); }
    public void update(Doctor d) throws SQLException { dao.update(d); }
    public void delete(int id) throws SQLException { dao.delete(id); }
    public Doctor get(int id) throws SQLException { return dao.findById(id); }
    public List<Doctor> list() throws SQLException { return dao.findAll(); }
}
