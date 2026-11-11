package github.snomfish.functionality.event;

import static github.snomfish.functionality.branch.BranchUtil.applyProbability;

import java.util.ArrayList;
import java.util.List;

import github.snomfish.functionality.branch.Branch;
import github.snomfish.functionality.context.BattleContext;

public class Report {
    

    List<Branch<BattleContext>> trueOutcomes;
    List<Interceptor> interceptors;


    public Report(List<Branch<BattleContext>> trueOutcomes) {
        this.trueOutcomes = trueOutcomes;
    }


    // collects the interceptors which have a correct event trigger
    public void record(List<Interceptor> interceptors, Event event) { 
        for (Interceptor interceptor : interceptors) {
            if (!interceptor.events().contains(event)) continue; // if the interceptor doesn't have the correct event trigger then ignore it

            this.interceptors.add(interceptor);            
        }
    }


    public List<Branch<BattleContext>> execute(BattleContext context) {
        List<Branch<BattleContext>> outcomes = new ArrayList<>();
        double probabilityToRunTrue = 1;

        for (Interceptor interceptor : interceptors) {
            
            double probability = interceptor.condition().execute(context);
            double probabilityChunk = probabilityToRunTrue * probability;
            probabilityToRunTrue -= probabilityChunk;
            // the new probabilityToRunTrue is not the probability leftover
            // the new probabiltiyToRunFalse (1-[]) is the probability given to the new effect
            
            if (probability > 0) {
                outcomes.addAll(applyProbability(interceptor.effect().execute(context), probabilityChunk));
            }
            if (probabilityToRunTrue == 0) break; // early break incase an intercepter occurs 100% of the time
        }
        if (probabilityToRunTrue > 0) {
            outcomes.addAll(applyProbability(trueOutcomes, probabilityToRunTrue));
        }

        return null;
    }
}
