package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.context.BattleContext;

import static github.snomfish.functionality.copy.DeepCopy.deepCopyList;

public class And implements ICondition {
    

    private List<ICondition> conditions;


    public And(ICondition a, ICondition b) {
        this.conditions = List.of(a, b);
    }
    public And(ICondition a, ICondition b, ICondition c) {
        this.conditions = List.of(a, b, c);
    }


    private And(And copy) {
        this.conditions = deepCopyList(conditions);
    }
    @Override
    public And deepCopy() {
        return new And(this);
    } 


    @Override
    public double execute(BattleContext context) {
        double probability = 1;

        for (ICondition condition : conditions) {
            probability *= condition.execute(context);
        }

        return probability;
    }
}
