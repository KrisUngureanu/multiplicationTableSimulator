package kz.kris.trenazher.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HistoryAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "primer_id")
    private Primer primer;
    private int resMulti;
    private double resDiv;
    private boolean isCorrect;
    @ManyToOne
    @JoinColumn(name = "seance_id", nullable = false)
    private Seance seance;
    private String comment;
    public HistoryAnswer(Primer primer, int answ, int i, boolean isCorrect, Seance seance) {
        this.primer = primer;
        this.resMulti = answ;
        this.resDiv = (double) answ / i;
        this.isCorrect = isCorrect;
        this.seance = seance;

    }
}
