package pl.karnecki.rentme.controller.monthsummary.report;

import lombok.Data;
import pl.karnecki.rentme.controller.IDateTimeFrame;

import java.time.LocalDate;
import java.util.Set;

@Data
public class MonthSummaryReportRequest implements IDateTimeFrame {

    private String dateFrom;
    private String dateTo;
    private Set<Long> landLords;

    @Override
    public LocalDate dateFrom() {
        return parseToLocalDate(dateFrom);
    }

    @Override
    public LocalDate dateTo() {
        return parseToLocalDate(dateTo);
    }

}
