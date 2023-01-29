package pl.karnecki.rentme.controller.daysrental.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DaysInRentalReportRow {

    private Long accommodationId;
    private Long reservationCount;
    private Long daysInRental;
}
