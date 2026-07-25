package com.orthocare.controller;

import com.orthocare.model.Appointment;
import com.orthocare.service.AppointmentService;
import com.orthocare.service.DoctorService;
import com.orthocare.service.PatientService;
import com.orthocare.util.ValidationUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet("/appointments/*")
public class AppointmentServlet extends HttpServlet {
    private final AppointmentService service = new AppointmentService();
    private final PatientService patientService = new PatientService();
    private final DoctorService doctorService = new DoctorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo() == null ? "/" : req.getPathInfo();
        try {
            switch (action) {
                case "/new" -> {
                    req.setAttribute("patients", patientService.list());
                    req.setAttribute("doctors", doctorService.list());
                    req.getRequestDispatcher("/WEB-INF/views/appointment/form.jsp").forward(req, resp);
                }
                case "/edit" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("appointment", service.get(id));
                    req.setAttribute("patients", patientService.list());
                    req.setAttribute("doctors", doctorService.list());
                    req.getRequestDispatcher("/WEB-INF/views/appointment/form.jsp").forward(req, resp);
                }
                case "/cancel" -> {
                    service.cancel(Integer.parseInt(req.getParameter("id")));
                    resp.sendRedirect(req.getContextPath() + "/appointments");
                }
                case "/status" -> {
                    service.updateStatus(Integer.parseInt(req.getParameter("id")), req.getParameter("status"));
                    resp.sendRedirect(req.getContextPath() + "/appointments");
                }
                case "/delete" -> {
                    service.delete(Integer.parseInt(req.getParameter("id")));
                    resp.sendRedirect(req.getContextPath() + "/appointments");
                }
                default -> {
                    req.setAttribute("appointments", service.list());
                    req.getRequestDispatcher("/WEB-INF/views/appointment/list.jsp").forward(req, resp);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Appointment action failed", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Appointment a = new Appointment();
            Integer id = ValidationUtil.parseInt(req.getParameter("id"));
            if (id != null) a.setId(id);
            a.setAppointmentCode(ValidationUtil.safe(req.getParameter("appointmentCode")));
            a.setPatientId(ValidationUtil.parseInt(req.getParameter("patientId")));
            a.setDoctorId(ValidationUtil.parseInt(req.getParameter("doctorId")));
            a.setDepartment(req.getParameter("department"));
            String dt = req.getParameter("appointmentDt");
            if (dt != null && !dt.isBlank()) a.setAppointmentDt(LocalDateTime.parse(dt));
            a.setReason(req.getParameter("reason"));
            a.setStatus(req.getParameter("status"));

            if (a.getPatientId() == 0 || a.getDoctorId() == 0 || a.getAppointmentDt() == null) {
                req.setAttribute("error", "Patient, doctor, and appointment date/time are required.");
                req.setAttribute("appointment", a);
                req.setAttribute("patients", patientService.list());
                req.setAttribute("doctors", doctorService.list());
                req.getRequestDispatcher("/WEB-INF/views/appointment/form.jsp").forward(req, resp);
                return;
            }
            if (a.getId() > 0) service.update(a); else service.save(a);
            resp.sendRedirect(req.getContextPath() + "/appointments");
        } catch (SQLException e) {
            throw new ServletException("Save appointment failed", e);
        }
    }
}
