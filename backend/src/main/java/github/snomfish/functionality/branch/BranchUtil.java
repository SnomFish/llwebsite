package github.snomfish.functionality.branch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BranchUtil {


    private BranchUtil() {}


    // takes in a list of Branches and a function that creates more branches and applies that function to each original branch, 
    // then packs all the results into one list
    public static <A, B> List<Branch<B>> flatMap(
        List<Branch<A>> branches,
        Function<A, List<Branch<B>>> function
    ) {
        List<Branch<B>> result = new ArrayList<>();
        for (Branch<A> branch : branches) {

            List<Branch<B>> newBranches = function.apply(branch.value());   
            for (Branch<B> newBranch : newBranches) {
                result.add(new Branch<B>(
                    newBranch.value(), 
                    branch.probability() * newBranch.probability()
                ));
            }
        }
        return result;
    }


    // much better, mutation but it is safe
    public static <A, B> List<Branch<A>> applyProbability(List<Branch<A>> branches, Branch<B> pBranch) {
        for (Branch<A> branch : branches) {
            branch.multiplyProbability(pBranch.probability());
        }
        return branches;
    }


    // this does not work for complicated objects
    // for now .......
    // will probably need another interface for comparable
    public static List<Branch<Boolean>> optimiseConditionalBranches(List<Branch<Boolean>> branches) {
        double trueProbability = 0.0;
        double falseProbability = 0.0;
        
        for (Branch<Boolean> branch : branches) {
            if (branch.value()) {
                trueProbability += branch.probability();
            } else {
                falseProbability += branch.probability();
            }
        }
        List<Branch<Boolean>> result = new ArrayList<>(2);
        
        if (trueProbability > 0.0) result.add(new Branch<>(true, trueProbability));
        if (falseProbability > 0.0) result.add(new Branch<>(false, falseProbability));

        return result;
    }
}