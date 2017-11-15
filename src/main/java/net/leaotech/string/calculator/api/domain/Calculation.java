package net.leaotech.string.calculator.api.domain;

import java.util.Date;

public class Calculation {

    private int id;

    private boolean result = false;

    private Date date;

    private String calculationResult;

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCalculationResult(String calculationResult) {
        this.calculationResult = calculationResult;
    }

    public String toString() {
        return calculationResult;
    }
}
