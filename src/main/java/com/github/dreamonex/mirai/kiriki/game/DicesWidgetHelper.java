package com.github.dreamonex.mirai.kiriki.game;

import java.util.List;

public final class DicesWidgetHelper {
    public static enum Kinds {
        ONES, TWOS, THREES, FOURS, FIVES, SIXES, GREATER_THAN_SIXTY_TWO,
        THREE_OF_A_KIND, FOUR_OF_A_KIND,
        FULL_HOUSE, SMALL_STRAIGHT, LARGE_STRAIGHT, CHANCE, KIRIKI
    }

    private final static int getSimilar(Dice[] dices, int value) {
        int count = 0;
        for (Dice dice : dices) {
            if (dice.getValue() == value) {
                count++;
            }
        }
        return count;
    }

    private final static int totalSum(Dice[] dices) {
        int sum = 0;
        for (Dice dice : dices) {
            sum += dice.getValue();
        }
        return sum;
    }

    private final static int getNOfAKind(Dice[] dices, int n) {
        int i = 1;
        boolean have = false;
        while (! have && i <= 6) {
            if (getSimilar(dices, i) >= n) have = true;
            i++;
        }
        if (have) return totalSum(dices);
        else return 0;
    }

    public final static int getOnes(Dice[] dices) {
        return getSimilar(dices, 1);
    }

    public final static int getTwos(Dice[] dices) {
        return getSimilar(dices, 2) * 2;
    }

    public final static int getThrees(Dice[] dices) {
        return getSimilar(dices, 3) * 3;
    }

    public final static int getFours(Dice[] dices) {
        return getSimilar(dices, 4) * 4;
    }

    public final static int getFives(Dice[] dices) {
        return getSimilar(dices, 5) * 5;
    }

    public final static int getSixes(Dice[] dices) {
        return getSimilar(dices, 6) * 6;
    }

    public final static int getThreeOfAKind(Dice[] dices) {
        return getNOfAKind(dices, 3);
    }

    public final static int getFourOfAKind(Dice[] dices) {
        return getNOfAKind(dices, 4);
    }

    public final static int getFullHouse(Dice[] dices) {
        int i = 0;
        boolean three,two;
        three = false;
        while (! three && i <= 6) {
            if (getSimilar(dices, i) == 3) three = true;
            i++;
        }
        if (three) {
            two = false;
            i = 0;
            while (! two && i <= 6) {
                if (getSimilar(dices, i) == 2) two = true;
                i++;
            }
            if (two) return 25;
            else return 0;
        } else return 0;
    }

    public final static int getSmallStraight(Dice[] dices) {
        if (getSimilar(dices, 1) >= 1 && getSimilar(dices, 2) >= 1 && getSimilar(dices, 3) >= 1 && getSimilar(dices, 4) >= 1) return 30;
        else if (getSimilar(dices, 2) >= 1 && getSimilar(dices, 3) >= 1 && getSimilar(dices, 4) >= 1 && getSimilar(dices, 5) >= 1) return 30;
        else if (getSimilar(dices, 3) >= 1 && getSimilar(dices, 4) >= 1 && getSimilar(dices, 5) >= 1 && getSimilar(dices, 6) >= 1) return 30;
        else return 0;
    }

    public final static int getLargeStraight(Dice[] dices) {
        if (getSimilar(dices, 1) >= 1 && getSimilar(dices, 2) >= 1 && getSimilar(dices, 3) >= 1 && getSimilar(dices, 4) >= 1 && getSimilar(dices, 5) >= 1) return 40;
        else if (getSimilar(dices, 2) >= 1 && getSimilar(dices, 3) >= 1 && getSimilar(dices, 4) >= 1 && getSimilar(dices, 5) >= 1 && getSimilar(dices, 6) >= 1) return 40;
        else return 0;
    }

    public final static int getChance(Dice[] dices) {
        return totalSum(dices);
    }

    public final static int getKiriki(Dice[] dices) {
        if (getSimilar(dices, 1) == 5 || getSimilar(dices, 2) == 5 || getSimilar(dices, 3) == 5 || getSimilar(dices, 4) == 5 || getSimilar(dices, 5) == 5 || getSimilar(dices, 6) == 5) return 50;
        else return 0;
    }

    public final static int getScore(List<Dice> dices, Kinds kind) {
        Dice[] dicesArray = new Dice[dices.size()];
        dices.toArray(dicesArray);
        switch (kind) {
            case ONES:
                return getOnes(dicesArray);
            case TWOS:
                return getTwos(dicesArray);
            case THREES:
                return getThrees(dicesArray);
            case FOURS:
                return getFours(dicesArray);
            case FIVES:
                return getFives(dicesArray);
            case SIXES:
                return getSixes(dicesArray);
            case THREE_OF_A_KIND:
                return getThreeOfAKind(dicesArray);
            case FOUR_OF_A_KIND:
                return getFourOfAKind(dicesArray);
            case FULL_HOUSE:
                return getFullHouse(dicesArray);
            case SMALL_STRAIGHT:
                return getSmallStraight(dicesArray);
            case LARGE_STRAIGHT:
                return getLargeStraight(dicesArray);
            case CHANCE:
                return getChance(dicesArray);
            case KIRIKI:
                return getKiriki(dicesArray);
            default:
                return 0;
        }
    }
}
