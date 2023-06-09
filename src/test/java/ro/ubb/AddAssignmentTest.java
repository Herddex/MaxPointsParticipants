package ro.ubb;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ro.ubb.TestUtils.clearRepo;

public class AddAssignmentTest {
    Validator<Student> studentValidator = new StudentValidator();
    Validator<Assignment> assignmentValidator = new AssignmentValidator();
    Validator<Grade> gradeValidator = new GradeValidator();

    StudentXMLRepository studentXMLRepository = new StudentXMLRepository(studentValidator, "repos/test/students_test.xml");
    AssignmentXMLRepository assignmentXMLRepository = new AssignmentXMLRepository(assignmentValidator, "repos/test/assignments_test.xml");
    GradeXMLRepository gradeXMLRepository = new GradeXMLRepository(gradeValidator, "repos/test/grades_test.xml");

    Service service = new Service(studentXMLRepository, assignmentXMLRepository, gradeXMLRepository);

    @BeforeEach
    void setUp() {
        clearRepo(assignmentXMLRepository);
    }

    @AfterEach
    void tearDown() {
        clearRepo(assignmentXMLRepository);
    }

    @Test // S2 True coverage
    void doesAddAssignmentReturnOnSuccess(){
        assertTrue(TestUtils.doesAddingAssignmentWork(service, TestUtils.ID, TestUtils.DESCRIPTION, 13, 1));
    }

    @Test // S2 False coverage
    void doesAddAssignmentReturnOnInvalid(){
        assertFalse(TestUtils.doesAddingAssignmentWork(service, "", TestUtils.DESCRIPTION, 1, 13));
        assertFalse(TestUtils.doesAddingAssignmentWork(service, TestUtils.ID, "", 1, 13));
    }

}
