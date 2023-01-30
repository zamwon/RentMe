package pl.karnecki.rentme.controller.monthsummary.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.karnecki.rentme.service.report.month.summary.MonthSummaryReportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports/monthSummary")
public class MonthSummaryReportController {

    private final MonthSummaryReportService monthSummaryReportService;
    private final MonthSummaryRequestValidator monthSummaryRequestValidator;

    @GetMapping("/ui/report")
    public ResponseEntity<MonthSummaryReportResponse> getDaysInRentalReport(@RequestBody final MonthSummaryReportRequest request) {

        monthSummaryRequestValidator.validate(request);

        return ResponseEntity.ok(
            new MonthSummaryReportResponse(
                monthSummaryReportService.getMonthSummaryReport(request)));
    }
}
