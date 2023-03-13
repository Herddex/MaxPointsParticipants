package ro.ubb.repository;

import ro.ubb.domain.HasID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.ubb.validation.ValidationException;
import ro.ubb.validation.Validator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public abstract class AbstractXMLRepository<ID, E extends HasID<ID>> extends AbstractCRUDRepository<ID, E> {
    protected String XmlFilename;

    public AbstractXMLRepository(Validator<E> validator, String XmlFilename) {
        super(validator);
        this.XmlFilename = XmlFilename;
    }

    protected abstract E getEntityFromNode(Element node);
    protected abstract Element getElementFromEntity(E entity, Document XmlDocument);

    protected void loadFromXmlFile() {
        try {
            Document XmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(XmlFilename);
            Element root = XmlDocument.getDocumentElement();
            NodeList list = root.getChildNodes();

            for(int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Element.ELEMENT_NODE) {
                    try {
                        super.save(getEntityFromNode((Element)node));
                    }
                    catch(ValidationException ve) {
                        ve.printStackTrace();
                    }
                }
            }
        }
        catch(ParserConfigurationException | SAXException | IOException exception) {
            exception.printStackTrace();
        }
    }

    protected void writeToXmlFile() {
        try {
            Document XmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = XmlDocument.createElement("Entities");
            XmlDocument.appendChild(root);

            entities.values().forEach(entity -> root.appendChild(getElementFromEntity(entity, XmlDocument)));
            Transformer XmlTransformer = TransformerFactory.newInstance().newTransformer();
            XmlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
            XmlTransformer.transform(new DOMSource(XmlDocument), new StreamResult(XmlFilename));
        } catch(ParserConfigurationException | TransformerException exception) {
            exception.printStackTrace();
        }
    }

    protected Element createElement(Document XmlDocument, String tag, String value) {
        Element element = XmlDocument.createElement(tag);
        element.setTextContent(value);
        return element;
    }

    @Override
    public E save(E entity) throws ValidationException {
        E result = super.save(entity);
        if (result == null) {
            writeToXmlFile();
        }
        return result;
    }

    @Override
    public E delete(ID id) {
        E result = super.delete(id);
        writeToXmlFile();

        return result;
    }

    @Override
    public E update(E newEntity) {
        E result = super.update(newEntity);
        writeToXmlFile();

        return result;
    }
}
