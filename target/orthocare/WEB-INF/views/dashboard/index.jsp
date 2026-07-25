<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:layout title="Dashboard" active="dashboard">
    <div class="page-header"><h1>Dashboard</h1></div>
    <div class="stats">
        <div class="stat"><h3>Total Patients</h3><div class="v">${stats.totalPatients}</div></div>
        <div class="stat"><h3>Total Doctors</h3><div class="v">${stats.totalDoctors}</div></div>
        <div class="stat"><h3>Total Appointments</h3><div class="v">${stats.totalAppointments}</div></div>
        <div class="stat"><h3>Today's Appointments</h3><div class="v">${stats.todaysAppointments}</div></div>
        <div class="stat"><h3>Total Bills</h3><div class="v">${stats.totalBills}</div></div>
        <div class="stat"><h3>Total Revenue</h3><div class="v"><fmt:formatNumber value="${stats.totalRevenue}" type="currency" currencySymbol="₹"/></div></div>
    </div>
</t:layout>
