package pl.karnecki.rentme.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.karnecki.rentme.dto.PersonDto;
import pl.karnecki.rentme.model.Reservation;
import pl.karnecki.rentme.service.ReservationService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/tenant")
    public List<Reservation> getReservationForTenant(@RequestBody PersonDto tenantDto) {

       return reservationService.findReservationForTenant(tenantDto);
    }

    @GetMapping("/object/{name}")
    public List<Reservation> getReservationForObject(@PathVariable String name) {

        return reservationService.findReservationForObjectToRent(name);
    }
}
