package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.stats.StatId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

public class DealFormulaDamage implements IEffect {
    // deals formula damage to the target
    
    private StatId attackingStat;
    private StatId defendingStat;
    private INumber basePower;
    
 
    public DealFormulaDamage(
        StatId attackingStat,
        StatId defendingStat,
        INumber basePower
    ) {
        this.attackingStat = attackingStat;
        this.defendingStat = defendingStat;
        this.basePower = basePower;
    }


    @Override 
    public DealFormulaDamage deepCopy() {
        return new DealFormulaDamage(
            attackingStat, 
            defendingStat, 
            basePower.deepCopy()
        );
    } 


    @Override
    public List<Branch<BattleContext>> execute(BattleContext context) {
        throw new UnsupportedOperationException("not implemented");  
    }
}
