package com.github.dreamonex.mirai.kiriki.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedList;

public class Player implements Comparable<Player> {
    private long id;
    private int score;
    private int lowerScore;
    private int grandScore;
    private int additionalTurns;
    private int remainingRollChance;
    public boolean isComputer;
    // A player should have 5 dices at total （ArrayList is not used here because it is not thread-safe）
    private List<Dice> dices = Collections.synchronizedList(new ArrayList<Dice>(5));
    private List<Dice> dicesToRoll = Collections.synchronizedList(new LinkedList<Dice>());
    private List<DicesWidgetHelper.Kinds> usedKinds = Collections.synchronizedList(new LinkedList<DicesWidgetHelper.Kinds>());
    private Map<DicesWidgetHelper.Kinds, Integer> kindsToScore = Collections.synchronizedMap(new HashMap<DicesWidgetHelper.Kinds, Integer>());

    public Player(long id) {
        this.id = id;
        this.score = 0;
        this.remainingRollChance = 3;
        this.isComputer = false;
        this.lowerScore = 0;
        this.grandScore = 0;
        this.additionalTurns = -1;
        synchronized (dices) {
            for (int i = 0; i < 5; i++) dices.add(new Dice());
        }
    }

    public long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getLowerScore() {
        return lowerScore;
    }

    public int getGrandScore() {
        return grandScore;
    }

    public int getRemainingRollChance() {
        return remainingRollChance;
    }

    public int getAdditionalTurns() {
        return additionalTurns;
    }

    public List<Dice> getDices() {
        return dices;
    }

    public List<DicesWidgetHelper.Kinds> getUsedKinds() {
        return usedKinds;
    }

    public List<Dice> getSelectedDices() {
        return dicesToRoll;
    }

    // TODO: 等待 Java 实现友元
    protected void freshRemainingRollChance() {
        remainingRollChance = 3;
    }

    public void selectDicesToRoll(int[] dicesToRoll) throws IndexOutOfBoundsException {
        synchronized (this.dicesToRoll) {
            this.dicesToRoll.clear();
            for (int i = 0; i < dicesToRoll.length; i++) {
                this.dicesToRoll.add(dices.get(dicesToRoll[i]));
            }
        }
    }

    public void addToDiceToRoll(int diceIndex) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (dicesToRoll.contains(dices.get(diceIndex))) throw new IllegalArgumentException("The dice is already selected.");
        synchronized (dicesToRoll) {
            dicesToRoll.add(dices.get(diceIndex));
        }
    }

    public void rollDices() throws IllegalStateException {
        if (remainingRollChance <= 0) throw new IllegalStateException("No remaining roll chance");
        synchronized (dicesToRoll) {
            for (Dice dice : dicesToRoll) {
                dice.roll();
            }
            dicesToRoll.clear();
        }
    }

    public void useKind(DicesWidgetHelper.Kinds kind) throws IllegalArgumentException {
        if (usedKinds.contains(kind)) throw new IllegalArgumentException("The kind is already used.");
        synchronized (this) {
            score += DicesWidgetHelper.getScore(dices, kind);
            kindsToScore.put(kind, DicesWidgetHelper.getScore(dices, kind));
            if (kind == DicesWidgetHelper.Kinds.ONES || kind == DicesWidgetHelper.Kinds.TWOS || kind == DicesWidgetHelper.Kinds.THREES || kind == DicesWidgetHelper.Kinds.FOURS || kind == DicesWidgetHelper.Kinds.FIVES || kind == DicesWidgetHelper.Kinds.SIXES) {
                lowerScore += DicesWidgetHelper.getScore(dices, kind);
                if (lowerScore >= 63) {
                    lowerScore += 35;
                    score += 35;
                    kindsToScore.put(DicesWidgetHelper.Kinds.GREATER_THAN_SIXTY_TWO, 35);
                }
            }
            if (kind == DicesWidgetHelper.Kinds.THREE_OF_A_KIND || kind == DicesWidgetHelper.Kinds.FOUR_OF_A_KIND || kind == DicesWidgetHelper.Kinds.FULL_HOUSE || kind == DicesWidgetHelper.Kinds.SMALL_STRAIGHT || kind == DicesWidgetHelper.Kinds.LARGE_STRAIGHT || kind == DicesWidgetHelper.Kinds.KIRIKI || kind == DicesWidgetHelper.Kinds.CHANCE) {
                grandScore += DicesWidgetHelper.getScore(dices, kind);
            }
            if (kind == DicesWidgetHelper.Kinds.KIRIKI && DicesWidgetHelper.getScore(dices, kind) == 50) additionalTurns += 1;
            else usedKinds.add(kind);
        }
    }

    @Override
    public int compareTo(Player o) {
        return Integer.compare(o.score, score);
    }
}
