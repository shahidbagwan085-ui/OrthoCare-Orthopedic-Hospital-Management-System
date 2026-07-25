package com.orthocare.service;

import com.orthocare.dao.MedicineDAO;
import com.orthocare.dao.PrescriptionDAO;
import com.orthocare.model.Medicine;
import com.orthocare.model.Prescription;

import java.sql.SQLException;
import java.util.List;

public class PrescriptionService {
    private final PrescriptionDAO dao = new PrescriptionDAO();
    private final MedicineDAO medDao = new MedicineDAO();

    public int save(Prescription p) throws SQLException { return dao.insert(p); }
    public Prescription get(int id) throws SQLException { return dao.findById(id); }
    public List<Prescription> list() throws SQLException { return dao.findAll(); }
    public List<Medicine> medicines() throws SQLException { return medDao.findAll(); }
}
