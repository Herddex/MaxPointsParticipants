package ro.ubb;

import org.junit.jupiter.api.*;
import ro.ubb.domain.Assignment;
import ro.ubb.domain.Grade;
import ro.ubb.domain.HasID;
import ro.ubb.domain.Student;
import ro.ubb.repository.*;
import ro.ubb.service.Service;
import ro.ubb.validation.AssignmentValidator;
import ro.ubb.validation.GradeValidator;
import ro.ubb.validation.StudentValidator;
import ro.ubb.validation.Validator;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

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

    private void clearRepo(AbstractXMLRepository<String, ? extends HasID<String>> fileRepo){
        if(!fileRepo.findAll().iterator().hasNext()){
            return;
        }
        for (HasID<String> item : fileRepo.findAll()) {
            fileRepo.delete(item.getID());
        }
    }

    @Test // EC # 1
    void doesAddStudentWorkProperly(){
        assertTrue(doesAddingStudentWork("1", "Numele", 422));
    }

    @Test // EC # 2
    void doesAddStudentValidateId(){
        assertFalse(doesAddingStudentWork("", "Numele", 422));
    }

    @Test // EC # 3
    void doesAddStudentValidateName(){
        assertFalse(doesAddingStudentWork("1", "", 422));
    }

    @Test // EC # 4
    void doesAddStudentValidateGroup(){
        assertFalse(doesAddingStudentWork("1", "Numele", 9));
    }

    @Test // EC # 5
    void doesAddStudentValidateUniqueId(){
        assertTrue(doesAddingStudentWork("1", "Numele", 422));
        assertFalse(doesAddingStudentWork("1", "Numelele", 422));
    }

    @Test // BVA # 1
    void doesAddStudentKnowGroupBoundLowerOut(){
        assertFalse(doesAddingStudentWork("1", "Numele", 110));
    }

    @Test // BVA # 2
    void doesAddStudentKnowGroupBoundLowerIn(){
        assertTrue(doesAddingStudentWork("1", "Numele", 111));
    }

    @Test // BVA # 3
    void doesAddStudentKnowGroupBoundUpperOut(){
        assertFalse(doesAddingStudentWork("1", "Numele", 938));
    }

    @Test // BVA # 4
    void doesAddStudentKnowGroupBoundUpperIn(){
        assertTrue(doesAddingStudentWork("1", "Numele", 937));
    }

    private int getIteratorCnt(Iterator<?> iterator){
        int count = 0;
        while(iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    private boolean doesAddingStudentWork(String id, String name, int group){
        Iterator<Student> prev_students = service.findAllStudents().iterator();
        int prev_count = getIteratorCnt(prev_students);

        int isSuccessful = service.saveStudent(id, name, group);
        if(isSuccessful != 1){
            return false;
        }

        Iterator<Student> students = service.findAllStudents().iterator();
        int count = getIteratorCnt(students);
        return count == prev_count + 1;
    }
}
