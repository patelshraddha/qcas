/*
 */
package qcas;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Creates random collection. Used for question selection.
 * @author Deepak
 * @param <E>
 */
public class RandomCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    public void add(double weight, E result) {
        if (weight <= 0) return;
        total += weight;
        map.put(total, result);
    }
    
    /**
     * Clears the map
     */
    public void remove()
    {
        map.clear();
    }

    /**
     * gives next random entry
     * @return
     */
    public E next() {
        double value = random.nextDouble() * total;
        return map.ceilingEntry(value).getValue();
    }
}
