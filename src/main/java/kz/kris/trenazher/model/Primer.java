package kz.kris.trenazher.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Primer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int operand1;

    @Column(nullable = false)
    private int operand2;

    @Column(insertable = false, updatable = false)
    private int multiplicationResult;

    @Column(insertable = false, updatable = false)
    private double divisionResult;

    public Primer() {
        // No-args constructor for JPA
    }

    public Primer(int operand1, int operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.multiplicationResult = operand1 * operand2;
        if (operand2 != 0) {
            this.divisionResult = (double) operand1 / operand2;
        } else {
            this.divisionResult = Double.NaN; // Assign NaN for division by zero
        }
    }

    public int getOperand1() {
        return operand1;
    }

    public void setOperand1(int operand1) {
        this.operand1 = operand1;
        this.multiplicationResult = operand1 * this.operand2;
        if (operand2 != 0) {
            this.divisionResult = (double) operand1 / this.operand2;
        } else {
            this.divisionResult = Double.NaN;
        }
    }

    public int getOperand2() {
        return operand2;
    }

    public void setOperand2(int operand2) {
        this.operand2 = operand2;
        this.multiplicationResult = this.operand1 * operand2;
        if (operand2 != 0) {
            this.divisionResult = (double) this.operand1 / operand2;
        } else {
            this.divisionResult = Double.NaN;
        }
    }

    public int getMultiplicationResult() {
        return multiplicationResult;
    }

    public double getDivisionResult() {
        return divisionResult;
    }

}
