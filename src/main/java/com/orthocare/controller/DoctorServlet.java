package com.orthocare.controller;

import com.orthocare.dao.UserDAO;
import com.orthocare.model.Doctor;
import com.orthocare.model.User;
import com.orthocare.service.AuthService;
import com.orthocare.service.DoctorService;
import com.orthocare.util.ValidationUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/doctors/*")
public class DoctorServlet extends HttpServlet {
    private final DoctorService service = new DoctorService();
    private final AuthService authService = new AuthService();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo() == null ? "/" : req.getPathInfo();
        try {
            switch (action) {
                case "/new" -> req.getRequestDispatcher("/WEB-INF/views/doctor/form.jsp").forward(req, resp);
                case "/edit" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("doctor", service.get(id));
                    req.getRequestDispatcher("/WEB-INF/views/doctor/form.jsp").forward(req, resp);
                }
                case "/delete" -> {
                    service.delete(Integer.parseInt(req.getParameter("id")));
                    resp.sendRedirect(req.getContextPath() + "/doctors");
                }
                default -> {
                    req.setAttribute("doctors", service.list());
                    req.getRequestDispatcher("/WEB-INF/views/doctor/list.jsp").forward(req, resp);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Doctor action failed", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Doctor d = new Doctor();
            Integer id = ValidationUtil.parseInt(req.getParameter("id"));
            if (id != null) d.setId(id);

            if (d.getId() == 0) {
                // Create linked user
                User u = new User();
                u.setUsername(ValidationUtil.safe(req.getParameter("username")));
                u.setFullName(ValidationUtil.safe(req.getParameter("fullName")));
                u.setEmail(req.getParameter("email"));
                u.setPhone(req.getParameter("contact"));
                u.setRole("DOCTOR");
                String pwd = req.getParameter("password");
                if (ValidationUtil.isBlank(u.getUsername()) || ValidationUtil.isBlank(pwd)) {
                    req.setAttribute("error", "Username and password are required.");
                    req.getRequestDispatcher("/WEB-INF/views/doctor/form.jsp").forward(req, resp);
                    return;
                }
                int uid = authService.register(u, pwd);
                d.setUserId(uid);
            } else {
                Integer uid = ValidationUtil.parseInt(req.getParameter("userId"));
                if (uid != null) d.setUserId(uid);
            }

            d.setQualification(req.getParameter("qualification"));
            d.setExperienceYears(ValidationUtil.parseInt(req.getParameter("experienceYears")));
            d.setSpecialization(req.getParameter("specialization"));
            Double fee = ValidationUtil.parseDouble(req.getParameter("consultationFee"));
            if (fee != null) d.setConsultationFee(BigDecimal.valueOf(fee));
            d.setAvailableDays(req.getParameter("availableDays"));
            d.setAvailableTimes(req.getParameter("availableTimes"));
            d.setDepartment(req.getParameter("department"));
            d.setContact(req.getParameter("contact"));
            d.setEmail(req.getParameter("email"));

            if (d.getId() > 0) service.update(d); else service.save(d);
            // Also keep user record's basic info in sync on edit
            if (d.getId() > 0 && d.getUserId() > 0) {
                User existing = userDAO.findById(d.getUserId());
                if (existing != null) {
                    existing.setFullName(ValidationUtil.safe(req.getParameter("fullName")));
                    existing.setEmail(req.getParameter("email"));
                    existing.setPhone(req.getParameter("contact"));
                    userDAO.update(existing);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/doctors");
        } catch (SQLException e) {
            throw new ServletException("Save doctor failed", e);
        }
    }
}
