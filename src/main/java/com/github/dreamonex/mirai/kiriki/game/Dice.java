package com.github.dreamonex.mirai.kiriki.game;

import java.util.Random;

public final class Dice implements Comparable<Dice> {
    private final Random random = new Random();
    private int value;

    public Dice() {
        this.value = random.nextInt(6) + 1;
    }

    public Dice(int value) throws IllegalArgumentException {
        if (value < 1 || value > 6) {
            throw new IllegalArgumentException("value must be between 1 and 6");
        }
        this.value = value;
    }

    public final void roll() {
        value = random.nextInt(6) + 1;
    }

    public final int getValue() {
        return value;
    }

    @Override
    public final int compareTo(Dice other) {
        return Integer.compare(value, other.value);
    }

    @Override
    public final String toString() {
        return Integer.toString(value);
    }

    @Override
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Dice dice = (Dice) other;
        return value == dice.value;
    }

    @Override
    public final int hashCode() {
        return value;
    }
}
