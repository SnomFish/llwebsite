package github.snomfish.domain;

import java.util.HashMap;
import java.util.Map;

public class Gamestate {
    
    
    private final Side playerSide;
    private final Side enemySide;
    private final Map<FieldEffect, Boolean> fieldEffects = new HashMap<>();


    public Gamestate(
        Side playerSide, 
        Side enemySide
    ) {
        this.playerSide = playerSide;
        this.enemySide = enemySide;
    }

    // getters
    public Side getPlayerSide() {
        return playerSide;
    }
    public Side getEnemySide() {
        return enemySide;
    }
    public Side getSide(SideId sideId) {
        switch (sideId) {
            case PLAYER: return playerSide;
            case ENEMY: return enemySide;
            default: 
                throw new IllegalArgumentException("Unknown side: " + sideId);
        }
    }
}
