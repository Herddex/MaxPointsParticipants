package ro.ubb;

import org.junit.jupiter.api.*;
import ro.ubb.domain.Assignment;
import ro.ubb.domain.Grade;
import ro.ubb.domain.Student;
import ro.ubb.repository.*;
import ro.ubb.service.Service;
import ro.ubb.validation.AssignmentValidator;
import ro.ubb.validation.GradeValidator;
import ro.ubb.validation.StudentValidator;
import ro.ubb.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;
import static ro.ubb.TestUtils.ID;
import static ro.ubb.TestUtils.clearRepo;

public class AddStudentTest
{
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
    }

    @AfterEach
    void tearDown() {
        clearRepo(studentXMLRepository);
    }

    @Test // EC # 1
    void doesAddStudentWorkProperly(){
        assertTrue(TestUtils.doesAddingStudentWork(service, ID, TestUtils.NAME, 422));
    }

    @Test // EC # 2
    void doesAddStudentValidateId(){
        assertFalse(TestUtils.doesAddingStudentWork(service, "", TestUtils.NAME, 422));
    }

    @Test // EC # 3
    void doesAddStudentValidateName(){
        assertFalse(TestUtils.doesAddingStudentWork(service, ID, "", 422));
    }

    @Test // EC # 4
    void doesAddStudentValidateGroup(){
        assertFalse(TestUtils.doesAddingStudentWork(service, ID, TestUtils.NAME, 9));
    }

    @Test // EC # 5
    void doesAddStudentValidateUniqueId(){
        assertTrue(TestUtils.doesAddingStudentWork(service, ID, TestUtils.NAME, 422));
        assertFalse(TestUtils.doesAddingStudentWork(service, ID, TestUtils.NAME, 422));
    }

    @Test // BVA # 1
    void doesAddStudentKnowGroupBoundLowerOut(){
        assertFalse(TestUtils.doesAddingStudentWork(service, ID, TestUtils.NAME, 110));
    }

    @Test // BVA # 2
    void doesAddStudentKnowGroupBoundLowerIn(){
        assertTrue(TestUtils.doesAddingStudentWork(service, ID, TestUtils.NAME, 111));
    }

    @Test // BVA # 3
    void doesAddStudentKnowGroupBoundUpperOut(){
        assertFalse(TestUtils.doesAddingStudentWork(service, ID, TestUtils.NAME, 938));
    }

    @Test // BVA # 4
    void doesAddStudentKnowGroupBoundUpperIn(){
        assertTrue(TestUtils.doesAddingStudentWork(service, ID, TestUtils.NAME, 937));
    }

}
