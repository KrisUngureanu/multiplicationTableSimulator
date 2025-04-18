package kz.kris.trenazher.repository;

import kz.kris.trenazher.model.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {
}
