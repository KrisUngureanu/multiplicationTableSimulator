package kz.kris.trenazher.service;

import kz.kris.trenazher.model.Primer;
import kz.kris.trenazher.repository.PrimerRepository;
import org.springframework.stereotype.Service;
import java.util.Collections;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrimerService {
    private final PrimerRepository primerRepository;

    public PrimerService(PrimerRepository primerRepository) {
        this.primerRepository = primerRepository;
    }


    public List<Primer> give4Primer(){
        List<Primer> primerList = primerRepository.findAll();
        Collections.shuffle(primerList);

        if (primerList.size() > 4) {
            return primerList.subList(0, 4);
        } else {
            // Если элементов меньше 4, возвращаем весь список
            return primerList;
        }

    }

    public List<Primer> give4PrimerForOperand(int operand){
        // Получаем все записи из репозитория
        List<Primer> primerList = primerRepository.findAll();

        // Фильтруем записи по заданному operand1
        List<Primer> filteredList = primerList.stream()
                .filter(primer -> primer.getOperand1() == operand)
                .collect(Collectors.toList());

        // Перемешиваем отфильтрованный список
        Collections.shuffle(filteredList);

        // Возвращаем случайные 4 элемента или весь список, если элементов меньше 4
        if (filteredList.size() > 4) {
            return filteredList.subList(0, 4);
        } else {
            return filteredList;
        }


    }

    public Primer getPrimerById(Long id){
        return primerRepository.findById(id).orElse(null);
    }
}
