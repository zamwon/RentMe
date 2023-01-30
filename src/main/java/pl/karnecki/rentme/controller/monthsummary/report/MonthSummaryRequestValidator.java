package pl.karnecki.rentme.controller.monthsummary.report;

import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

@Component
public class MonthSummaryRequestValidator {

    public void validate(final MonthSummaryReportRequest request) {
        validateDates(request.dateFrom(), request.dateTo());
    }

    private void validateDates(final LocalDate dateFrom, final LocalDate dateTo) {
        if (Objects.nonNull(dateFrom) && Objects.nonNull(dateTo) && dateFrom.isAfter(dateTo)) {
            throw new DateTimeException("Provided date not correct!");
        }
    }
}
