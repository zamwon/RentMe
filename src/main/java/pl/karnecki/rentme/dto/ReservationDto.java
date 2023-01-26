package pl.karnecki.rentme.dto;

import java.time.LocalDate;

public record ReservationDto(String placeToRent, LocalDate issueDate, LocalDate returnDate, Long tenantId) {
}
