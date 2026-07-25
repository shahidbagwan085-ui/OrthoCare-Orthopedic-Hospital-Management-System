package com.orthocare.service;

import com.orthocare.dao.PatientDAO;
import com.orthocare.model.Patient;
import com.orthocare.util.CodeGenerator;
import com.orthocare.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

public class PatientService {
    private final PatientDAO dao = new PatientDAO();

    public int save(Patient p) throws SQLException {
        if (ValidationUtil.isBlank(p.getPatientCode())) p.setPatientCode(CodeGenerator.patientCode());
        return dao.insert(p);
    }
    public void update(Patient p) throws SQLException { dao.update(p); }
    public void delete(int id) throws SQLException { dao.delete(id); }
    public Patient get(int id) throws SQLException { return dao.findById(id); }
    public List<Patient> list() throws SQLException { return dao.findAll(); }
}
