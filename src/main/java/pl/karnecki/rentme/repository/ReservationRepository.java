package pl.karnecki.rentme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.karnecki.rentme.model.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationByTenantNameAndTenantSurname(String name, String surname);

    List<Reservation> findReservationByPlaceToRentName(String name);

    @Query(value = "SELECT * FROM reservations WHERE issue_date >= :from AND return_date <= :to", nativeQuery = true)
    List<Reservation> findReservationsInRange(LocalDate from, LocalDate to);

    Optional<Reservation> findReservationById(Long reservationId);

    @Query(value = "SELECT * FROM reservations WHERE issue_date >= :from AND return_date <= :to AND id != :reservationId", nativeQuery = true)
    List<Reservation> findReservationsOverlapped(LocalDate from, LocalDate to, Long reservationId);
}
