package pl.karnecki.rentme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.karnecki.rentme.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
