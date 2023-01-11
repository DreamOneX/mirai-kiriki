package com.github.dreamonex.mirai.kiriki.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Player implements Comparable<Player> {
    public boolean isComputer;
    public DicesGroup dicesGroup;
    private long id;
    private int totalScore;
    private int lowerScore;
    private int grandScore;
    private int additionalTurns;
    private int remainingRollChance;
    private List<DicesWidgetHelper.Kinds> usedKinds;
    private Map<DicesWidgetHelper.Kinds, Integer> kindsToScore;

    private class DicesGroup {
        private Map<Dice, Boolean> dices = Collections.synchronizedMap(new HashMap<Dice, Boolean>(5));

        public DicesGroup() {
            for (int i = 0; i < 5; i++) {
                dices.put(new Dice(), false);
            }
        }

        public void selectDice(Dice dice) throws IllegalArgumentException {
            if (dices.containsKey(dice)) {
                dices.put(dice, true);
            } else {
                throw new IllegalArgumentException("The dice is not in the group");
            }
        }

        public void selectAllDice() {
            for (Dice dice : dices.keySet()) {
                dices.put(dice, true);
            }
        }

        public void unselectDice(Dice dice) throws IllegalArgumentException {
            if (dices.containsKey(dice)) {
                dices.put(dice, false);
            } else {
                throw new IllegalArgumentException("The dice is not in the group");
            }
        }

        public void unselectAllDices() {
            for (Dice dice : dices.keySet()) {
                dices.put(dice, false);
            }
        }

        public void rollSelectedDices() throws IllegalStateException {
            if (remainingRollChance <= 0)
                throw new IllegalStateException("No more roll chance");
            for (Dice dice : dices.keySet()) {
                if (dices.get(dice)) {
                    dice.roll();
                }
            }
            remainingRollChance--;
        }

        public void rollAllDices() throws IllegalStateException {
            if (remainingRollChance <= 0)
                throw new IllegalStateException("No more roll chance");
            for (Dice dice : dices.keySet()) {
                dice.roll();
            }
            remainingRollChance--;
        }

        public List<Dice> getSelectedDices() {
            List<Dice> selectedDices = new ArrayList<Dice>();
            for (Dice dice : dices.keySet()) {
                if (dices.get(dice)) {
                    selectedDices.add(dice);
                }
            }
            return selectedDices;
        }

        public List<Dice> getAllDices() {
            List<Dice> allDices = new ArrayList<Dice>();
            for (Dice dice : dices.keySet()) {
                allDices.add(dice);
            }
            return allDices;
        }

        public Map<Dice, Boolean> getDicesMap() {
            return dices;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Dice dice : dices.keySet()) {
                sb.append(dice.toString())
                        .append(" ")
                        .append(dices.get(dice))
                        .append("\n");
            }
            return sb.toString();
        }
    }

    public Player(long id) {
        this.id = id;
        this.totalScore = 0;
        this.remainingRollChance = 3;
        this.isComputer = false;
        this.lowerScore = 0;
        this.grandScore = 0;
        this.additionalTurns = -1;
        this.usedKinds = Collections.synchronizedList(new LinkedList<DicesWidgetHelper.Kinds>());
        this.kindsToScore = Collections.synchronizedMap(new HashMap<DicesWidgetHelper.Kinds, Integer>());
        this.dicesGroup = new DicesGroup();
    }

    public long getId() {
        return id;
    }

    public int getTotalScore() {
        return totalScore;
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

    public List<DicesWidgetHelper.Kinds> getUsedKinds() {
        return usedKinds;
    }

    // TODO: 等待 Java 实现友元(划掉)
    protected void freshRemainingRollChance() {
        remainingRollChance = 3;
    }

    public void useKind(DicesWidgetHelper.Kinds kind) throws IllegalArgumentException {
        if (usedKinds.contains(kind))
            throw new IllegalArgumentException("The kind is already used.");
        synchronized (this) {
            int score = DicesWidgetHelper.getScore(dicesGroup.getAllDices(), kind);
            kindsToScore.put(kind, score);
            if (kind.ordinal() < 6) {
                lowerScore += score;
                if (lowerScore >= 63) {
                    lowerScore += 35;
                    kindsToScore.put(DicesWidgetHelper.Kinds.GREATER_THAN_SIXTY_TWO, 35);
                    totalScore += 35;
                }
            } else {
                grandScore += score;
            }
            if (score == 50 && kind == DicesWidgetHelper.Kinds.KIRIKI)
                additionalTurns++;
            else
                usedKinds.add(kind);
            totalScore += score;
        }
    }

    @Override
    public int compareTo(Player o) {
        return Integer.compare(o.totalScore, totalScore);
    }
}
