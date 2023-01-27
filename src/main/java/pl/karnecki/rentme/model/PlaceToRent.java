package pl.karnecki.rentme.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@ToString
@Table(name = "Places_to_rent")
public class PlaceToRent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal pricePerDay;
    private Integer area;
    private String description;
    @OneToOne
    private Person landlord;

    @OneToMany(mappedBy = "placeToRent")
    private Set<Reservation> reservations;

    public PlaceToRent(final String name, final BigDecimal pricePerDay, final Integer area, final String description,
                       final Person landlord, final Set<Reservation> reservation) {
        this.name = name;
        this.pricePerDay = pricePerDay;
        this.area = area;
        this.description = description;
        this.landlord = landlord;
        this.reservations = reservation;
    }
}
