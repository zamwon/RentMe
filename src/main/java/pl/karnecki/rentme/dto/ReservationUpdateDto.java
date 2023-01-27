package pl.karnecki.rentme.dto;

import java.time.LocalDate;

public record ReservationUpdateDto(Long reservationId, LocalDate issueDate, LocalDate returnDate) {
}
