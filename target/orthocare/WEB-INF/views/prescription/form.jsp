<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="New Prescription" active="prescriptions">
    <div class="page-header"><h1>New Prescription</h1></div>
    <div class="card">
    <form method="post" action="${pageContext.request.contextPath}/prescriptions">
        <div class="row">
            <div class="field"><label>Appointment *</label>
                <select name="appointmentId" required>
                    <option value="">-- select --</option>
                    <c:forEach var="a" items="${appointments}">
                        <option value="${a.id}">${a.appointmentCode} — <c:out value="${a.patientName}"/> / <c:out value="${a.doctorName}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="field"><label>Prescribed Date</label><input type="date" name="prescribedDate"></div>
        </div>
        <div class="field"><label>Notes</label><textarea name="notes" rows="2"></textarea></div>

        <h3>Medicines</h3>
        <div id="items">
            <c:forEach begin="0" end="2" var="i">
                <div class="item-row">
                    <div><label>Medicine</label>
                        <select name="medicineId">
                            <option value="0">-- none --</option>
                            <c:forEach var="m" items="${medicines}">
                                <option value="${m.id}"><c:out value="${m.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div><label>Dosage</label><input name="dosage" placeholder="e.g. 1 tab"></div>
                    <div class="check"><label>M</label><input type="checkbox" name="morning"></div>
                    <div class="check"><label>A</label><input type="checkbox" name="afternoon"></div>
                    <div class="check"><label>N</label><input type="checkbox" name="night"></div>
                    <div><label>Days</label><input name="durationDays" type="number"></div>
                    <div><label>Instructions</label><input name="instructions"></div>
                    <div></div>
                </div>
            </c:forEach>
        </div>
        <div class="actions">
            <button class="btn btn-primary">Save Prescription</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/prescriptions">Cancel</a>
        </div>
    </form>
    </div>
</t:layout>
