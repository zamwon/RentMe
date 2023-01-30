package pl.karnecki.rentme;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import pl.karnecki.rentme.controller.monthsummary.report.IMonthSummaryReportRow;
import pl.karnecki.rentme.controller.monthsummary.report.MonthSummaryReportRequest;
import pl.karnecki.rentme.repository.ReservationRepository;
import pl.karnecki.rentme.service.report.month.summary.MonthSummaryReportService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class MonthSummaryReportTest {

    @Mock
    private ReservationRepository reservationRepository = mock(ReservationRepository.class);

    @InjectMocks
    private MonthSummaryReportService monthSummaryReportService;

    private static IMonthSummaryReportRow createMonthSummaryRow() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        IMonthSummaryReportRow projection = factory.createProjection(IMonthSummaryReportRow.class);
        projection.setLandLordId(1L);
        projection.setLandLordNameAndSurname("Test Name");
        projection.setGuestsAmount(2L);
        projection.setBookedPropertiesAmount(1L);
        projection.setTotalProfit(BigDecimal.valueOf(2000L));
        return projection;
    }

    private static MonthSummaryReportRequest createRequest() {
        return MonthSummaryReportRequest.builder()
            .dateFrom("2023-02-01")
            .dateTo("2023-03-01")
            .landLords(Set.of(1L, 3L))
            .build();
    }

    @Test
    void shouldReturnMonthSummaryReport() {

        //given
        final var request = createRequest();
        IMonthSummaryReportRow reportRow = createMonthSummaryRow();
        List<IMonthSummaryReportRow> monthSummaryReportRows = List.of(reportRow);

        //when
        when(reservationRepository
            .getMonthSummaryReport(request.dateFrom(), request.dateTo(), request.getLandLords()))
            .thenReturn(monthSummaryReportRows);

        //expect
        final var actual = monthSummaryReportService.getMonthSummaryReportRows(request);

        Assertions.assertEquals(actual, monthSummaryReportRows);
    }
}
