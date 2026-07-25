<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Patient Details" active="patients">
    <div class="page-header">
        <h1><c:out value="${patient.fullName}"/> <small style="color:#64748b;font-size:14px">(${patient.patientCode})</small></h1>
        <div>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/records?patientId=${patient.id}">Medical Records</a>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/patients/edit?id=${patient.id}">Edit</a>
        </div>
    </div>
    <div class="card">
        <div class="row">
            <div><b>Age:</b> ${patient.age}</div>
            <div><b>Gender:</b> ${patient.gender}</div>
            <div><b>DOB:</b> ${patient.dob}</div>
            <div><b>Blood Group:</b> ${patient.bloodGroup}</div>
            <div><b>Height:</b> ${patient.heightCm} cm</div>
            <div><b>Weight:</b> ${patient.weightKg} kg</div>
            <div><b>Phone:</b> ${patient.phone}</div>
            <div><b>Email:</b> ${patient.email}</div>
            <div><b>Emergency:</b> ${patient.emergencyContact}</div>
        </div>
        <p><b>Address:</b> <c:out value="${patient.address}"/></p>
        <p><b>Allergies:</b> <c:out value="${patient.allergies}"/></p>
        <p><b>Existing Diseases:</b> <c:out value="${patient.existingDiseases}"/></p>
        <p><b>Insurance:</b> <c:out value="${patient.insuranceDetails}"/></p>
    </div>
</t:layout>
