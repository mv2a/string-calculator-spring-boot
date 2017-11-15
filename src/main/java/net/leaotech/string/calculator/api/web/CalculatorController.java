package net.leaotech.string.calculator.api.web;

import io.swagger.annotations.ApiParam;
import net.leaotech.string.calculator.api.exception.InvalidInputException;
import net.leaotech.string.calculator.api.exception.NegativeNumberException;
import net.leaotech.string.calculator.api.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class CalculatorController {

    private final CalculationService calculationService;

    @Autowired
    public CalculatorController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @PostMapping(path = "/calculator/string")
    public ResponseEntity<Object> calculate(
            @ApiParam(name = "input", value = "The input expression, comma separated integers."
                    + "No negative numbers allowed. Numbers greater than 1000 will be ignored.")
            @RequestBody @NotNull String input) {
        try {
            return new ResponseEntity<>(calculationService.calculate(input), HttpStatus.OK);
        } catch (InvalidInputException | NegativeNumberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
