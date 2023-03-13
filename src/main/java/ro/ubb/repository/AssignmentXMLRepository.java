package ro.ubb.repository;

import ro.ubb.domain.Assignment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.ubb.validation.Validator;

public class AssignmentXMLRepository extends AbstractXMLRepository<String, Assignment> {

    public AssignmentXMLRepository(Validator<Assignment> validator, String XmlFilename) {
        super(validator, XmlFilename);
        loadFromXmlFile();
    }

    protected Element getElementFromEntity(Assignment assignment, Document XmlDocument) {
        Element element = XmlDocument.createElement("tema");
        element.setAttribute("ID", assignment.getID());

        element.appendChild(createElement(XmlDocument, "Descriere", assignment.getDescription()));
        element.appendChild(createElement(XmlDocument, "Deadline", String.valueOf(assignment.getDeadlineWeek())));
        element.appendChild(createElement(XmlDocument, "Startline", String.valueOf(assignment.getStartWeek())));

        return element;
    }

    protected Assignment getEntityFromNode(Element node) {
        String ID = node.getAttributeNode("ID").getValue();
        String descriere = node.getElementsByTagName("Descriere").item(0).getTextContent();
        int deadline = Integer.parseInt(node.getElementsByTagName("Deadline").item(0).getTextContent());
        int startline = Integer.parseInt(node.getElementsByTagName("Startline").item(0).getTextContent());

        return new Assignment(ID, descriere, deadline, startline);
    }
}
