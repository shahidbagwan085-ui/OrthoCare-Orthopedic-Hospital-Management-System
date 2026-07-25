package com.orthocare.service;

import com.orthocare.dao.BillDAO;
import com.orthocare.model.Bill;
import com.orthocare.model.Payment;
import com.orthocare.util.CodeGenerator;
import com.orthocare.util.ValidationUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

public class BillingService {
    public static final BigDecimal GST_RATE = new BigDecimal("0.18");
    private final BillDAO dao = new BillDAO();

    /** Computes GST (18%) and net amount from total and discount. */
    public Bill compute(Bill b) {
        BigDecimal total = b.getTotalAmount() == null ? BigDecimal.ZERO : b.getTotalAmount();
        BigDecimal discount = b.getDiscount() == null ? BigDecimal.ZERO : b.getDiscount();
        BigDecimal gst = total.multiply(GST_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal net = total.add(gst).subtract(discount).setScale(2, RoundingMode.HALF_UP);
        b.setGstAmount(gst);
        b.setNetAmount(net);
        return b;
    }

    public int save(Bill b) throws SQLException {
        if (ValidationUtil.isBlank(b.getBillNumber())) b.setBillNumber(CodeGenerator.billNumber());
        compute(b);
        return dao.insert(b);
    }

    public Bill get(int id) throws SQLException { return dao.findById(id); }
    public List<Bill> list() throws SQLException { return dao.findAll(); }

    public int recordPayment(Payment p) throws SQLException {
        int id = dao.insertPayment(p);
        Bill b = dao.findById(p.getBillId());
        if (b != null) {
            BigDecimal paid = dao.totalPaid(b.getId());
            String status = paid.compareTo(b.getNetAmount()) >= 0 ? "PAID"
                          : paid.compareTo(BigDecimal.ZERO) > 0 ? "PARTIAL" : "UNPAID";
            dao.updateStatus(b.getId(), status);
        }
        return id;
    }
}
