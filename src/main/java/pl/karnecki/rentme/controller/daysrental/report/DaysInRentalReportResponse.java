package pl.karnecki.rentme.controller.daysrental.report;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
class DaysInRentalReportResponse {

    List<DaysInRentalReportRowResponse> rows;

    DaysInRentalReportResponse(final Page<IDaysInRentalReportRow> rows) {

        this.rows = rows
            .stream()
            .map(DaysInRentalReportRowResponse::new)
            .toList();
    }

    @Value
    class DaysInRentalReportRowResponse {

        String getAccommodationName;
        Long reservationCount;
        Long daysInRental;

        DaysInRentalReportRowResponse(final IDaysInRentalReportRow row) {
            this.getAccommodationName = row.getAccommodationName();
            this.reservationCount = row.getReservationCount();
            this.daysInRental = row.getDaysInRental();
        }
    }
}



