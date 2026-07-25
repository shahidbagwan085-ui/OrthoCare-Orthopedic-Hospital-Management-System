package com.orthocare.controller;

import com.orthocare.model.Patient;
import com.orthocare.service.PatientService;
import com.orthocare.util.ValidationUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/patients/*")
public class PatientServlet extends HttpServlet {
    private final PatientService service = new PatientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo() == null ? "/" : req.getPathInfo();
        try {
            switch (action) {
                case "/new" -> req.getRequestDispatcher("/WEB-INF/views/patient/form.jsp").forward(req, resp);
                case "/edit" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("patient", service.get(id));
                    req.getRequestDispatcher("/WEB-INF/views/patient/form.jsp").forward(req, resp);
                }
                case "/view" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("patient", service.get(id));
                    req.getRequestDispatcher("/WEB-INF/views/patient/view.jsp").forward(req, resp);
                }
                case "/delete" -> {
                    service.delete(Integer.parseInt(req.getParameter("id")));
                    resp.sendRedirect(req.getContextPath() + "/patients");
                }
                default -> {
                    req.setAttribute("patients", service.list());
                    req.getRequestDispatcher("/WEB-INF/views/patient/list.jsp").forward(req, resp);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Patient action failed", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Patient p = bind(req);
        StringBuilder errors = new StringBuilder();
        if (ValidationUtil.isBlank(p.getFullName())) errors.append("Full name is required. ");
        if (!ValidationUtil.isBlank(p.getEmail()) && !ValidationUtil.isEmail(p.getEmail())) errors.append("Invalid email. ");
        if (!ValidationUtil.isBlank(p.getPhone()) && !ValidationUtil.isPhone(p.getPhone())) errors.append("Invalid phone. ");
        if (errors.length() > 0) {
            req.setAttribute("error", errors.toString());
            req.setAttribute("patient", p);
            req.getRequestDispatcher("/WEB-INF/views/patient/form.jsp").forward(req, resp);
            return;
        }
        try {
            if (p.getId() > 0) service.update(p); else service.save(p);
            resp.sendRedirect(req.getContextPath() + "/patients");
        } catch (SQLException e) {
            throw new ServletException("Save patient failed", e);
        }
    }

    private Patient bind(HttpServletRequest req) {
        Patient p = new Patient();
        Integer id = ValidationUtil.parseInt(req.getParameter("id"));
        if (id != null) p.setId(id);
        p.setPatientCode(ValidationUtil.safe(req.getParameter("patientCode")));
        p.setFullName(ValidationUtil.safe(req.getParameter("fullName")));
        p.setAge(ValidationUtil.parseInt(req.getParameter("age")));
        p.setGender(req.getParameter("gender"));
        String dob = req.getParameter("dob");
        if (dob != null && !dob.isBlank()) p.setDob(LocalDate.parse(dob));
        p.setBloodGroup(req.getParameter("bloodGroup"));
        Double h = ValidationUtil.parseDouble(req.getParameter("heightCm"));
        if (h != null) p.setHeightCm(BigDecimal.valueOf(h));
        Double w = ValidationUtil.parseDouble(req.getParameter("weightKg"));
        if (w != null) p.setWeightKg(BigDecimal.valueOf(w));
        p.setPhone(req.getParameter("phone"));
        p.setEmail(req.getParameter("email"));
        p.setAddress(req.getParameter("address"));
        p.setEmergencyContact(req.getParameter("emergencyContact"));
        p.setAllergies(req.getParameter("allergies"));
        p.setExistingDiseases(req.getParameter("existingDiseases"));
        p.setInsuranceDetails(req.getParameter("insuranceDetails"));
        return p;
    }
}
