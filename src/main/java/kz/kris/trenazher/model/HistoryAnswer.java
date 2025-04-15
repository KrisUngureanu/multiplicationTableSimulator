package kz.kris.trenazher.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryAnswer {
    private Primer primer;
    private int resMulti;
    private double resDiv;
    private boolean isCorrect;
}
