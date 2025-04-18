package kz.kris.trenazher.repository;

import kz.kris.trenazher.model.HistoryAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryAnswer, Long>{
}
