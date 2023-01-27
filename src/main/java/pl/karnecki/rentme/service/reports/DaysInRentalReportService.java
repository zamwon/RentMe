package pl.karnecki.rentme.service.reports;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.karnecki.rentme.controller.DaysInRentalReportRequest;
import pl.karnecki.rentme.model.reports.DaysInRentalReport;
import pl.karnecki.rentme.repository.DaysInRentalReportRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DaysInRentalReportService {

    private final DaysInRentalReportRepository daysInRentalReportRepository;

    public List<DaysInRentalReport> getDaysInRentalReport(final DaysInRentalReportRequest request) {

        return daysInRentalReportRepository.getReport(request.dateFrom().toString(), request.dateTo().toString());
    }
}
