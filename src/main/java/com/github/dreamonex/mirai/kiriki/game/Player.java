package com.github.dreamonex.mirai.kiriki.game;

public final class Player implements Comparable<Player> {
    private final long id;
    private int score;
    // A player should have 5 dices at total （ArrayList is not used here because it is not thread-safe）
    private final Dice[] dices = new Dice[5];

    public Player(long id) {
        this.id = id;
        this.score = 0;
        for (int i = 0; i < 5; i++) {
            dices[i] = new Dice();
        }
    }

    public final long getId() {
        return id;
    }

    public final int getScore() {
        return score;
    }

    @Override
    public final int compareTo(Player o) {
        return Integer.compare(o.score, score);
    }
}
