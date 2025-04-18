package kz.kris.trenazher.service;

import kz.kris.trenazher.repository.HistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }
}
