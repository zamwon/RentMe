package pl.karnecki.rentme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "Reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate issueDate;
    private LocalDate returnDate;

    @ManyToOne
    private Person landLord;

    @ManyToOne
    private Person tenant;

    private BigDecimal totalCost;

    @ManyToOne()
    @JsonIgnore
    private PlaceToRent placeToRent;

    public Reservation(final LocalDate issueDate, final LocalDate returnDate, final BigDecimal totalCost,
                       final Person landLord, final Person tenant, final PlaceToRent placeToRent) {
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.landLord = landLord;
        this.tenant = tenant;
        this.totalCost = totalCost;
        this.placeToRent = placeToRent;
    }
}

