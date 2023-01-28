package pl.karnecki.rentme.controller.daysrental.report;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
class DaysInRentalReportResponse {

    List<DaysInRentalReportRowResponse> rows;

    DaysInRentalReportResponse(final Page<DaysInRentalReportRow> rows) {

        this.rows = rows
            .stream()
            .map(DaysInRentalReportRowResponse::new)
            .toList();
    }

    @Value
    class DaysInRentalReportRowResponse {

        String accommodationName;
        Integer reservationCount;
        Integer daysInRental;

        DaysInRentalReportRowResponse(final DaysInRentalReportRow row) {
            this.accommodationName = row.getAccommodationName();
            this.reservationCount = row.getReservationCount();
            this.daysInRental = row.getDaysInRental();
        }
    }
}



