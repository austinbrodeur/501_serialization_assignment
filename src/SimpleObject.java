import org.w3c.dom.*;
import java.util.HashMap;

public class SimpleObject {
    private int a;
    private boolean b;

    public SimpleObject(int a, boolean b) {
        this.a = a;
        this.b = b;
    }

    public Node toElements(Document document, int id) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put(this.toString(), String.valueOf(id));

        Element object_element = document.createElement("object");
        object_element.setAttribute("class", this.getClass().getName());
        object_element.setAttribute("id", hm.get(this.toString()));

        Element a_field_element = document.createElement("field"); // Fields names are hardcoded as their names don't change
        a_field_element.setAttribute("name", "a");
        a_field_element.setAttribute("declaringclass", "SimpleObject");

        Element a_value_element = document.createElement("value");
        a_value_element.setTextContent(String.valueOf(a));

        Element b_field_element = document.createElement("field");
        b_field_element.setAttribute("name", "b");
        b_field_element.setAttribute("declaringclass", "SimpleObject");

        Element b_value_element = document.createElement("value");
        b_value_element.setTextContent(String.valueOf(b));

        a_field_element.appendChild(a_value_element);
        b_field_element.appendChild(b_value_element);
        object_element.appendChild(a_field_element);
        object_element.appendChild(b_field_element);

        return object_element;
    }
}
