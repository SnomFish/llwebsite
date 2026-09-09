package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.stats.StatId;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;
import github.snomfish.functionality.number.INumber;

public class EnemyNormalDamage implements IEffect {
    
    
    private StatId attackingStat;
    private StatId defendingStat;
    private INumber basePower;
    
 
    public EnemyNormalDamage(
        StatId attackingStat,
        StatId defendingStat,
        INumber basePower
    ) {
        this.attackingStat = attackingStat;
        this.defendingStat = defendingStat;
        this.basePower = basePower;
    }


    @Override 
    public EnemyNormalDamage deepCopy() {
        return new EnemyNormalDamage(
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
