package pl.karnecki.rentme.service;

import lombok.NoArgsConstructor;
import pl.karnecki.rentme.dto.ReservationDto;
import pl.karnecki.rentme.model.Person;
import pl.karnecki.rentme.model.PlaceToRent;
import pl.karnecki.rentme.model.Reservation;

@NoArgsConstructor
public class ReservationAssembler {

    public Reservation mapToReservation(ReservationDto reservationDto, Person tenant, PlaceToRent placeToRent) {

        return new Reservation(
            reservationDto.issueDate(),
            reservationDto.returnDate(),
            tenant,
            placeToRent);
    }

}
