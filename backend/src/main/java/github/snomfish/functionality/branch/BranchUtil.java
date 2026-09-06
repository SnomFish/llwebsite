package github.snomfish.functionality.branch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

            List<Branch<B>> newBranches = function.apply(branch.getValue());   
            for (Branch<B> newBranch : newBranches) {
                result.add(new Branch<B>(
                    newBranch.getValue(), 
                    branch.getProbability() * newBranch.getProbability()
                ));
            }
        }
        return result;
    }


    public static <T> List<Branch<T>> compact(List<Branch<T>> branches) {
        Map<T, Double> map = new HashMap<>();
        for (Branch<T> branch : branches) {
            T value = branch.getValue();
            Double probability = branch.getProbability();
            probability += map.get(value) != null ? map.get(value) : 0;
            map.put(value, probability);
        }
        List<Branch<T>> newBranches = new ArrayList<>();
        for (T key : map.keySet()) {
            Double probability = map.get(key);
            newBranches.add(new Branch<T>(key, probability));
        }
        return newBranches;
    }
}