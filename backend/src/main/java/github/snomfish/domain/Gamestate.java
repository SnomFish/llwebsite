package github.snomfish.domain;

import github.snomfish.functionality.copy.DeepCopyable;

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


    // setter/builder
    public Builder builder() {
        return new Builder(this);
    }
    public class Builder {

        private final Gamestate copy;

        private Builder(Gamestate original) {
            copy = original.deepCopy();
        }

        public Builder playerSide(Side playerSide) {
            copy.playerSide = playerSide;
            return this;
        }
        public Builder enemySide(Side enemySide) {
            copy.enemySide = enemySide;
            return this;
        }

        public Gamestate build() {
            return copy;
        }
    }
}
