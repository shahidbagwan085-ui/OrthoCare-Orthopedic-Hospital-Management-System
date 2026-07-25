<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Appointments" active="appointments">
    <div class="page-header">
        <h1>Appointments</h1>
        <a href="${pageContext.request.contextPath}/appointments/new" class="btn btn-primary">+ New Appointment</a>
    </div>
    <table>
        <thead><tr><th>Code</th><th>Patient</th><th>Doctor</th><th>Dept</th><th>Date/Time</th><th>Reason</th><th>Status</th><th></th></tr></thead>
        <tbody>
        <c:forEach var="a" items="${appointments}">
            <tr>
                <td>${a.appointmentCode}</td>
                <td><c:out value="${a.patientName}"/></td>
                <td><c:out value="${a.doctorName}"/></td>
                <td>${a.department}</td>
                <td>${a.formattedDt}</td>
                <td><c:out value="${a.reason}"/></td>
                <td><span class="badge badge-${a.status.toLowerCase()}">${a.status}</span></td>
                <td>
                    <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/appointments/edit?id=${a.id}">Edit</a>
                    <c:if test="${a.status ne 'CANCELLED' and a.status ne 'COMPLETED'}">
                        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/appointments/status?id=${a.id}&status=CONFIRMED">Confirm</a>
                        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/appointments/status?id=${a.id}&status=COMPLETED">Complete</a>
                        <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/appointments/cancel?id=${a.id}" onclick="return confirm('Cancel appointment?')">Cancel</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty appointments}"><tr><td colspan="8" style="text-align:center;color:#94a3b8;padding:24px">No appointments yet.</td></tr></c:if>
        </tbody>
    </table>
</t:layout>
