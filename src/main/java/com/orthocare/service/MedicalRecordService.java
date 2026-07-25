package com.orthocare.service;

import com.orthocare.dao.MedicalRecordDAO;
import com.orthocare.model.MedicalRecord;

import java.sql.SQLException;
import java.util.List;

public class MedicalRecordService {
    private final MedicalRecordDAO dao = new MedicalRecordDAO();

    public int save(MedicalRecord r) throws SQLException { return dao.insert(r); }
    public void delete(int id) throws SQLException { dao.delete(id); }
    public List<MedicalRecord> forPatient(int patientId) throws SQLException { return dao.findByPatient(patientId); }
}
