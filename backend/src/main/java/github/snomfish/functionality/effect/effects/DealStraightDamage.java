package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

public class DealStraightDamage implements IEffect {
    

    private INumber idamage;


    public DealStraightDamage(INumber idamage) {
        this.idamage = idamage;
    }


    @Override 
    public DealStraightDamage deepCopy() {
        return new DealStraightDamage(
            idamage.deepCopy()
        );
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        throw new RuntimeException("not fully implemented");
    }
}
