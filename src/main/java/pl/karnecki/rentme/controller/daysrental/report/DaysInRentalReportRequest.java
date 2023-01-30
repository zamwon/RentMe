package pl.karnecki.rentme.controller.daysrental.report;

import lombok.*;
import pl.karnecki.rentme.controller.IDateTimeFrame;

import java.time.LocalDate;

@Data
public class DaysInRentalReportRequest implements IDateTimeFrame {

    private String dateFrom;
    private String dateTo;


    @Override
    public LocalDate dateFrom() {
        return parseToLocalDate(dateFrom);
    }

    @Override
    public LocalDate dateTo() { return parseToLocalDate(dateTo); }
}
