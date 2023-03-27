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

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ro.ubb.TestUtils.clearRepo;
import static ro.ubb.TestUtils.getIteratorCnt;

public class AddAssignmentTest {
    public static final String ID = "1";
    public static final String DESCRIPTION = "Descriere";

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

    @Test // EC # 1
    void doesAddAssignmentReturnOnSuccess(){
        assertTrue(doesAddingAssignmentWork(ID, DESCRIPTION,1, 13));
    }

    @Test // EC # 2
    void doesAddAssignmentReturnOnInvalid(){
        assertFalse(doesAddingAssignmentWork("", DESCRIPTION, 1, 13));
    }

    private boolean doesAddingAssignmentWork(String id, String description, int deadline, int startWeek) {
        Iterator<Assignment> prev_assignments = service.findAllAssignments().iterator();
        int prev_count = getIteratorCnt(prev_assignments);

        int isSuccessful = service.saveAssignment(id, description, deadline, startWeek);
        if(isSuccessful != 1) {
            return false;
        }

        Iterator<Student> students = service.findAllStudents().iterator();
        int count = getIteratorCnt(students);
        return count == prev_count + 1;
    }
}
