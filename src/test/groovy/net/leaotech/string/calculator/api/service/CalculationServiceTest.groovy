package net.leaotech.string.calculator.api.service

import net.leaotech.string.calculator.api.exception.InvalidInputException
import net.leaotech.string.calculator.api.exception.NegativeNumberException
import net.leaotech.string.calculator.api.repository.CalculationsRepository
import net.leaotech.string.calculator.api.repository.CalculationsRepositoryInMemory
import spock.lang.Specification
import spock.lang.Unroll

class CalculationServiceTest extends Specification {

    CalculationService calculationService

    CalculationsRepository calculationsRepository = new CalculationsRepositoryInMemory()

    def setup() {
        calculationService = new CalculationService(calculationsRepository)
    }

    @Unroll
    def "handles empty and single number input"() {
        when:
            def calculateResponse = calculationService.calculate(input)
        then:
            calculateResponse == expectedCalculateResponse
        where:
            input    | expectedCalculateResponse
            ""       | "0"
            null     | "0"
            "1"      | "1"
            "15"     | "15"
            "1000"   | "1000"
    }

    @Unroll //note that this also covers calculationService.prepareInput
    def "processes the calculation correctly"() {
        when:
            def resp = calculationService.calculate(input)
        then: "an empty string returns zero"
            resp == expected
        where:
            input        | expected
            "3,4\n"      | "7"
            "1,2,\n3,5"  | "11"
            "2,4\n6"     | "12"
            "1,10001,2"  | "3"
            "1,10001\n,2"| "3"
    }

    def "repository stores calculations correctly"() {
        given:
            calculationService.calculate("1,2")
            calculationService.calculate("1")
            calculationService.calculate("3,4\n")

        when:
            def calculations = calculationService.getAllCalculations()
        then:
            calculations.size() == 3
            calculations.stream().allMatch({c -> c.result })
        and:
            calculationService.getCalculation(1) as String == "3"
            calculationService.getCalculation(2) as String == "1"
            calculationService.getCalculation(3) as String == "7"
    }

    def "calculation errors are stored but have no result"() {
        when:
            calculationService.calculate("abc")
        then:
            thrown InvalidInputException
            calculationService.getAllCalculations().size() == 1 && !calculationService.getAllCalculations()[0].result

        when:
            calculationService.calculate("-1")
        then: "there is no calculation result but calculation dates are set regardless"
            thrown NegativeNumberException
            calculationService.getAllCalculations().size() == 2
            calculationService.getAllCalculations().stream().allMatch({c -> !c.result && c.date != null})
    }
}
