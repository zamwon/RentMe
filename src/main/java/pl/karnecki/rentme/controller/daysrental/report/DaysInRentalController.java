package pl.karnecki.rentme.controller.daysrental.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.karnecki.rentme.service.reports.DaysInRentalReportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports/daysInRental")
public class DaysInRentalController {

    private final DaysInRentalReportService daysInRentalReportService;
    private final DaysInRentalRequestValidator daysInRentalRequestValidator;

    @GetMapping("/ui/report")
    public ResponseEntity<DaysInRentalReportResponse> getDaysInRentalReport(@RequestBody final DaysInRentalReportRequest request) {

        daysInRentalRequestValidator.validate(request);

        return ResponseEntity.ok(
            new DaysInRentalReportResponse(
                daysInRentalReportService.getDaysInRentalReport(request)));
    }
}
