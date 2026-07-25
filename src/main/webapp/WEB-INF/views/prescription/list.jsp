<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Prescriptions" active="prescriptions">
    <div class="page-header">
        <h1>Prescriptions</h1>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/prescriptions/new">+ New Prescription</a>
    </div>
    <table>
        <thead><tr><th>#</th><th>Date</th><th>Patient</th><th>Doctor</th><th></th></tr></thead>
        <tbody>
        <c:forEach var="p" items="${prescriptions}">
            <tr>
                <td>${p.id}</td>
                <td>${p.prescribedDate}</td>
                <td><c:out value="${p.patientName}"/></td>
                <td><c:out value="${p.doctorName}"/></td>
                <td><a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/prescriptions/view?id=${p.id}">View</a></td>
            </tr>
        </c:forEach>
        <c:if test="${empty prescriptions}"><tr><td colspan="5" style="text-align:center;color:#94a3b8;padding:24px">No prescriptions yet.</td></tr></c:if>
        </tbody>
    </table>
</t:layout>
