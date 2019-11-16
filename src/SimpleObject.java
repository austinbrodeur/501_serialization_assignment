import org.w3c.dom.*;
import java.util.HashMap;

public class SimpleObject {
    private int a;
    private String b;
    private boolean c;

    public SimpleObject(int a, String b, boolean c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Node toElements(Document document) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put(this.toString(), "0");

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
        b_value_element.setTextContent(b);

        Element c_field_element = document.createElement("field");
        c_field_element.setAttribute("name", "c");
        c_field_element.setAttribute("declaringclass", "SimpleObject");

        Element c_value_element = document.createElement("value");
        c_value_element.setTextContent(String.valueOf(c));

        a_field_element.appendChild(a_value_element);
        b_field_element.appendChild(b_value_element);
        c_field_element.appendChild(c_value_element);
        object_element.appendChild(a_field_element);
        object_element.appendChild(b_field_element);
        object_element.appendChild(c_field_element);

        return object_element;
    }
}
