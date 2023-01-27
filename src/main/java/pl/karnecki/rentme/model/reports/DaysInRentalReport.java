package pl.karnecki.rentme.model.reports;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DaysInRentalReport {

    private String hotelName;
    private Integer reservationCount;
    private Integer daysInRental;


}
