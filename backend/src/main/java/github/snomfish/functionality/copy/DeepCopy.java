package github.snomfish.functionality.copy;

import java.util.ArrayList;
import java.util.List;

public class DeepCopy {
    

    public static <T extends DeepCopyable<T>> List<T> deepCopyList(List<T> list) {
        List<T> newList = new ArrayList<>();
        for (T entry : list) {
            newList.add(entry.deepCopy());
        }
        return newList;
    }
}
