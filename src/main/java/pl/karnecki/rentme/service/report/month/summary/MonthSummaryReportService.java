package pl.karnecki.rentme.service.report.month.summary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import pl.karnecki.rentme.controller.monthsummary.report.IMonthSummaryReportRow;
import pl.karnecki.rentme.controller.monthsummary.report.MonthSummaryReportRequest;
import pl.karnecki.rentme.repository.ReservationRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonthSummaryReportService {

    private final ReservationRepository reservationRepository;

    public Page<IMonthSummaryReportRow> getMonthSummaryReport(final MonthSummaryReportRequest request) {

        log.info("Generating Month Summary Report - start - searchParams: {}", request);

        final var reportRows = getMonthSummaryReportRows(request);

        log.info("Generating Month Summary Report - finished");
        return new PageImpl<>(reportRows);
    }

    public List<IMonthSummaryReportRow> getMonthSummaryReportRows(final MonthSummaryReportRequest request) {

        return reservationRepository.getMonthSummaryReport(request.dateFrom(), request.dateTo(), request.getLandLords());
    }
}
