package github.snomfish.domain;

import java.util.HashMap;
import java.util.Map;

// the gamestate is mutable.
// then branching the gamestate is copied and the new changes are applied to each gamestate
// should cut down on garbage collection
public class Gamestate {
    
    
    private Side playerSide;
    private Side enemySide;
    private Map<FieldEffect, Boolean> fieldEffects = new HashMap<>();


    public Gamestate(
        Side playerSide, 
        Side enemySide
    ) {
        this.playerSide = playerSide;
        this.enemySide = enemySide;
    }

    
    // getters
    public Side playerSide() {return playerSide;}
    public Side enemySide() {return enemySide;}
    public Side side(SideId sideId) {
        switch (sideId) {
            case PLAYER: return playerSide;
            case ENEMY: return enemySide;
            default: 
                throw new IllegalArgumentException("Unknown side: " + sideId);
        }
    }
}
