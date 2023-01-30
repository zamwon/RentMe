package pl.karnecki.rentme.controller.monthsummary.report;

import java.math.BigDecimal;

public interface IMonthSummaryReportRow {

    Long getLandLordId();
    String getLandLordNameAndSurname();
    Long getGuestsAmount();
    Long getBookedPropertiesAmount();
    BigDecimal getTotalProfit();
}
