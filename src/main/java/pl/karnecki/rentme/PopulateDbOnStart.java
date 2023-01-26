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
    public PopulateDbOnStart(PersonRepository personRepository, PlaceToRentRepository placeToRentRepository,
                             ReservationRepository reservationRepository) {

        Person landlord = new Person("Blazej", "Karnecki");
        Person person1 = new Person("John", "Doe");
        Person person2 = new Person("Want", "ToRent");

        personRepository.save(landlord);
        personRepository.save(person1);
        personRepository.save(person2);

        PlaceToRent placeToRent1 = new PlaceToRent(
            "Jantar_Hotel", BigDecimal.valueOf(100L), 28, "High standard hotel room", Set.of());
        PlaceToRent placeToRent2 = new PlaceToRent(
            "DayAndNight_Apartments", BigDecimal.valueOf(200L), 35, "Short and long term rental", Set.of());

        placeToRentRepository.save(placeToRent1);
        placeToRentRepository.save(placeToRent2);

        Reservation reservation1 = new Reservation(
            LocalDate.of(2023, 2, 1),
            LocalDate.of(2023, 2, 7),
            landlord,
            person1,
            BigDecimal.valueOf(700L),
            placeToRent1);

        Reservation reservation2 = new Reservation(
            LocalDate.of(2023, 2, 9),
            LocalDate.of(2023, 2, 10),
            landlord,
            person2,
            BigDecimal.valueOf(200L),
            placeToRent2);
        Reservation reservation3 = new Reservation(
            LocalDate.of(2023, 5, 9),
            LocalDate.of(2023, 5, 10),
            landlord,
            person2,
            BigDecimal.valueOf(200L),
            placeToRent2);

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);
    }
}