package github.snomfish.domain;

import github.snomfish.functionality.copy.DeepCopyable;

import static github.snomfish.domain.SideId.*;

// the gamestate is mutable.
// then branching the gamestate is copied and the new changes are applied to each gamestate
// should cut down on garbage collection
public class Gamestate implements DeepCopyable<Gamestate> {
    
    
    private Side playerSide;
    private Side enemySide;
    // this is silly and should probably be an enum map
    //private final Map<FieldEffect, Boolean> fieldEffects = new HashMap<>();


    public Gamestate(
        Side playerSide, 
        Side enemySide
    ) {
        this.playerSide = playerSide;
        this.enemySide = enemySide;
    }

    
    // deepcopy
    public Gamestate deepCopy() {
        return new Gamestate(
            playerSide.deepCopy(),
            enemySide.deepCopy()
        );
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


    // setter
    public void setPlayerSide(Side playerSide) {this.playerSide = playerSide;}
    public void setEnemySide(Side enemySide) {this.enemySide = enemySide;} 
    public void setSide(SideId sideId, Side side) {
        switch (sideId) {
            case PLAYER: this.playerSide = side;
            case ENEMY: this.enemySide = side;
            default:
                throw new IllegalArgumentException("Unkown side: " + sideId);
        }
    }
}
