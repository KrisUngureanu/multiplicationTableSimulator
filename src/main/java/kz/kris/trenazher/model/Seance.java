package kz.kris.trenazher.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "seance", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HistoryAnswer> answerList;

    @Column(nullable = false)
    private LocalTime time;


    public void setTimestamp(LocalDateTime now) {
        this.time = now.toLocalTime();
    }
}
