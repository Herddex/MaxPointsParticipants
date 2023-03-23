package ro.ubb;

import org.junit.jupiter.api.Test;
import ro.ubb.domain.Assignment;
import ro.ubb.domain.Grade;
import ro.ubb.domain.Student;
import ro.ubb.repository.AssignmentXMLRepository;
import ro.ubb.repository.GradeXMLRepository;
import ro.ubb.repository.StudentXMLRepository;
import ro.ubb.service.Service;
import ro.ubb.validation.AssignmentValidator;
import ro.ubb.validation.GradeValidator;
import ro.ubb.validation.StudentValidator;
import ro.ubb.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

public class AddStudentTest
{

    Validator<Student> studentValidator = new StudentValidator();
    Validator<Assignment> assignmentValidator = new AssignmentValidator();
    Validator<Grade> gradeValidator = new GradeValidator();

    StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students_test.xml");
    AssignmentXMLRepository fileRepository2 = new AssignmentXMLRepository(assignmentValidator, "assignments_test.xml");
    GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades_test.xml");

    Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

    @Test
    void doesAddStudentWork(){
        int isSuccessful = service.saveStudent("1", "Name", 422);
        assertEquals(0, isSuccessful);
    }

    @Test
    void doesAddStudentWorkProperly(){
        service.saveStudent("1", "Name", 422);
        Iterable<Student> students = service.findAllStudents();
        Student student = students.iterator().next();

        assertEquals(student.getID(), "1");
        assertEquals(student.getName(), "Name");
        assertEquals(student.getGroup(), 422);
    }
}
