package com.orthocare.controller;

import com.orthocare.model.MedicalRecord;
import com.orthocare.model.User;
import com.orthocare.service.MedicalRecordService;
import com.orthocare.service.PatientService;
import com.orthocare.util.ValidationUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/records/*")
public class MedicalRecordServlet extends HttpServlet {
    private final MedicalRecordService service = new MedicalRecordService();
    private final PatientService patientService = new PatientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo() == null ? "/" : req.getPathInfo();
        try {
            if ("/delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                int patientId = Integer.parseInt(req.getParameter("patientId"));
                service.delete(id);
                resp.sendRedirect(req.getContextPath() + "/records?patientId=" + patientId);
                return;
            }
            Integer patientId = ValidationUtil.parseInt(req.getParameter("patientId"));
            if (patientId == null) {
                req.setAttribute("patients", patientService.list());
                req.getRequestDispatcher("/WEB-INF/views/record/select-patient.jsp").forward(req, resp);
                return;
            }
            req.setAttribute("patient", patientService.get(patientId));
            req.setAttribute("records", service.forPatient(patientId));
            req.getRequestDispatcher("/WEB-INF/views/record/list.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Records action failed", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            MedicalRecord r = new MedicalRecord();
            r.setPatientId(Integer.parseInt(req.getParameter("patientId")));
            String d = req.getParameter("recordDate");
            r.setRecordDate(d == null || d.isBlank() ? LocalDate.now() : LocalDate.parse(d));
            r.setTitle(ValidationUtil.safe(req.getParameter("title")));
            r.setDescription(req.getParameter("description"));
            User u = (User) req.getSession().getAttribute("currentUser");
            if (u != null) r.setCreatedBy(u.getId());
            if (ValidationUtil.isBlank(r.getTitle())) {
                req.setAttribute("error", "Title is required.");
            } else {
                service.save(r);
            }
            resp.sendRedirect(req.getContextPath() + "/records?patientId=" + r.getPatientId());
        } catch (SQLException e) {
            throw new ServletException("Save record failed", e);
        }
    }
}
