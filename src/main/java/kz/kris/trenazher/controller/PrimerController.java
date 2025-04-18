package kz.kris.trenazher.controller;


import kz.kris.trenazher.model.HistoryAnswer;
import kz.kris.trenazher.model.Primer;
import kz.kris.trenazher.model.Seance;
import kz.kris.trenazher.repository.HistoryRepository;
import kz.kris.trenazher.repository.SeanceRepository;
import kz.kris.trenazher.service.HistoryService;
import kz.kris.trenazher.service.PrimerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PrimerController {

    private final PrimerService primerService;
    private final HistoryRepository historyRepository;
    private final HistoryService historyService;
    private final SeanceRepository seanceRepository;
    public PrimerController(PrimerService primerService, HistoryRepository historyRepository, HistoryService historyService, SeanceRepository seanceRepository) {
        this.primerService = primerService;
        this.historyRepository = historyRepository;
        this.historyService = historyService;
        this.seanceRepository = seanceRepository;
    }


    @PostMapping("/submit-reshenie")
    public String processReshenie(@RequestParam Map<String, String> allParams, Model model) {
        Seance seance = new Seance();
        List<HistoryAnswer> seanceAnswers = new ArrayList<>();
        seance.setTimestamp(java.time.LocalDateTime.now());
        seanceRepository.save(seance);


        // Разделим ответы на умножение и деление
        Map<Long, String> multiplicationAnswers = new HashMap<>();
        Map<Long, String> divisionAnswers = new HashMap<>();
        Map<Long, String> multiplicationAnswersObrat = new HashMap<>();
        Map<Long, String> divisionAnswersObrat = new HashMap<>();

        allParams.forEach((key, value) -> {
            if (key.startsWith("answer-multiply-")) {
                Long primerId = Long.parseLong(key.replace("answer-multiply-", ""));
                multiplicationAnswers.put(primerId, value);

            } else if (key.startsWith("answer-divide-")) {

                Long primerId = Long.parseLong(key.replace("answer-divide-", ""));
                divisionAnswers.put(primerId, value);

            } else if (key.startsWith("answer-multiplyObrat-")){
                Long primerId = Long.parseLong(key.replace("answer-multiplyObrat-", ""));
                multiplicationAnswersObrat.put(primerId, value);
            } else if (key.startsWith("answer-divideObrat-")){
                Long primerId = Long.parseLong(key.replace("answer-divideObrat-", ""));
                divisionAnswersObrat.put(primerId, value);
            }
        });

        // Проверка правильности обработки (в сервисе должна быть логика для этого)
        List<HistoryAnswer> multiplicationResults = checkMultiplicationAnswers(multiplicationAnswers, seance);
        List<HistoryAnswer> multiplicationResultsObrat = checkMultiplicationAnswers(multiplicationAnswersObrat, seance);
        List<HistoryAnswer> divisionResults = checkDivisionAnswers(divisionAnswers, seance);
        List<HistoryAnswer> divisionResultsObrat = checkDivisionAnswersObrat(divisionAnswersObrat, seance);
        // Добавим результаты проверки в модель для вывода пользователю
        model.addAttribute("multiplicationResults", multiplicationResults);
        model.addAttribute("divisionResults", divisionResults);
        model.addAttribute("multiplicationResultsObrat", multiplicationResultsObrat);
        model.addAttribute("divisionResultsObrat", divisionResultsObrat);
        seanceAnswers.addAll(multiplicationResults);
        seanceAnswers.addAll(multiplicationResultsObrat);
        seanceAnswers.addAll(divisionResultsObrat);
        seanceAnswers.addAll(divisionResults);


        seanceRepository.save(seance);
        // Вернуть имя шаблона для показа результатов
        return "results_summary";
    }

    private List<HistoryAnswer> checkMultiplicationAnswers(Map<Long, String> answers, Seance seance) {
        List<HistoryAnswer> historyAnswers = new ArrayList<>();
      //  Map<Primer, Boolean> results = new HashMap<>();
        answers.forEach((id, answer) -> {
            Primer primer = primerService.getPrimerById(id);
            int correctAnswer = primer.getMultiplicationResult();

           // results.put(primer, Integer.parseInt(answer) == correctAnswer);
            int answ = Integer.parseInt(answer);
            String text = primer.getOperand1() + " * " + primer.getOperand2() + " = " + answ;
            boolean isCorrect = Integer.parseInt(answer) == correctAnswer;
            HistoryAnswer historyAnswer = new HistoryAnswer(primer, answ, 0, isCorrect, seance);
            historyAnswer.setComment(text);
            historyRepository.save(historyAnswer);

            historyAnswers.add(historyAnswer);

        });
        return historyAnswers;
    }

    private List<HistoryAnswer> checkDivisionAnswers(Map<Long, String> answers, Seance seance) {
        // Здесь должна быть логика сверки ответов на деление
        List<HistoryAnswer> historyAnswers = new ArrayList<>();
        //Map<Primer, Boolean> results = new HashMap<>();
        answers.forEach((id, answer) -> {
            Primer primer = primerService.getPrimerById(id);

            double correctAnswer = primer.getDivisionResult();
          //  results.put(primer, Integer.parseInt(answer) == correctAnswer);

            int answ = Integer.parseInt(answer);
            int promezhutok = primer.getOperand1() * primer.getOperand2();
            String text = promezhutok + " / " + primer.getOperand1() + " = " + answ;
            boolean isCorrect = Integer.parseInt(answer) == correctAnswer;
            HistoryAnswer historyAnswer = new HistoryAnswer(primer, 0, answ, isCorrect, seance);
            historyAnswer.setComment(text);

            historyAnswers.add(historyAnswer);
            historyRepository.save(historyAnswer);
        });
        return historyAnswers;
    }

    private List<HistoryAnswer> checkDivisionAnswersObrat(Map<Long, String> answers, Seance seance) {
        // Здесь должна быть логика сверки ответов на деление
        List<HistoryAnswer> historyAnswers = new ArrayList<>();
        //Map<Primer, Boolean> results = new HashMap<>();
        answers.forEach((id, answer) -> {
            Primer primer = primerService.getPrimerById(id);
            double oper = primer.getOperand1() * primer.getOperand2();
            double correctAnswer = (int) oper / primer.getOperand2();
            //  results.put(primer, Integer.parseInt(answer) == correctAnswer);
            int answ = Integer.parseInt(answer);
            int promezhutok = primer.getOperand1() * primer.getOperand2();
            String text = promezhutok + " / " + primer.getOperand2() + " = " + answ;
            boolean isCorrect = Integer.parseInt(answer) == correctAnswer;
            HistoryAnswer historyAnswer = new HistoryAnswer(primer, 0, answ, isCorrect, seance);
            historyAnswer.setComment(text);

            historyAnswers.add(historyAnswer);
            historyRepository.save(historyAnswer);
        });
        return historyAnswers;
    }
}


