<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Record Payment" active="billing">
    <div class="page-header"><h1>Record Payment — ${bill.billNumber}</h1></div>
    <c:if test="${not empty error}"><div class="alert alert-error"><c:out value="${error}"/></div></c:if>
    <div class="card">
        <p><b>Patient:</b> <c:out value="${bill.patientName}"/> &nbsp; | &nbsp;
           <b>Net:</b> <fmt:formatNumber value="${bill.netAmount}" type="currency" currencySymbol="₹"/></p>
        <form method="post" action="${pageContext.request.contextPath}/billing/pay">
            <input type="hidden" name="billId" value="${bill.id}">
            <div class="row">
                <div class="field"><label>Amount *</label><input name="amount" type="number" step="0.01" required></div>
                <div class="field"><label>Method *</label>
                    <select name="method" required>
                        <option value="CASH">Cash</option>
                        <option value="CARD">Card</option>
                        <option value="UPI">UPI</option>
                    </select>
                </div>
                <div class="field"><label>Reference ID</label><input name="referenceId"></div>
            </div>
            <div class="actions">
                <button class="btn btn-primary">Record Payment</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/billing/view?id=${bill.id}">Cancel</a>
            </div>
        </form>
    </div>
</t:layout>
