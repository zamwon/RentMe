package pl.karnecki.rentme.controller.monthsummary.report;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

@Value
public class MonthSummaryReportResponse {

    List<MonthSummaryReportResponse.MonthSummaryReportRowResponse> rows;

    MonthSummaryReportResponse(final Page<IMonthSummaryReportRow> rows) {

        this.rows = rows
            .stream()
            .map(MonthSummaryReportRowResponse::new)
            .toList();
    }

    @Value
    class MonthSummaryReportRowResponse {

        Long landLordId;
        String landLordNameAndSurname;
        Long guestsAmount;
        Long bookedPropertiesAmount;
        BigDecimal totalProfit;

        MonthSummaryReportRowResponse(final IMonthSummaryReportRow row) {
            this.landLordId = row.getLandLordId();
            this.landLordNameAndSurname = row.getLandLordNameAndSurname();
            this.guestsAmount = row.getGuestsAmount();
            this.bookedPropertiesAmount = row.getBookedPropertiesAmount();
            this.totalProfit = row.getTotalProfit();
        }
    }
}
