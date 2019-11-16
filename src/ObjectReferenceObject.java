import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;

public class ObjectReferenceObject {
    private Object referenceObj = null;

    public ObjectReferenceObject(Object o) {
        referenceObj = o;
    }


    public ObjectReferenceObject() { }


    public Node toElements(Document document, int id) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put(this.toString(), String.valueOf(id));
        hm.put(referenceObj.toString(), String.valueOf(id+1));

        Element object_element = document.createElement("object");
        object_element.setAttribute("class", this.getClass().getName());
        object_element.setAttribute("id", hm.get(this.toString()));

        Element field_element = document.createElement("field");
        field_element.setAttribute("name", "referenceObj");
        field_element.setAttribute("declaringclass", "ObjectReferenceObject");

        Element reference_element = document.createElement("reference");
        reference_element.setTextContent(hm.get(referenceObj.toString()));

        Element reference_object_element = null;
        if (referenceObj instanceof SimpleObject) {
            SimpleObject sObj = (SimpleObject) referenceObj;
            reference_object_element = (Element) sObj.toElements(document, Integer.parseInt(hm.get(referenceObj.toString())));
        }
        else if (referenceObj instanceof ArrayOfPrimsObject) {
            ArrayOfPrimsObject aObj = (ArrayOfPrimsObject) referenceObj;
            reference_object_element = (Element) aObj.toElements(document, Integer.parseInt(hm.get(referenceObj.toString())));
        }
        else if (referenceObj instanceof ObjectReferenceObject) {
            ObjectReferenceObject oObj = (ObjectReferenceObject) referenceObj;
            reference_object_element = (Element) oObj.toElements(document, Integer.parseInt(hm.get(referenceObj.toString())));
        }

        field_element.appendChild(reference_element);
        field_element.appendChild(reference_object_element);
        object_element.appendChild(field_element);

        return object_element;
    }
}
