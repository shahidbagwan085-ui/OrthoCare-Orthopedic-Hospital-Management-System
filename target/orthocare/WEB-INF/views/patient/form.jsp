<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Patient Form" active="patients">
    <div class="page-header"><h1><c:choose><c:when test="${patient.id gt 0}">Edit Patient</c:when><c:otherwise>New Patient</c:otherwise></c:choose></h1></div>
    <c:if test="${not empty error}"><div class="alert alert-error"><c:out value="${error}"/></div></c:if>
    <div class="card">
    <form method="post" action="${pageContext.request.contextPath}/patients">
        <input type="hidden" name="id" value="${patient.id}">
        <div class="row">
            <div class="field"><label>Patient Code (auto if blank)</label><input name="patientCode" value="${patient.patientCode}"></div>
            <div class="field"><label>Full Name *</label><input name="fullName" required value="<c:out value='${patient.fullName}'/>"></div>
            <div class="field"><label>Age</label><input name="age" type="number" value="${patient.age}"></div>
            <div class="field"><label>Gender</label>
                <select name="gender">
                    <option value="">--</option>
                    <c:forEach var="g" items="MALE,FEMALE,OTHER">
                        <option value="${g}" ${patient.gender==g?'selected':''}>${g}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="field"><label>Date of Birth</label><input name="dob" type="date" value="${patient.dob}"></div>
            <div class="field"><label>Blood Group</label><input name="bloodGroup" value="${patient.bloodGroup}"></div>
            <div class="field"><label>Height (cm)</label><input name="heightCm" type="number" step="0.01" value="${patient.heightCm}"></div>
            <div class="field"><label>Weight (kg)</label><input name="weightKg" type="number" step="0.01" value="${patient.weightKg}"></div>
            <div class="field"><label>Phone</label><input name="phone" value="${patient.phone}"></div>
            <div class="field"><label>Email</label><input name="email" type="email" value="${patient.email}"></div>
            <div class="field"><label>Emergency Contact</label><input name="emergencyContact" value="${patient.emergencyContact}"></div>
        </div>
        <div class="field"><label>Address</label><textarea name="address" rows="2"><c:out value='${patient.address}'/></textarea></div>
        <div class="row">
            <div class="field"><label>Allergies</label><textarea name="allergies" rows="2"><c:out value='${patient.allergies}'/></textarea></div>
            <div class="field"><label>Existing Diseases</label><textarea name="existingDiseases" rows="2"><c:out value='${patient.existingDiseases}'/></textarea></div>
            <div class="field"><label>Insurance Details</label><textarea name="insuranceDetails" rows="2"><c:out value='${patient.insuranceDetails}'/></textarea></div>
        </div>
        <div class="actions">
            <button class="btn btn-primary" type="submit">Save</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/patients">Cancel</a>
        </div>
    </form>
    </div>
</t:layout>
