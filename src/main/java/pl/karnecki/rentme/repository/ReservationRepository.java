package pl.karnecki.rentme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.karnecki.rentme.controller.daysrental.report.IDaysInRentalReportRow;
import pl.karnecki.rentme.controller.monthsummary.report.IMonthSummaryReportRow;
import pl.karnecki.rentme.model.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationByTenantNameAndTenantSurname(final String name, final String surname);

    List<Reservation> findReservationByPlaceToRentName(final String name);

    @Query(value = "SELECT * FROM reservations WHERE issue_date >= :from AND return_date <= :to", nativeQuery = true)
    List<Reservation> findReservationsInRange(final LocalDate from, final LocalDate to);

    Optional<Reservation> findReservationById(final Long reservationId);

    @Query(value = "SELECT * FROM reservations WHERE issue_date >= :from AND return_date <= :to AND id != :reservationId", nativeQuery = true)
    List<Reservation> findReservationsOverlapped(final LocalDate from, final LocalDate to, final Long reservationId);

    @Query(value =
        """
                SELECT
                p.name as accommodationName,
                COUNT(r1.*) as reservationCount,
                SELECT SUM(DATEDIFF('day', r1.issue_date, r1.return_date)) FROM RESERVATIONS r1
                    WHERE r1.place_to_rent_id = p.id AND issue_date >= :dateFrom AND return_date <= :dateTo
                    GROUP BY r1.place_to_rent_id as daysInRental
                FROM reservations r1
                JOIN places_to_rent p ON r1.place_to_rent_id = p.id
                WHERE (issue_date >= :dateFrom AND return_date <= :dateTo)
                GROUP BY p.name
            """, nativeQuery = true)
    List<IDaysInRentalReportRow> getDaysInRentalReport(final String dateFrom, final String dateTo);

    @Query(value = """
    SELECT
        p.id as landLordId,
        CONCAT(p.name, ' ' , p.surname) as landLordNameAndSurname,
        COUNT(DISTINCT tenant_id) as guestsAmount,
        COUNT(place_to_rent_id) as bookedPropertiesAmount,
        SUM(TOTAL_COST) as totalProfit
    FROM RESERVATIONS r\s
    JOIN PERSONS p ON r.land_lord_id = p.id
    WHERE (issue_date >= :issueDate AND return_date <= :returnDate ) AND p.id IN :landLords
    GROUP BY p.id
    ORDER BY p.surname ASC
    """, nativeQuery = true)
   List<IMonthSummaryReportRow> getMonthSummaryReport(LocalDate issueDate, LocalDate returnDate, Set<Long> landLords);


}
