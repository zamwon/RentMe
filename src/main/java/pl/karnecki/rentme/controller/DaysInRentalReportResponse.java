package pl.karnecki.rentme.controller;


import pl.karnecki.rentme.model.reports.DaysInRentalReport;

import java.util.List;


public record DaysInRentalReportResponse(List<DaysInRentalReport> rows) {

}
