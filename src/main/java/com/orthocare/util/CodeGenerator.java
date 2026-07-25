package com.orthocare.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public final class CodeGenerator {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private CodeGenerator() {}

    public static String patientCode() { return "PAT-" + stamp(); }
    public static String appointmentCode() { return "APT-" + stamp(); }
    public static String billNumber() { return "BILL-" + stamp(); }

    private static String stamp() {
        return LocalDateTime.now().format(FMT)
                + "-" + ThreadLocalRandom.current().nextInt(100, 999);
    }
}
