package net.leaotech.string.calculator.api.repository;

import net.leaotech.string.calculator.api.domain.Calculation;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CalculationsRepositoryInMemory implements CalculationsRepository {

    private final AtomicInteger counter = new AtomicInteger(0);
    private final Map<Integer, Calculation> calculationsStore = new ConcurrentHashMap<>();

    @Override
    public void storeCalculation(Calculation calculation) {
        int id = counter.incrementAndGet();
        calculation.setId(id);
        calculationsStore.putIfAbsent(id, calculation);
    }

    @Override
    public Calculation getCalculation(int id) {
        return calculationsStore.get(id);
    }

    @Override
    public void clearCalculations() {
        calculationsStore.clear();
    }

    @Override
    public Collection<Calculation> getCalculations() {
        return calculationsStore.values();
    }
}
