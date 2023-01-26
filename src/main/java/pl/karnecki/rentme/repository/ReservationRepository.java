package pl.karnecki.rentme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karnecki.rentme.model.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationByTenantNameAndTenantSurname(String name, String surname);

    List<Reservation> findReservationByPlaceToRentName(String name);
}
