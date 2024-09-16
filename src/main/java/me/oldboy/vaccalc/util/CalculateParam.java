package me.oldboy.vaccalc.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Utility class containing standard constants for calculating vacation pay.
 */
public class CalculateParam {
    private static CalculateParam INSTANCE;

    /**
     * Average number of days in a month used for calculations.
     */
    private final BigDecimal avgDay = BigDecimal.valueOf(29.3);

    /**
     * Personal income tax rate.
     */
    private final BigDecimal incomeTax = BigDecimal.valueOf(0.13);
    private LocalDate isNow = LocalDate.now();

    /**
     * Set of federal holidays used in calculating vacation pay.
     */
    private final Set<LocalDate> vocationDays = Set.of(
            LocalDate.of(isNow.getYear(), 1,1),
            LocalDate.of(isNow.getYear(), 1,2),
            LocalDate.of(isNow.getYear(), 1,3),
            LocalDate.of(isNow.getYear(), 1,4),
            LocalDate.of(isNow.getYear(), 1,5),
            LocalDate.of(isNow.getYear(), 1,6),
            LocalDate.of(isNow.getYear(), 1,7),
            LocalDate.of(isNow.getYear(), 1,8),
            LocalDate.of(isNow.getYear(), 2,23),
            LocalDate.of(isNow.getYear(), 3,8),
            LocalDate.of(isNow.getYear(), 5,1),
            LocalDate.of(isNow.getYear(), 5,9),
            LocalDate.of(isNow.getYear(), 6,12),
            LocalDate.of(isNow.getYear(), 11,4)
    );

    private CalculateParam() {
    }

    public synchronized static CalculateParam getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CalculateParam();
        }
        return INSTANCE;
    }

    public BigDecimal getAvgDay() {
        return avgDay;
    }

    public BigDecimal getIncomeTax() {
        return incomeTax;
    }

    public Set<LocalDate> getVocationDays() {
        return vocationDays;
    }
}
