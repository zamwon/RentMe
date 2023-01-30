package pl.karnecki.rentme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.karnecki.rentme.model.Person;
import pl.karnecki.rentme.model.PlaceToRent;
import pl.karnecki.rentme.model.Reservation;
import pl.karnecki.rentme.repository.PersonRepository;
import pl.karnecki.rentme.repository.PlaceToRentRepository;
import pl.karnecki.rentme.repository.ReservationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Component
public class PopulateDbOnStart {


    @Autowired
    public PopulateDbOnStart(final PersonRepository personRepository, final PlaceToRentRepository placeToRentRepository,
                             final ReservationRepository reservationRepository) {

        Person landlord = new Person("Blazej", "Karnecki");
        Person landlord2 = new Person("Adam", "Kowalski");
        Person landlord3 = new Person("TestLand", "TestLord");
        Person person1 = new Person("John", "Doe");
        Person person2 = new Person("Want", "ToRent");
        Person person3 = new Person("Personality", "NN");

        personRepository.save(landlord);
        personRepository.save(landlord2);
        personRepository.save(landlord3);
        personRepository.save(person1);
        personRepository.save(person2);
        personRepository.save(person3);

        PlaceToRent placeToRent1 = PlaceToRent.builder()
            .name("Jantar_Hotel")
            .pricePerDay(BigDecimal.valueOf(100L))
            .area(28)
            .description("High standard hotel room")
            .landlord(landlord)
            .reservations(Set.of())
            .build();

        PlaceToRent placeToRent2 = PlaceToRent.builder()
            .name("DayAndNight_Apartments")
            .pricePerDay(BigDecimal.valueOf(200L))
            .area(35)
            .description("Short and long term rental")
            .landlord(landlord)
            .reservations(Set.of())
            .build();

        PlaceToRent placeToRent3 = PlaceToRent.builder()
            .name("Test_Apartments")
            .pricePerDay(BigDecimal.valueOf(100L))
            .area(100)
            .description("Description")
            .landlord(landlord2)
            .reservations(Set.of())
            .build();

        placeToRentRepository.save(placeToRent1);
        placeToRentRepository.save(placeToRent2);
        placeToRentRepository.save(placeToRent3);

        Reservation reservation1 = Reservation.builder()
            .issueDate(LocalDate.of(2023, 2, 1))
            .returnDate(LocalDate.of(2023, 2, 7))
            .totalCost(BigDecimal.valueOf(700L))
            .landLord(landlord)
            .tenant(person1)
            .placeToRent(placeToRent1)
            .build();

        Reservation reservation2 = Reservation.builder()
            .issueDate(LocalDate.of(2023, 2, 9))
            .returnDate(LocalDate.of(2023, 2, 10))
            .totalCost(BigDecimal.valueOf(200L))
            .landLord(landlord)
            .tenant(person2)
            .placeToRent(placeToRent2)
            .build();
        Reservation reservation3 = Reservation.builder()
            .issueDate(LocalDate.of(2023, 5, 10))
            .returnDate(LocalDate.of(2023, 5, 19))
            .totalCost(BigDecimal.valueOf(2000L))
            .landLord(landlord)
            .tenant(person2)
            .placeToRent(placeToRent2)
            .build();

        Reservation reservation4 = Reservation.builder()
            .issueDate(LocalDate.of(2023, 5, 20))
            .returnDate(LocalDate.of(2023, 5, 21))
            .totalCost(BigDecimal.valueOf(200L))
            .landLord(landlord)
            .tenant(person2)
            .placeToRent(placeToRent2)
            .build();

        Reservation reservation5 = Reservation.builder()
            .issueDate(LocalDate.of(2023, 1, 1))
            .returnDate(LocalDate.of(2023, 1, 31))
            .totalCost(BigDecimal.valueOf(3000L))
            .landLord(landlord2)
            .tenant(person2)
            .placeToRent(placeToRent3)
            .build();

        Reservation reservation6 = Reservation.builder()
            .issueDate(LocalDate.of(2023, 1, 19))
            .returnDate(LocalDate.of(2023, 1, 20))
            .totalCost(BigDecimal.valueOf(200L))
            .landLord(landlord)
            .tenant(person2)
            .placeToRent(placeToRent2)
            .build();

        Reservation reservation7 = Reservation.builder()
            .issueDate(LocalDate.of(2023, 2, 10))
            .returnDate(LocalDate.of(2023, 2, 19))
            .totalCost(BigDecimal.valueOf(2000L))
            .landLord(landlord)
            .tenant(person3)
            .placeToRent(placeToRent3)
            .build();

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);
        reservationRepository.save(reservation4);
        reservationRepository.save(reservation5);
        reservationRepository.save(reservation6);
        reservationRepository.save(reservation6);
        reservationRepository.save(reservation7);
    }
}