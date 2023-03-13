package ro.ubb.repository;

import ro.ubb.domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.ubb.validation.Validator;

public class StudentXMLRepository extends AbstractXMLRepository<String, Student> {

    public StudentXMLRepository(Validator<Student> validator, String XmlFilename) {
        super(validator, XmlFilename);
        loadFromXmlFile();
    }

    protected Element getElementFromEntity(Student student, Document XmlDocument) {
        Element element = XmlDocument.createElement("student");
        element.setAttribute("ID", student.getID());

        element.appendChild(createElement(XmlDocument, "Nume", student.getName()));
        element.appendChild(createElement(XmlDocument, "Grupa", String.valueOf(student.getGroup())));

        return element;
    }

    protected Student getEntityFromNode(Element node) {
        String ID = node.getAttributeNode("ID").getValue();
        String nume = node.getElementsByTagName("Nume").item(0).getTextContent();
        int grupa = Integer.parseInt(node.getElementsByTagName("Grupa").item(0).getTextContent());

        return new Student(ID, nume, grupa);
    }
}
