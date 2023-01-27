package pl.karnecki.rentme.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.karnecki.rentme.dto.PersonDto;
import pl.karnecki.rentme.dto.ReservationDto;
import pl.karnecki.rentme.dto.ReservationUpdateDto;
import pl.karnecki.rentme.exception.NoSuchAccommodationException;
import pl.karnecki.rentme.exception.NoSuchTenantException;
import pl.karnecki.rentme.exception.PlaceNotAvailableException;
import pl.karnecki.rentme.exception.ReservationNotFoundException;
import pl.karnecki.rentme.model.Person;
import pl.karnecki.rentme.model.PlaceToRent;
import pl.karnecki.rentme.model.Reservation;
import pl.karnecki.rentme.repository.PersonRepository;
import pl.karnecki.rentme.repository.PlaceToRentRepository;
import pl.karnecki.rentme.repository.ReservationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final PersonRepository personRepository;

    private final PlaceToRentRepository placeToRentRepository;

    public void createReservation(final ReservationDto reservationDto) {

        final Reservation newReservation = validate(reservationDto);
        saveReservation(newReservation);
    }

    public void saveReservation(final Reservation reservation) {

        reservationRepository.save(reservation);
    }

    public void modifyReservation(final ReservationUpdateDto reservationDto) {

        Reservation reservation = checkReservationExistence(reservationDto);

        final Reservation updatedReservation = validateForUpdate(reservationDto, reservation);
        reservationRepository.save(updatedReservation);

    }

    public List<Reservation> findReservationForTenant(final PersonDto tenantDto) {

        return reservationRepository
            .findReservationByTenantNameAndTenantSurname(tenantDto.name(), tenantDto.surname());
    }

    public List<Reservation> findReservationForObjectToRent(final String objectName) {

        return reservationRepository.findReservationByPlaceToRentName(objectName);
    }

    private Reservation checkReservationExistence(final ReservationUpdateDto reservationDto) {

        return reservationRepository.findReservationById(reservationDto.reservationId())
            .orElseThrow(() -> new ReservationNotFoundException(String.format
                ("No such reservation for id: %s", reservationDto.reservationId())));
    }

    private PlaceToRent findAccommodation(final ReservationDto reservationDto) {

        return Optional.of(placeToRentRepository.findPlaceToRentByName(reservationDto.placeToRent())).get()
            .orElseThrow(() -> new NoSuchAccommodationException(
                String.format("Accommodation: %s not found!", reservationDto.placeToRent())));

    }

    private Person findTenant(final ReservationDto reservationDto) {

        return Optional.of(personRepository.findPersonById(reservationDto.tenantId())).get()
            .orElseThrow(() -> new NoSuchTenantException(
                String.format("Tenant with id: %s not found. Create an account before trying to book a place!",
                    reservationDto.tenantId())));

    }

    private Reservation validate(final ReservationDto reservationDto) {

        validateAvailability(reservationDto.issueDate(), reservationDto.returnDate());

        final var tenant = findTenant(reservationDto);
        final var placeToRent = findAccommodation(reservationDto);
        final var landlord = placeToRent.getLandlord();
        final var totalCost = calculateCost(reservationDto.issueDate(), reservationDto.returnDate(), placeToRent);

        return dtoToReservation(reservationDto, totalCost, tenant, placeToRent, landlord);
    }

    private Reservation validateForUpdate(final ReservationUpdateDto reservationDto, final Reservation reservation) {

        validateAvailability(reservationDto.issueDate(), reservationDto.returnDate(), reservationDto.reservationId());

        final var totalCost = calculateCost(reservationDto.issueDate(), reservationDto.returnDate(), reservation.getPlaceToRent());

        return updateReservation(reservationDto, totalCost, reservation);
    }

    private Reservation updateReservation(final ReservationUpdateDto reservationDto, final BigDecimal totalCost, final Reservation reservation) {

        final var reservationAssembler = new ReservationAssembler();
        return reservationAssembler.mapToReservation(reservationDto, totalCost, reservation);
    }

    private BigDecimal calculateCost(final LocalDate issueDate, final LocalDate returnDate, final PlaceToRent placeToRent) {

        final long daysCount = ChronoUnit.DAYS.between(issueDate, returnDate);
        return placeToRent.getPricePerDay().multiply(BigDecimal.valueOf(daysCount));
    }

    private Reservation dtoToReservation(final ReservationDto reservationDto, final BigDecimal totalCost,
                                         final Person tenant, final PlaceToRent placeToRent, final Person landlord) {

        final var reservationAssembler = new ReservationAssembler();
        return reservationAssembler.mapToReservation(reservationDto, totalCost, landlord, tenant, placeToRent);
    }

    private void validateAvailability(final LocalDate issueDate, final LocalDate returnDate) {

        List<Reservation> reservationBookedInProvidedTimeframe = reservationRepository.findReservationsInRange(issueDate, returnDate);
        if (!reservationBookedInProvidedTimeframe.isEmpty()) {

            var message = String.format("Place is not available in provided time range: %s - %s", issueDate, returnDate);
            log.error(message);
            throw new PlaceNotAvailableException(message);
        }
    }

    private void validateAvailability(final LocalDate issueDate, final LocalDate returnDate, final Long reservationId) {

        List<Reservation> reservationBookedInProvidedTimeframe = reservationRepository.findReservationsOverlapped(issueDate, returnDate, reservationId);
        if (!reservationBookedInProvidedTimeframe.isEmpty()) {

            var message = String.format("Place is not available in provided time range: %s - %s", issueDate, returnDate);
            log.error(message);
            throw new PlaceNotAvailableException(message);
        }
    }
}
