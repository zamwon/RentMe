package pl.karnecki.rentme.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.karnecki.rentme.dto.PersonDto;
import pl.karnecki.rentme.model.Reservation;
import pl.karnecki.rentme.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {

    ReservationRepository reservationRepository;

    @Transactional
    public void createReservation(String objectName, LocalDateTime issueDate, LocalDateTime returnDate) {
    //TODO
    }

    public void modifyReservation(Long reservationId) {
    //TODO
    }

    public List<Reservation> findReservationForTenant(PersonDto tenantDto) {

        return reservationRepository
            .findReservationByTenantNameAndTenantSurname(tenantDto.name(), tenantDto.surname());
    }

    public List<Reservation> findReservationForObjectToRent(String objectName) {

        return reservationRepository.findReservationByPlaceToRentName(objectName);
    }
}
