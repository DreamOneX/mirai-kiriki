package com.github.dreamonex.mirai.kiriki.game;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private int currentTurn;
    private int realPlayerCount;
    private int computerPlayerCount;
    private int playerCount;
    private Player currentPlayer;
    private int currentPlayerIndex;
    private Boolean isAdditionalTurn;
    private List<Player> additionalTurnPlayers;
    private List<Player> players;

    public GameSession(int realPlayerCount, int playerCount, List<Integer> realPlayerIDs) {
        this.realPlayerCount = realPlayerCount;
        this.playerCount = playerCount;
        this.computerPlayerCount = playerCount - realPlayerCount;
        this.currentTurn = 0;
        this.additionalTurnPlayers = new ArrayList<Player>();
        this.players = new ArrayList<Player>(playerCount);
        for (int i = 0; i < realPlayerCount; i++) {
            players.add(new Player(realPlayerIDs.get(i)));
        }
        for (int i = 0; i < computerPlayerCount; i++) {
            players.add(new Computer());
        }
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public int getRealPlayerCount() {
        return realPlayerCount;
    }

    public int getComputerPlayerCount() {
        return computerPlayerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Boolean getIsAdditionalTurn() {
        return isAdditionalTurn;
    }

    public List<Player> getAdditionalTurnPlayers() {
        return additionalTurnPlayers;
    }

    /*
     * @return is computer play
     */
    public Boolean nextPlayer() {
        currentPlayerIndex++;
        if ((!isAdditionalTurn && currentPlayerIndex >= playerCount) ||
                (isAdditionalTurn && currentPlayerIndex >= additionalTurnPlayers.size())) {
            currentPlayerIndex = 0;
            currentTurn++;
            if (currentTurn >= DicesWidgetHelper.Kinds.values().length) {
                this.additionalTurn();
                if (additionalTurnPlayers.size() == 0) {
                    return false;
                }
                for (Player player : additionalTurnPlayers) {
                    player.reset();
                }
            } else {
                for (Player player : players) {
                    player.reset();
                }
            }
        }
        if (isAdditionalTurn)
            currentPlayer = additionalTurnPlayers.get(currentPlayerIndex);
        else
            currentPlayer = players.get(currentPlayerIndex);
        if (currentPlayer.isComputer) {
            // TODO: ((Computer) currentPlayer).play();
            return true;
        }
        return false;
    }

    public void additionalTurn() {
        for (Player player : players) {
            if (player.getAdditionalTurns() > 0) {
                additionalTurnPlayers.add(player);
            }
        }
        if (additionalTurnPlayers.size() > 0) {
            isAdditionalTurn = true;
        } else {
            isAdditionalTurn = false;
        }
    }
}
