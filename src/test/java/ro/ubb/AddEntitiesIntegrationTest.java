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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static ro.ubb.TestUtils.clearGradesRepo;
import static ro.ubb.TestUtils.clearRepo;

public class AddEntitiesIntegrationTest {
    public static final String ID_STUDENT = "230740239472";
    public static final String ID_ASSIGNMENT = "235345";
    Validator<Student> studentValidator = new StudentValidator();
    Validator<Assignment> assignmentValidator = new AssignmentValidator();
    Validator<Grade> gradeValidator = new GradeValidator();

    StudentXMLRepository studentXMLRepository = new StudentXMLRepository(studentValidator, "repos/test/students_test.xml");
    AssignmentXMLRepository assignmentXMLRepository = new AssignmentXMLRepository(assignmentValidator, "repos/test/assignments_test.xml");
    GradeXMLRepository gradeXMLRepository = new GradeXMLRepository(gradeValidator, "repos/test/grades_test.xml");

    Service service = new Service(studentXMLRepository, assignmentXMLRepository, gradeXMLRepository);

    @BeforeEach
    void setUp() {
        clearRepo(studentXMLRepository);
        clearRepo(assignmentXMLRepository);
        clearGradesRepo(gradeXMLRepository);
    }

    @AfterEach
    void tearDown() {
        clearRepo(studentXMLRepository);
        clearRepo(assignmentXMLRepository);
        clearGradesRepo(gradeXMLRepository);
    }

    @Test
    void addStudentTest() {
        assertTrue(TestUtils.doesAddingStudentWork(service, TestUtils.ID, TestUtils.NAME, 934));
    }

    @Test
    void addAssignmentTest() {
        assertTrue(TestUtils.doesAddingAssignmentWork(service, TestUtils.ID, TestUtils.DESCRIPTION, 13, 1));
    }

    @Test
    void addGradeTest() {
        studentXMLRepository.save(new Student(ID_STUDENT, "Test Student", 931));
        assignmentXMLRepository.save(new Assignment(ID_ASSIGNMENT, "Test Assignment", 13, 12));
        assertTrue(TestUtils.doesAddingGradeWork(service, ID_STUDENT, ID_ASSIGNMENT, 9.5, 13, TestUtils.DESCRIPTION));
    }

    @Test
    void addEntitiesTest() {
        assertTrue(TestUtils.doesAddingStudentWork(service, ID_STUDENT, TestUtils.NAME, 934));
        assertTrue(TestUtils.doesAddingAssignmentWork(service, ID_ASSIGNMENT, TestUtils.DESCRIPTION, 13, 1));
        assertTrue(TestUtils.doesAddingGradeWork(service, ID_STUDENT, ID_ASSIGNMENT, 9.5, 1, TestUtils.DESCRIPTION));
    }
}
