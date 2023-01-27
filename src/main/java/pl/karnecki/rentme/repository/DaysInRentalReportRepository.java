package pl.karnecki.rentme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.karnecki.rentme.model.reports.DaysInRentalReport;

import java.util.List;

@Repository
public interface DaysInRentalReportRepository extends JpaRepository<DaysInRentalReport, Long> {

    @Query(value = "SELECT COUNT(*) as liczba_rezerwacji, SUM(TIMESTAMPDIFF('DAY', :from, :to)) as ilosc_dni_zarezerwowanych FROM reservations", nativeQuery = true)
    List<DaysInRentalReport> getReport(final String from, final String to);
}
