package pl.karnecki.rentme.controller;

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
        final var daysInRentalReport = daysInRentalReportService.getDaysInRentalReport(request);

        return ResponseEntity.ok().body(new DaysInRentalReportResponse(daysInRentalReport));
    }
}
