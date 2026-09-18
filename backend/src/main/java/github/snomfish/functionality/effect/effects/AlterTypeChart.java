package github.snomfish.functionality.effect.effects;

import java.util.List;

import github.snomfish.domain.type.TypeChart;
import github.snomfish.domain.type.TypeChartKey;
import github.snomfish.domain.type.TypeId;
import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class AlterTypeChart implements IEffect {
    

    private Object attackingTypeObject;
    private Object defendingTypeObject;
    private double multiplier;


    public AlterTypeChart(
        Object attackingTypeObject,
        Object defendingTypeObject,
        double multiplier
    ) {
        this.attackingTypeObject = attackingTypeObject;
        this.defendingTypeObject = defendingTypeObject;
        this.multiplier = multiplier;
    }


    // I should probably have a check to make sure hte objects going in are either a value -> typeId or just a TypeId
    @Override 
    public AlterTypeChart deepCopy() {
        return new AlterTypeChart(
            attackingTypeObject, 
            defendingTypeObject, 
            multiplier
        );
    }


    @Override 
    public List<Branch<BattleContext>> execute(BattleContext context) {
        TypeChart typeChart = context.user().activeLoomian().action().move().typeChart();
        TypeId attackingType = Value.resolve(attackingTypeObject, TypeId.class, context);
        TypeId defendingType = Value.resolve(defendingTypeObject, TypeId.class, context);

        typeChart.set(attackingType, defendingType, multiplier);

        return List.of(new Branch<>(context, 1.0));
    }
}
