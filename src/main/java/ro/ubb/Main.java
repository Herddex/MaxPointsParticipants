package ro.ubb;

import ro.ubb.console.*;
import ro.ubb.domain.*;
import ro.ubb.repository.*;
import ro.ubb.service.*;
import ro.ubb.validation.*;

public class Main {
    public static void main(String[] args) {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        AssignmentXMLRepository fileRepository2 = new AssignmentXMLRepository(assignmentValidator, "assignments.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        UI console = new UI(service);
        console.run();

        //PENTRU GUI
        // de avut un check: daca profesorul introduce sau nu saptamana la timp
        // daca se introduce nota la timp, se preia saptamana din sistem
        // altfel, se introduce de la tastatura
    }
}
