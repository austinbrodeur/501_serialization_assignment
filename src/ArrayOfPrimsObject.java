import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;

public class ArrayOfPrimsObject {
    private String[] stringArray;

    public ArrayOfPrimsObject(String[] sArr) { stringArray = sArr; }

    public Node toElements(Document document) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put(this.toString(), "0");
        hm.put(stringArray.toString(), "1");

        Element object_element = document.createElement("object");
        object_element.setAttribute("class", this.getClass().getName());
        object_element.setAttribute("id", hm.get(this.toString()));

        Element field_element = document.createElement("field");
        field_element.setAttribute("name", "stringArray");
        field_element.setAttribute("declaringclass", "ArrayOfPrimsObject");

        Element reference_element = document.createElement("reference");
        reference_element.setTextContent(hm.get(stringArray.toString()));

        Element array_object_element = document.createElement("object");
        array_object_element.setAttribute("class", stringArray.getClass().getName());
        array_object_element.setAttribute("id", hm.get(stringArray.toString()));
        array_object_element.setAttribute("length", String.valueOf(stringArray.length));

        for (String s : stringArray) {
            Element array_value_object = document.createElement("value");
            array_value_object.setTextContent(s);
            array_object_element.appendChild(array_value_object);
        }

        object_element.appendChild(reference_element);
        object_element.appendChild(array_object_element);

        return object_element;
    }
}
