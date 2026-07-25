<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Prescription" active="prescriptions">
    <div class="page-header"><h1>Prescription #${prescription.id}</h1></div>
    <div class="card">
        <p><b>Patient:</b> <c:out value="${prescription.patientName}"/> &nbsp; <b>Doctor:</b> <c:out value="${prescription.doctorName}"/></p>
        <p><b>Date:</b> ${prescription.prescribedDate}</p>
        <p><b>Notes:</b> <c:out value="${prescription.notes}"/></p>
    </div>
    <table>
        <thead><tr><th>Medicine</th><th>Dosage</th><th>M</th><th>A</th><th>N</th><th>Days</th><th>Instructions</th></tr></thead>
        <tbody>
        <c:forEach var="it" items="${prescription.items}">
            <tr>
                <td><c:out value="${it.medicineName}"/></td>
                <td>${it.dosage}</td>
                <td>${it.morning?'✓':''}</td>
                <td>${it.afternoon?'✓':''}</td>
                <td>${it.night?'✓':''}</td>
                <td>${it.durationDays}</td>
                <td><c:out value="${it.instructions}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</t:layout>
