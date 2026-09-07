package github.snomfish.functionality.branch;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BranchUtil {


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


    // multiplies the probability by a set value across all branches
    public static <T> List<Branch<T>> mulProb(List<Branch<T>> branches, double delta) {
        for (Branch<T> branch : branches) {
            branch.multiplyProbability(delta); 
        }
        return branches;
    }
}