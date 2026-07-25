<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Medical Records" active="records">
    <div class="page-header">
        <h1>Records — <c:out value="${patient.fullName}"/></h1>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/records">Change patient</a>
    </div>

    <div class="card">
        <h3 style="margin-top:0">Add Record</h3>
        <form method="post" action="${pageContext.request.contextPath}/records">
            <input type="hidden" name="patientId" value="${patient.id}">
            <div class="row">
                <div class="field"><label>Date</label><input name="recordDate" type="date"></div>
                <div class="field"><label>Title / Diagnosis *</label><input name="title" required></div>
            </div>
            <div class="field"><label>Description / Notes</label><textarea name="description" rows="4"></textarea></div>
            <div class="actions"><button class="btn btn-primary">Add Record</button></div>
        </form>
    </div>

    <table>
        <thead><tr><th>Date</th><th>Title</th><th>Notes</th><th>By</th><th></th></tr></thead>
        <tbody>
        <c:forEach var="r" items="${records}">
            <tr>
                <td>${r.recordDate}</td>
                <td><c:out value="${r.title}"/></td>
                <td><c:out value="${r.description}"/></td>
                <td><c:out value="${r.createdByName}"/></td>
                <td><a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/records/delete?id=${r.id}&patientId=${patient.id}" onclick="return confirm('Delete?')">Delete</a></td>
            </tr>
        </c:forEach>
        <c:if test="${empty records}"><tr><td colspan="5" style="text-align:center;color:#94a3b8;padding:24px">No records yet.</td></tr></c:if>
        </tbody>
    </table>
</t:layout>
