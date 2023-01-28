package pl.karnecki.rentme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.karnecki.rentme.controller.daysrental.report.DaysInRentalReportRow;
import pl.karnecki.rentme.model.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationByTenantNameAndTenantSurname(final String name, final String surname);

    List<Reservation> findReservationByPlaceToRentName(final String name);

    @Query(value = "SELECT * FROM reservations WHERE issue_date >= :from AND return_date <= :to", nativeQuery = true)
    List<Reservation> findReservationsInRange(final LocalDate from, final LocalDate to);

    Optional<Reservation> findReservationById(final Long reservationId);

    @Query(value = "SELECT * FROM reservations WHERE issue_date >= :from AND return_date <= :to AND id != :reservationId", nativeQuery = true)
    List<Reservation> findReservationsOverlapped(final LocalDate from, final LocalDate to, final Long reservationId);

    @Query(value = """
            SELECT
            p.name,
            COUNT(*) as liczba_rezerwacji,
            SUM(DATEDIFF('day',  r1.issue_date, r1.return_date) +
                CASE
                   WHEN DATEDIFF('day',  r1.issue_date, r1.return_date) = 1 THEN 0
                   WHEN EXISTS (SELECT 1 FROM reservations r2 WHERE r2.place_to_rent_id = r1.place_to_rent_id
                                   AND r2.issue_date =  DATEADD('day', 1, r1.return_date )) THEN 1
                   ELSE 1
                END) as ilosc_dni_zarezerwowanych
            FROM reservations r1
            JOIN places_to_rent p ON r1.place_to_rent_id = p.id
            WHERE (issue_date >= :dateFrom AND return_date <= :dateTo)
            GROUP BY p.name
        """, nativeQuery = true)
    List<DaysInRentalReportRow> getReport(String dateFrom, String dateTo);
}
