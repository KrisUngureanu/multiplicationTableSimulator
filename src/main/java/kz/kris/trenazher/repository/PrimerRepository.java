package kz.kris.trenazher.repository;

import kz.kris.trenazher.model.Primer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PrimerRepository extends JpaRepository <Primer,Long> {

}
