package pl.karnecki.rentme.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DaysInRentalReportRequest {

    private String dateFrom;
    private String dateTo;

    public LocalDate dateFrom() {
        return parseToLocalDate(dateFrom);
    }

    public LocalDate dateTo() {
        return parseToLocalDate(dateTo);
    }

    private LocalDate parseToLocalDate(final String date) {
        try {
            return date != null ? LocalDate.parse(date) : null;
        } catch (final DateTimeParseException e) {
            throw new DateTimeParseException("Wrong data format", date, e.getErrorIndex());
        }
    }
}
