import java.util.ArrayList;
import java.util.Arrays;

public class CollectionObject {
    private ArrayList<Object> list = new ArrayList<>();

    public CollectionObject(Object[] objs) {
        list.addAll(Arrays.asList(objs));
    }
}
