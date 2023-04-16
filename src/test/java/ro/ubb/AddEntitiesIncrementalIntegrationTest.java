package ro.ubb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.ubb.domain.Assignment;
import ro.ubb.domain.Grade;
import ro.ubb.domain.Pair;
import ro.ubb.domain.Student;
import ro.ubb.repository.AssignmentXMLRepository;
import ro.ubb.repository.GradeXMLRepository;
import ro.ubb.repository.StudentXMLRepository;
import ro.ubb.service.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddEntitiesIncrementalIntegrationTest {
    private static final String STUDENT_ID = "230740239472";
    private static final String STUDENT_NAME = "Name";
    private static final int STUDENT_GROUP = 934;
    private static final String ASSIGNMENT_ID = "235345";
    private static final String ASSIGNMENT_DESCRIPTION = "Description";
    private static final int ASSIGNMENT_DEADLINE = 9;
    private static final int ASSIGNMENT_START_WEEK = 8;
    private static final double GRADE_VALUE = 9.6;
    private static final String FEEDBACK = "Feedback";

    @Mock
    StudentXMLRepository studentXMLRepository;
    @Mock
    AssignmentXMLRepository assignmentXMLRepository;
    @Mock
    GradeXMLRepository gradeXMLRepository;

    @InjectMocks
    Service service;

    @Test
    void addStudentTestIncremental() {
        when(studentXMLRepository.save(new Student(STUDENT_ID, STUDENT_NAME, STUDENT_GROUP))).thenReturn(null);
        assertEquals(1, service.saveStudent(STUDENT_ID, STUDENT_NAME, STUDENT_GROUP));
    }

    @Test
    void addAssignmentTestIncremental() {
        when(studentXMLRepository.save(new Student(STUDENT_ID, STUDENT_NAME, STUDENT_GROUP))).thenReturn(null);
        assertEquals(1, service.saveStudent(STUDENT_ID, STUDENT_NAME, STUDENT_GROUP));

        when(assignmentXMLRepository.save(
                new Assignment(ASSIGNMENT_ID, ASSIGNMENT_DESCRIPTION, ASSIGNMENT_DEADLINE, ASSIGNMENT_START_WEEK)))
                .thenReturn(null);
        assertEquals(1, service.saveAssignment(
                ASSIGNMENT_ID, ASSIGNMENT_DESCRIPTION, ASSIGNMENT_DEADLINE, ASSIGNMENT_START_WEEK));
    }

    @Test
    void addGradeTestIncremental() {
        Student student = new Student(STUDENT_ID, STUDENT_NAME, STUDENT_GROUP);
        when(studentXMLRepository.save(student)).thenReturn(null);
        assertEquals(1, service.saveStudent(STUDENT_ID, STUDENT_NAME, STUDENT_GROUP));

        Assignment assignment =
                new Assignment(ASSIGNMENT_ID, ASSIGNMENT_DESCRIPTION, ASSIGNMENT_DEADLINE, ASSIGNMENT_START_WEEK);
        when(assignmentXMLRepository.save(assignment)).thenReturn(null);
        assertEquals(1, service.saveAssignment(
                ASSIGNMENT_ID, ASSIGNMENT_DESCRIPTION, ASSIGNMENT_DEADLINE, ASSIGNMENT_START_WEEK));

        when(studentXMLRepository.findOne(STUDENT_ID)).thenReturn(student);
        when(assignmentXMLRepository.findOne(ASSIGNMENT_ID)).thenReturn(assignment);
        when(gradeXMLRepository.save(
                new Grade(new Pair<>(STUDENT_ID, ASSIGNMENT_ID), GRADE_VALUE, ASSIGNMENT_DEADLINE, FEEDBACK)))
                .thenReturn(null);

        assertEquals(1,
                service.saveGrade(STUDENT_ID, ASSIGNMENT_ID, GRADE_VALUE, ASSIGNMENT_DEADLINE, FEEDBACK));
    }
}
