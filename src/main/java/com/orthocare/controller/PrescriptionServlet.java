package com.orthocare.controller;

import com.orthocare.model.Appointment;
import com.orthocare.model.Prescription;
import com.orthocare.model.PrescriptionItem;
import com.orthocare.service.AppointmentService;
import com.orthocare.service.PrescriptionService;
import com.orthocare.util.ValidationUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/prescriptions/*")
public class PrescriptionServlet extends HttpServlet {
    private final PrescriptionService service = new PrescriptionService();
    private final AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo() == null ? "/" : req.getPathInfo();
        try {
            switch (action) {
                case "/new" -> {
                    req.setAttribute("appointments", appointmentService.list());
                    req.setAttribute("medicines", service.medicines());
                    req.getRequestDispatcher("/WEB-INF/views/prescription/form.jsp").forward(req, resp);
                }
                case "/view" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("prescription", service.get(id));
                    req.getRequestDispatcher("/WEB-INF/views/prescription/view.jsp").forward(req, resp);
                }
                default -> {
                    req.setAttribute("prescriptions", service.list());
                    req.getRequestDispatcher("/WEB-INF/views/prescription/list.jsp").forward(req, resp);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Prescription action failed", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int appointmentId = Integer.parseInt(req.getParameter("appointmentId"));
            Appointment a = appointmentService.get(appointmentId);
            if (a == null) throw new ServletException("Appointment not found");

            Prescription p = new Prescription();
            p.setAppointmentId(appointmentId);
            p.setPatientId(a.getPatientId());
            p.setDoctorId(a.getDoctorId());
            String d = req.getParameter("prescribedDate");
            p.setPrescribedDate(d == null || d.isBlank() ? LocalDate.now() : LocalDate.parse(d));
            p.setNotes(req.getParameter("notes"));

            String[] medIds = req.getParameterValues("medicineId");
            if (medIds != null) {
                for (int i = 0; i < medIds.length; i++) {
                    Integer mid = ValidationUtil.parseInt(medIds[i]);
                    if (mid == null || mid == 0) continue;
                    PrescriptionItem it = new PrescriptionItem();
                    it.setMedicineId(mid);
                    it.setDosage(param(req, "dosage", i));
                    it.setMorning("on".equalsIgnoreCase(param(req, "morning", i)));
                    it.setAfternoon("on".equalsIgnoreCase(param(req, "afternoon", i)));
                    it.setNight("on".equalsIgnoreCase(param(req, "night", i)));
                    it.setDurationDays(ValidationUtil.parseInt(param(req, "durationDays", i)));
                    it.setInstructions(param(req, "instructions", i));
                    p.getItems().add(it);
                }
            }
            service.save(p);
            resp.sendRedirect(req.getContextPath() + "/prescriptions");
        } catch (SQLException e) {
            throw new ServletException("Save prescription failed", e);
        }
    }

    private String param(HttpServletRequest req, String name, int i) {
        String[] arr = req.getParameterValues(name);
        return (arr != null && i < arr.length) ? arr[i] : null;
    }
}
