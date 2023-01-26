package pl.karnecki.rentme.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.karnecki.rentme.dto.PersonDto;
import pl.karnecki.rentme.dto.ReservationDto;
import pl.karnecki.rentme.exception.NoSuchAccommodationException;
import pl.karnecki.rentme.exception.NoSuchTenantException;
import pl.karnecki.rentme.exception.PlaceNotAvailableException;
import pl.karnecki.rentme.model.Person;
import pl.karnecki.rentme.model.PlaceToRent;
import pl.karnecki.rentme.model.Reservation;
import pl.karnecki.rentme.repository.PersonRepository;
import pl.karnecki.rentme.repository.PlaceToRentRepository;
import pl.karnecki.rentme.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ReservationService {

    ReservationRepository reservationRepository;

    PersonRepository personRepository;

    PlaceToRentRepository placeToRentRepository;

    public void createReservation(ReservationDto reservationDto) {

        final var tenant = findTenant(reservationDto);
        final var placeToRent = findAccommodation(reservationDto);
        validateAvailability(reservationDto);

        final var reservationAssembler = new ReservationAssembler();
        final var newReservation = reservationAssembler.mapToReservation(reservationDto, tenant, placeToRent);

        saveReservation(newReservation);
    }

    private PlaceToRent findAccommodation(final ReservationDto reservationDto) {

        return Optional.of(placeToRentRepository.findPlaceToRentByName(reservationDto.placeToRent())).get()
            .orElseThrow(() -> new NoSuchAccommodationException(
                String.format("Accomodation: %s not found!", reservationDto.placeToRent())));

    }

    private Person findTenant(final ReservationDto reservationDto) {

        return Optional.of(personRepository.findPersonById(reservationDto.tenantId())).get()
            .orElseThrow(() -> new NoSuchTenantException(
                String.format("Tenant with id: %s not found. Create an account before trying to book a place!",
                    reservationDto.tenantId())));

    }

    @Transactional
    public void saveReservation(Reservation reservation) {

        //Todo Check for duplicate
        reservationRepository.save(reservation);
    }

    public void modifyReservation(Long reservationId) {
        //TODO
    }

    public List<Reservation> findReservationForTenant(PersonDto tenantDto) {

        return reservationRepository
            .findReservationByTenantNameAndTenantSurname(tenantDto.name(), tenantDto.surname());
    }

    public List<Reservation> findReservationForObjectToRent(String objectName) {

        return reservationRepository.findReservationByPlaceToRentName(objectName);
    }

    private void validateAvailability(ReservationDto reservationDto) {


        LocalDate issueDate = reservationDto.issueDate();
        LocalDate returnDate = reservationDto.returnDate();

        if (false) {

            var message = String.format("Place is not available in provided time range: %s - %s", issueDate, returnDate);
            log.error(message);
            throw new PlaceNotAvailableException(message);
        }
    }
}
