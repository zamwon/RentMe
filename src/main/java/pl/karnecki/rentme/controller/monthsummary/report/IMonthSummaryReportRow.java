package pl.karnecki.rentme.controller.monthsummary.report;

import java.math.BigDecimal;

public interface IMonthSummaryReportRow {

    Long getLandLordId();
    String getLandLordNameAndSurname();
    Long getGuestsAmount();
    Long getBookedPropertiesAmount();
    BigDecimal getTotalProfit();

    void setLandLordId(Long landLordId);
    void setLandLordNameAndSurname(String landLordNameAndSurname);
    void setGuestsAmount(Long guestsAmount);
    void setBookedPropertiesAmount(Long bookedPropertiesAmount);
    void setTotalProfit(BigDecimal totalProfit);


}
