package pl.karnecki.rentme.service;

import lombok.NoArgsConstructor;
import pl.karnecki.rentme.dto.ReservationDto;
import pl.karnecki.rentme.dto.ReservationUpdateDto;
import pl.karnecki.rentme.model.Person;
import pl.karnecki.rentme.model.PlaceToRent;
import pl.karnecki.rentme.model.Reservation;

import java.math.BigDecimal;

@NoArgsConstructor
public class ReservationAssembler {

    public Reservation mapToReservation(final ReservationDto reservationDto, final BigDecimal totalCost,
                                        final Person landlord, final Person tenant, final PlaceToRent placeToRent) {

        return new Reservation(reservationDto.issueDate(), reservationDto.returnDate(), totalCost, landlord, tenant, placeToRent);
    }

    public Reservation mapToReservation(final ReservationUpdateDto reservationDto, final BigDecimal totalCost,
                                        final Reservation reservation) {

        reservation.setIssueDate(reservationDto.issueDate());
        reservation.setReturnDate(reservationDto.returnDate());
        reservation.setTotalCost(totalCost);

        return reservation;
    }

}
