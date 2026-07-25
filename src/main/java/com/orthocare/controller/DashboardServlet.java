package com.orthocare.controller;

import com.orthocare.service.DashboardService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final DashboardService service = new DashboardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("stats", service.getStats());
            req.getRequestDispatcher("/WEB-INF/views/dashboard/index.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Failed to load dashboard", e);
        }
    }
}
