package github.snomfish.domain.move.structures;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

// for a move who hits multiple times with the same move
public class Barrage implements IMove {

    private MoveEffect perHit;
    private int minHits; // for now this doesnt need to be an INumber
    private int maxHits;


    public Barrage(
        MoveEffect perHit,
        int minHits,
        int maxHits
    ) {
        this.perHit = perHit;
        this.minHits = minHits;
        this.maxHits = maxHits;
    }


    @Override
    public Barrage deepCopy() {
        return new Barrage(
            perHit.deepCopy(),
            minHits,
            maxHits
        );
    }

    
    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        return null;
    }
}
