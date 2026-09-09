package github.snomfish.functionality.condition;

import java.util.List;

import github.snomfish.functionality.Value;
import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class ListContains implements ICondition {
      

    private Value list;
    private Value value;


    public ListContains(
        Value list,
        Value value
    ) {
        this.list = list;
        this.value = value;
    }


    // THERE IS NO DEEP COPYING HAPPENING HERE, BUGS WILL OCCUR LMAOOOOOOOOOO 
    // I COPIED THIS FROM EQUALS LMAOOOOOOOOOOO
    @Override
    public ListContains deepCopy() {
        return new ListContains(
            list,
            value
        );
    }


    @Override 
    public List<Branch<Boolean>> execute(BattleContext context) {
        
        Object listValue = list.get(context);
        Object valueValue = value.get(context);

        if (!(listValue instanceof List<?> actualList)) {
            throw new IllegalStateException(list + " does not contain a List, got: " + listValue);
        }
        
        return List.of(new Branch<>(
            actualList.contains(valueValue),
            1.0
        ));
    }
}
