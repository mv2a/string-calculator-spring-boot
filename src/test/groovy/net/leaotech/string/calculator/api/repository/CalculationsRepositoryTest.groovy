package net.leaotech.string.calculator.api.repository

import net.leaotech.string.calculator.api.domain.Calculation
import spock.lang.Specification

class CalculationsRepositoryTest extends Specification {

    def "Stores and retrieves a calculation"() {
        when:
            CalculationsRepository calculationsRepository = new CalculationsRepositoryInMemory()
            def calculation = new Calculation()
            calculationsRepository.storeCalculation(calculation)
        then:
            !calculationsRepository.calculations.isEmpty()
        and:
            calculationsRepository.getCalculation(1) == calculation
    }

    def "clears calculations"() {
        when:
            def repo = new CalculationsRepositoryInMemory()
            repo.calculationsStore << [1:_, 2:_, 3:_]
        then: "the repo store has some data"
            !repo.getCalculations().isEmpty() && repo.calculationsStore.size() == 3
        then: "after calling clear calculation it becomes empty"
            repo.clearCalculations()
            repo.getCalculations().isEmpty()
    }

    def "returns the list of calculations"() {
        given: "a list of calculations"
            def repo = new CalculationsRepositoryInMemory()
            def calc = new Calculation()
            repo.calculationsStore << [1:calc, 2:calc, 3:calc, 4:calc]
        when:
            def resp = repo.getCalculations()
        then:
            resp.size() == 4
            resp.stream().allMatch({c -> c instanceof Calculation})
    }
}
