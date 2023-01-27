package pl.karnecki.rentme;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.karnecki.rentme.dto.PersonDto;
import pl.karnecki.rentme.dto.ReservationDto;
import pl.karnecki.rentme.exception.NoSuchAccommodationException;
import pl.karnecki.rentme.exception.PlaceNotAvailableException;
import pl.karnecki.rentme.model.Person;
import pl.karnecki.rentme.model.PlaceToRent;
import pl.karnecki.rentme.model.Reservation;
import pl.karnecki.rentme.repository.PersonRepository;
import pl.karnecki.rentme.repository.PlaceToRentRepository;
import pl.karnecki.rentme.repository.ReservationRepository;
import pl.karnecki.rentme.service.ReservationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReservationControllerTest {

    @Mock
    private PersonRepository personRepository = mock(PersonRepository.class);
    @Mock
    private PlaceToRentRepository placeToRentRepository = mock(PlaceToRentRepository.class);

    @Mock
    private ReservationRepository reservationRepository = mock(ReservationRepository.class);

    @InjectMocks
    private ReservationService reservationService = new ReservationService(reservationRepository, personRepository, placeToRentRepository);

    private Reservation reservation2;
    private Reservation reservation3;
    private Reservation reservation4;
    private Person person2;
    private PlaceToRent placeToRent1;

    @BeforeEach
    public void setUpRepos() {

        Person landlord = new Person(1L, "Blazej", "Karnecki");
        Person person1 = new Person(2L, "John", "Doe");
        person2 = new Person(3L,"Want", "ToRent");

        personRepository.save(landlord);
        personRepository.save(person1);
        personRepository.save(person2);

        placeToRent1 = new PlaceToRent(
            "Jantar_Hotel", BigDecimal.valueOf(100L), 28, "High standard hotel room",
            landlord, Set.of());
        PlaceToRent placeToRent2 = new PlaceToRent(
            "DayAndNight_Apartments", BigDecimal.valueOf(200L), 35, "Short and long term rental",
            landlord, Set.of());

        placeToRentRepository.save(placeToRent1);
        placeToRentRepository.save(placeToRent2);

        Reservation reservation1 = new Reservation(
            LocalDate.of(2023, 2, 1),
            LocalDate.of(2023, 2, 7),
            BigDecimal.valueOf(700L),
            landlord,
            person1,
            placeToRent1);

        reservation2 = new Reservation(
            LocalDate.of(2023, 2, 9),
            LocalDate.of(2023, 2, 10),
            BigDecimal.valueOf(200L),
            landlord,
            person2,
            placeToRent2);

        reservation3 = new Reservation(
            LocalDate.of(2023, 5, 10),
            LocalDate.of(2023, 5, 19),
            BigDecimal.valueOf(2000L),
            landlord,
            person2,
            placeToRent2);

        reservation4 = new Reservation(
            LocalDate.of(2023, 5, 20),
            LocalDate.of(2023, 5, 21),
            BigDecimal.valueOf(200L),
            landlord,
            person2,
            placeToRent2);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);
        reservationRepository.save(reservation4);
    }

    @Test
    void shouldReturnReservationsForTenant() {

        final var resultList = List.of(reservation2, reservation3, reservation4);
        //when
        when(reservationRepository.findReservationByTenantNameAndTenantSurname("Want", "ToRent"))
            .thenReturn(resultList);

        //then
        final var actual = reservationService.findReservationForTenant(new PersonDto("Want", "ToRent"));


        Assertions.assertEquals(actual, resultList);
    }

    @Test
    void shouldNotReturnReservationsForTenant() {

        //given
        final var resultList = List.of(reservation2, reservation3, reservation4);

        //when
        when(reservationRepository.findReservationByTenantNameAndTenantSurname("Want", "ToRent"))
            .thenReturn(resultList);

        //then
        final var actual = reservationService.findReservationForTenant(new PersonDto("No", "Such"));


        Assertions.assertNotEquals(actual, resultList);
    }

    @Test
    void shouldThrowErrorWhenPlaceToRentNotFound() {

        //given
        final var issueDate = LocalDate.of(2023, 3, 1);
        final var returnDate = LocalDate.of(2023, 3, 7);
        final var reservationDto = new ReservationDto("NoSuchPlace", issueDate, returnDate, person2.getId());

        //when
        when(personRepository.findPersonById(any())).thenReturn(Optional.of(person2));
        when(placeToRentRepository.findPlaceToRentByName(reservationDto.placeToRent()))
            .thenThrow(NoSuchAccommodationException.class);

        //then
        assertThatCode(() -> reservationService.createReservation(reservationDto))
            .isInstanceOf(NoSuchAccommodationException.class);

    }

    @Test
    void shouldThrowErrorWhenObjectToRentNotAvailable() {

        //given
        final var issueDate = LocalDate.of(2023, 2, 1);
        final var returnDate = LocalDate.of(2023, 2, 7);
        final var reservationDto = new ReservationDto(placeToRent1.getName(), issueDate, returnDate, person2.getId());

        //when
        when(personRepository.findPersonById(any())).thenReturn(Optional.of(person2));
        when(placeToRentRepository.findPlaceToRentByName(any())).thenReturn(Optional.of(placeToRent1));
        when(reservationRepository.findReservationsInRange(issueDate, returnDate)).thenThrow(PlaceNotAvailableException.class);

        //then
        assertThatCode(() -> reservationService.createReservation(reservationDto))
            .isInstanceOf(PlaceNotAvailableException.class);
    }
}
