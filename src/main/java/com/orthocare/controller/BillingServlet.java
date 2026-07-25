package com.orthocare.controller;

import com.orthocare.model.Bill;
import com.orthocare.model.Payment;
import com.orthocare.service.AppointmentService;
import com.orthocare.service.BillingService;
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

@WebServlet("/billing/*")
public class BillingServlet extends HttpServlet {
    private final BillingService service = new BillingService();
    private final PatientService patientService = new PatientService();
    private final AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo() == null ? "/" : req.getPathInfo();
        try {
            switch (action) {
                case "/new" -> {
                    req.setAttribute("patients", patientService.list());
                    req.setAttribute("appointments", appointmentService.list());
                    req.getRequestDispatcher("/WEB-INF/views/billing/form.jsp").forward(req, resp);
                }
                case "/view" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("bill", service.get(id));
                    req.getRequestDispatcher("/WEB-INF/views/billing/view.jsp").forward(req, resp);
                }
                case "/pay" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("bill", service.get(id));
                    req.getRequestDispatcher("/WEB-INF/views/billing/payment.jsp").forward(req, resp);
                }
                default -> {
                    req.setAttribute("bills", service.list());
                    req.getRequestDispatcher("/WEB-INF/views/billing/list.jsp").forward(req, resp);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Billing action failed", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo() == null ? "/" : req.getPathInfo();
        try {
            if ("/pay".equals(action)) {
                Payment p = new Payment();
                p.setBillId(Integer.parseInt(req.getParameter("billId")));
                Double amount = ValidationUtil.parseDouble(req.getParameter("amount"));
                if (amount == null || amount <= 0) {
                    req.setAttribute("error", "Enter a valid amount.");
                    req.setAttribute("bill", service.get(p.getBillId()));
                    req.getRequestDispatcher("/WEB-INF/views/billing/payment.jsp").forward(req, resp);
                    return;
                }
                p.setAmount(BigDecimal.valueOf(amount));
                p.setMethod(req.getParameter("method"));
                p.setReferenceId(req.getParameter("referenceId"));
                service.recordPayment(p);
                resp.sendRedirect(req.getContextPath() + "/billing/view?id=" + p.getBillId());
                return;
            }

            Bill b = new Bill();
            b.setPatientId(Integer.parseInt(req.getParameter("patientId")));
            Integer apt = ValidationUtil.parseInt(req.getParameter("appointmentId"));
            if (apt != null && apt > 0) b.setAppointmentId(apt);
            Double total = ValidationUtil.parseDouble(req.getParameter("totalAmount"));
            Double discount = ValidationUtil.parseDouble(req.getParameter("discount"));
            b.setTotalAmount(total == null ? BigDecimal.ZERO : BigDecimal.valueOf(total));
            b.setDiscount(discount == null ? BigDecimal.ZERO : BigDecimal.valueOf(discount));
            service.save(b);
            resp.sendRedirect(req.getContextPath() + "/billing/view?id=" + b.getId());
        } catch (SQLException e) {
            throw new ServletException("Save bill/payment failed", e);
        }
    }
}
