package pl.karnecki.rentme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karnecki.rentme.model.PlaceToRent;

@Repository
public interface PlaceToRentRepository extends JpaRepository<PlaceToRent, Long> {
}
