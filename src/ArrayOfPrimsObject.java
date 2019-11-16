import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;

public class ArrayOfPrimsObject {
    private int[] intArray;

    public ArrayOfPrimsObject(int[] sArr) { intArray = sArr; }

    public Node toElements(Document document, int id) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put(this.toString(), String.valueOf(id));
        hm.put(intArray.toString(), String.valueOf(id+1));

        Element object_element = document.createElement("object");
        object_element.setAttribute("class", this.getClass().getName());
        object_element.setAttribute("id", hm.get(this.toString()));

        Element field_element = document.createElement("field");
        field_element.setAttribute("name", "stringArray");
        field_element.setAttribute("declaringclass", "ArrayOfPrimsObject");

        Element reference_element = document.createElement("reference");
        reference_element.setTextContent(hm.get(intArray.toString()));

        Element array_object_element = document.createElement("object");
        array_object_element.setAttribute("class", intArray.getClass().getName());
        array_object_element.setAttribute("id", hm.get(intArray.toString()));
        array_object_element.setAttribute("length", String.valueOf(intArray.length));

        for (int i : intArray) {
            Element array_value_object = document.createElement("value");
            array_value_object.setTextContent(String.valueOf(i));
            array_object_element.appendChild(array_value_object);
        }

        object_element.appendChild(reference_element);
        object_element.appendChild(array_object_element);

        return object_element;
    }
}
