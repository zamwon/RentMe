package pl.karnecki.rentme.service.reports;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import pl.karnecki.rentme.controller.daysrental.report.DaysInRentalReportRequest;
import pl.karnecki.rentme.controller.daysrental.report.DaysInRentalReportRow;
import pl.karnecki.rentme.repository.ReservationRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DaysInRentalReportService {

    private final ReservationRepository reservationRepository;

    public Page<DaysInRentalReportRow> getDaysInRentalReport(final DaysInRentalReportRequest request) {

        log.info("Generating Days in Rental Report - start - searchParams: {}", request);

        final var reportRows = getDaysInRentalReportRows(request);

        log.info("Generating Game Stats Report - finished");
        return new PageImpl<>(reportRows);
    }

    public List<DaysInRentalReportRow> getDaysInRentalReportRows(final DaysInRentalReportRequest request) {

        return reservationRepository.getReport(request.dateFrom().toString(), request.dateTo().toString());
    }
}
