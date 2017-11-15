package net.leaotech.string.calculator.api.repository;

import net.leaotech.string.calculator.api.domain.Calculation;

import java.util.Collection;

public interface CalculationsRepository {

    void storeCalculation(Calculation calculation);

    Calculation getCalculation(int id);

    void clearCalculations();

    Collection<Calculation> getCalculations();
}
