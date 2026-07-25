<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Patients" active="patients">
    <div class="page-header">
        <h1>Patients</h1>
        <a href="${pageContext.request.contextPath}/patients/new" class="btn btn-primary">+ New Patient</a>
    </div>
    <table>
        <thead><tr><th>Code</th><th>Name</th><th>Age</th><th>Gender</th><th>Phone</th><th>Blood</th><th></th></tr></thead>
        <tbody>
        <c:forEach var="p" items="${patients}">
            <tr>
                <td>${p.patientCode}</td>
                <td><c:out value="${p.fullName}"/></td>
                <td>${p.age}</td>
                <td>${p.gender}</td>
                <td>${p.phone}</td>
                <td>${p.bloodGroup}</td>
                <td>
                    <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/patients/view?id=${p.id}">View</a>
                    <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/patients/edit?id=${p.id}">Edit</a>
                    <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/patients/delete?id=${p.id}" onclick="return confirm('Delete this patient?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty patients}"><tr><td colspan="7" style="text-align:center;color:#94a3b8;padding:24px">No patients yet.</td></tr></c:if>
        </tbody>
    </table>
</t:layout>
