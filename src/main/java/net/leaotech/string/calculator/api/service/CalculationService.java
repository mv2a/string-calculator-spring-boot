package net.leaotech.string.calculator.api.service;

import net.leaotech.string.calculator.api.domain.Calculation;
import net.leaotech.string.calculator.api.exception.InvalidInputException;
import net.leaotech.string.calculator.api.exception.NegativeNumberException;
import net.leaotech.string.calculator.api.repository.CalculationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.Collection;

@Service
public class CalculationService {
    private static final int LIMIT = 1000;
    private static char DELIMITER = ',';
    private static String DEFAULT = "0";

    private CalculationsRepository calculationsRepository;

    @Autowired
    public CalculationService(CalculationsRepository calculationsRepository) {
        this.calculationsRepository = calculationsRepository;
    }

    public String calculate(String inputExpression)
            throws NegativeNumberException, InvalidInputException {
        Calculation calculation = new Calculation();
        calculation.setDate(Date.from(Instant.now()));

        calculationsRepository.storeCalculation(calculation);
        if (inputExpression == null || inputExpression.isEmpty()) {
            calculation.setCalculationResult(DEFAULT);
        } else {
            inputExpression = prepareInput(inputExpression);
            if (inputExpression.indexOf(DELIMITER) > 0) { //check if it is present, also not the first char
                String[] values = inputExpression.split(String.valueOf(DELIMITER));
                int result = 0;
                for (String s : values) {
                    int number = getNumber(s);
                    if (number > LIMIT)
                        continue;
                    result += number;
                }
                calculation.setCalculationResult(String.valueOf(result));
            } else {
                int number = getNumber(inputExpression);
                if (number > LIMIT)  //assumption, input contains a single number greater than 1000 then return "0"
                    calculation.setCalculationResult(DEFAULT);
                else
                    calculation.setCalculationResult(String.valueOf(number));
            }
        }
        calculation.setResult(true);
        return calculation.toString();
    }

    public Calculation getCalculation(int calculationId) {
        return calculationsRepository.getCalculation(calculationId);
    }

    public Collection<Calculation> getAllCalculations() {
        return calculationsRepository.getCalculations();
    }

    private static int getNumber(String text) throws InvalidInputException, NegativeNumberException {
        int number;
        try {
            number = Integer.parseInt(text);
            if (number < 0) {
                throw new NegativeNumberException("Negative number are not allowed");
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid input, only numbers allowed");
        }
        return number;
    }

    private static String prepareInput(String inputExpression) {
        inputExpression = inputExpression.replaceAll("\\n|\\\\n", ",").replace(",,", ",");
        if (inputExpression.charAt(inputExpression.length() - 1) == ',')
            inputExpression = inputExpression.substring(0, inputExpression.length());
        return inputExpression;
    }
}
