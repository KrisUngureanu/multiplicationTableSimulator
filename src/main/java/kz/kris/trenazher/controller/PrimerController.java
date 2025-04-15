package kz.kris.trenazher.controller;


import kz.kris.trenazher.model.HistoryAnswer;
import kz.kris.trenazher.model.Primer;
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

    public PrimerController(PrimerService primerService) {
        this.primerService = primerService;
    }


    @PostMapping("/submit-reshenie")
    public String processReshenie(@RequestParam Map<String, String> allParams, Model model) {
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
        List<HistoryAnswer> multiplicationResults = checkMultiplicationAnswers(multiplicationAnswers);
        List<HistoryAnswer> multiplicationResultsObrat = checkMultiplicationAnswers(multiplicationAnswersObrat);
        List<HistoryAnswer>  divisionResults = checkDivisionAnswers(divisionAnswers);
        List<HistoryAnswer>  divisionResultsObrat = checkDivisionAnswersObrat(divisionAnswersObrat);
        // Добавим результаты проверки в модель для вывода пользователю
        model.addAttribute("multiplicationResults", multiplicationResults);
        model.addAttribute("divisionResults", divisionResults);
        model.addAttribute("multiplicationResultsObrat", multiplicationResultsObrat);
        model.addAttribute("divisionResultsObrat", divisionResultsObrat);

        // Вернуть имя шаблона для показа результатов
        return "results_summary";
    }

    private List<HistoryAnswer> checkMultiplicationAnswers(Map<Long, String> answers) {
        List<HistoryAnswer> historyAnswers = new ArrayList<>();
      //  Map<Primer, Boolean> results = new HashMap<>();
        answers.forEach((id, answer) -> {
            Primer primer = primerService.getPrimerById(id);
            int correctAnswer = primer.getMultiplicationResult();
           // results.put(primer, Integer.parseInt(answer) == correctAnswer);
            int answ = Integer.parseInt(answer);
            boolean isCorrect = Integer.parseInt(answer) == correctAnswer;
            HistoryAnswer historyAnswer = new HistoryAnswer(primer, answ, 0, isCorrect);
            historyAnswers.add(historyAnswer);
        });
        return historyAnswers;
    }

    private List<HistoryAnswer> checkDivisionAnswers(Map<Long, String> answers) {
        // Здесь должна быть логика сверки ответов на деление
        List<HistoryAnswer> historyAnswers = new ArrayList<>();
        //Map<Primer, Boolean> results = new HashMap<>();
        answers.forEach((id, answer) -> {
            Primer primer = primerService.getPrimerById(id);

            double correctAnswer = primer.getDivisionResult();
          //  results.put(primer, Integer.parseInt(answer) == correctAnswer);
            int answ = Integer.parseInt(answer);
            boolean isCorrect = Integer.parseInt(answer) == correctAnswer;
            HistoryAnswer historyAnswer = new HistoryAnswer(primer, 0, answ, isCorrect);
            historyAnswers.add(historyAnswer);
        });
        return historyAnswers;
    }

    private List<HistoryAnswer> checkDivisionAnswersObrat(Map<Long, String> answers) {
        // Здесь должна быть логика сверки ответов на деление
        List<HistoryAnswer> historyAnswers = new ArrayList<>();
        //Map<Primer, Boolean> results = new HashMap<>();
        answers.forEach((id, answer) -> {
            Primer primer = primerService.getPrimerById(id);
            double oper = primer.getOperand1() * primer.getOperand2();
            double correctAnswer = (int) oper / primer.getOperand2();
            //  results.put(primer, Integer.parseInt(answer) == correctAnswer);
            int answ = Integer.parseInt(answer);
            boolean isCorrect = Integer.parseInt(answer) == correctAnswer;
            HistoryAnswer historyAnswer = new HistoryAnswer(primer, 0, answ, isCorrect);
            historyAnswers.add(historyAnswer);
        });
        return historyAnswers;
    }
}


