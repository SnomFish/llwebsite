package github.snomfish.functionality.event;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.branch.BranchUtil;
import github.snomfish.functionality.context.BattleContext;
import github.snomfish.functionality.effect.IEffect;

public class InterceptorReport {
    

    private BattleContext context;
    private IEffect trueEffect;
    private List<Interceptor> interceptors;


    public InterceptorReport(
        BattleContext context,
        IEffect trueEffect
    ) {
        this.context = context;
        this.trueEffect = trueEffect;
    }


    public void record(List<Interceptor> interceptors, Event<InterceptorId> event) {
        for (Interceptor interceptor : interceptors) {
            if (interceptor.events().contains(event)) {
                
                this.interceptors.add(interceptor);
                
            }
        }
    }


    public List<Branch<BattleContext>> execute() {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        double falseProbability = 1.0;

        for (Interceptor interceptor : interceptors) {
            
            double newFalseProbability = falseProbability;
            for (Branch<Boolean> conditionBranch : interceptor.condition().execute(context)) {
                
                if (!conditionBranch.value()) { // if the condition is false then execute the false condition
                    List<Branch<BattleContext>> newOutcomes = interceptor.effect().execute(context);
                    BranchUtil.applyProbability(newOutcomes, conditionBranch);
                    outcomes.addAll(newOutcomes);

                    newFalseProbability *= conditionBranch.probability();
                }
            }
            falseProbability = newFalseProbability;
        }
        if (falseProbability < 1) {
            List<Branch<BattleContext>> newOutcomes = trueEffect.execute(context);
            BranchUtil.applyProbability(newOutcomes, new Branch<>(context, falseProbability));
            outcomes.addAll(newOutcomes);
        }

        return outcomes;
    }
}
