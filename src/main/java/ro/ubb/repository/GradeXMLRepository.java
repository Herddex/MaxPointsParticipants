package ro.ubb.repository;

import ro.ubb.domain.Grade;
import ro.ubb.domain.Pair;
import ro.ubb.domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.ubb.validation.StudentValidator;
import ro.ubb.validation.AssignmentValidator;
import ro.ubb.validation.Validator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GradeXMLRepository extends AbstractXMLRepository<Pair<String, String>, Grade> {

    public GradeXMLRepository(Validator<Grade> validator, String XMLfilename) {
        super(validator, XMLfilename);
        loadFromXmlFile();
    }

    protected Element getElementFromEntity(Grade grade, Document XmlDocument) {
        Element element = XmlDocument.createElement("nota");
        element.setAttribute("IDStudent", grade.getID().getObject1());
        element.setAttribute("IDTema", grade.getID().getObject2());

        element.appendChild(createElement(XmlDocument, "Nota", String.valueOf(grade.getValue())));
        element.appendChild(createElement(XmlDocument, "SaptamanaPredare", String.valueOf(grade.getDeliveryWeek())));
        element.appendChild(createElement(XmlDocument, "Feedback", grade.getFeedback()));

        return element;
    }

    protected Grade getEntityFromNode(Element node) {
        String IDStudent = node.getAttributeNode("IDStudent").getValue();
        String IDTema= node.getAttributeNode("IDTema").getValue();
        double nota = Double.parseDouble(node.getElementsByTagName("Nota").item(0).getTextContent());
        int saptamanaPredare = Integer.parseInt(node.getElementsByTagName("SaptamanaPredare").item(0).getTextContent());
        String feedback = node.getElementsByTagName("Feedback").item(0).getTextContent();

        return new Grade(new Pair(IDStudent, IDTema), nota, saptamanaPredare, feedback);
    }

    public void createFile(Grade gradeObj) {
        String idStudent = gradeObj.getID().getObject1();
        StudentValidator sval = new StudentValidator();
        AssignmentValidator tval = new AssignmentValidator();
        StudentFileRepository srepo = new StudentFileRepository(sval, "studenti.txt");
        AssignmentFileRepository trepo = new AssignmentFileRepository(tval, "teme.txt");

        Student student = srepo.findOne(idStudent);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(student.getName() + ".txt", false))) {
            super.findAll().forEach(nota -> {
                if (nota.getID().getObject1().equals(idStudent)) {
                    try {
                        bw.write("Tema: " + nota.getID().getObject2() + "\n");
                        bw.write("Nota: " + nota.getValue() + "\n");
                        bw.write("Predata in saptamana: " + nota.getDeliveryWeek() + "\n");
                        bw.write("Deadline: " + trepo.findOne(nota.getID().getObject2()).getDeadlineWeek() + "\n");
                        bw.write("Feedback: " + nota.getFeedback() + "\n\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
//    public void createFile(Nota notaObj) {
//        String idStudent = notaObj.getID().getObject1();
//        StudentValidator sval = new StudentValidator();
//        TemaValidator tval = new TemaValidator();
//        StudentXMLRepository srepo = new StudentXMLRepository(sval, "studenti.xml");
//        TemaXMLRepository trepo = new TemaXMLRepository(tval, "teme.xml");
//
//        Student student = srepo.findOne(idStudent);
//        try {
//            Document XMLdocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
//            Element root = XMLdocument.createElement("NoteStudent");
//            XMLdocument.appendChild(root);
//
//            super.findAll().forEach(nota -> {
//                if (nota.getID().getObject1().equals(idStudent)) {
//                    try {
//                        Document XMLstudent = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
//                        Element element = XMLstudent.createElement("nota");
//
//                        Document XMLdocument2 = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(trepo.XMLfilename);
//                        Node n = XMLdocument2.getFirstChild();
//                        Node temaNode = XMLstudent.importNode(XMLdocument2, true);
//                        Tema t = trepo.getEntityFromNode((Element) temaNode);
//
//                        element.appendChild(createElement(XMLstudent, "Tema", t.getID()));
//                        element.appendChild(createElement(XMLstudent, "Nota", String.valueOf(nota.getNota())));
//                        element.appendChild(createElement(XMLstudent, "SaptamanaPredare", String.valueOf(nota.getSaptamanaPredare())));
//                        element.appendChild(createElement(XMLstudent, "Deadline", String.valueOf(t.getDeadline())));
//                        element.appendChild(createElement(XMLstudent, "Feedback", nota.getFeedback()));
//
//                        root.appendChild(element);
//
//                    } catch (ParserConfigurationException e) {
//                        e.printStackTrace();
//                    } catch (SAXException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//
//            Transformer XMLtransformer = TransformerFactory.newInstance().newTransformer();
//            XMLtransformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            XMLtransformer.transform(new DOMSource(XMLdocument), new StreamResult(XMLfilename));
//        }
//        catch(ParserConfigurationException pce) {
//            pce.printStackTrace();
//        }
//        catch(TransformerConfigurationException tce) {
//            tce.printStackTrace();
//        }
//        catch(TransformerException te) {
//            te.printStackTrace();
//        }
//    }}
