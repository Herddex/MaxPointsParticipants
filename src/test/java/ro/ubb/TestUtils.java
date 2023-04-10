package ro.ubb;

import ro.ubb.domain.*;
import ro.ubb.repository.AbstractXMLRepository;
import ro.ubb.service.Service;

import java.util.Iterator;

public class TestUtils {
    public static final String DESCRIPTION = "Descriere";
    public static final String ID = "1";
    public static final String NAME = "Name";

    public static void clearRepo(AbstractXMLRepository<String, ? extends HasID<String>> fileRepo) {
        for (HasID<String> item : fileRepo.findAll()) {
            fileRepo.delete(item.getID());
        }
    }

    public static void clearGradesRepo(AbstractXMLRepository<Pair<String, String>, ? extends HasID<Pair<String, String>>> fileRepo) {
        for (HasID<Pair<String, String>> item : fileRepo.findAll()) {
            fileRepo.delete(item.getID());
        }
    }

    public static int getIteratorCnt(Iterator<?> iterator){
        int count = 0;
        while(iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }


    public static boolean doesAddingAssignmentWork(Service service, String id, String description, int deadline, int startWeek) {
        Iterator<Assignment> prev_assignments = service.findAllAssignments().iterator();
        int prev_count = getIteratorCnt(prev_assignments);

        int isSuccessful = service.saveAssignment(id, description, deadline, startWeek);
        if(isSuccessful != 1) {
            return false;
        }

        Iterator<Assignment> assignmentIterator = service.findAllAssignments().iterator();
        int count = getIteratorCnt(assignmentIterator);
        return count == prev_count + 1;
    }

    public static boolean doesAddingStudentWork(Service service, String id, String name, int group){
        Iterator<Student> prev_students = service.findAllStudents().iterator();
        int prev_count = getIteratorCnt(prev_students);

        int isSuccessful = service.saveStudent(id, name, group);
        if(isSuccessful != 1){
            return false;
        }

        Iterator<Student> studentIterator = service.findAllStudents().iterator();
        int count = getIteratorCnt(studentIterator);
        return count == prev_count + 1;
    }


    public static boolean doesAddingGradeWork(Service service, String idStudent, String idAssignment,
                                              double value, int deliveryWeek, String feedback) {
        Iterator<Grade> prev_grades = service.findAllGrades().iterator();
        int prev_count = getIteratorCnt(prev_grades);

        int isSuccessful = service.saveGrade(idStudent, idAssignment, value, deliveryWeek, feedback);
        if(isSuccessful != 1) {
            return false;
        }

        Iterator<Grade> gradeIterator = service.findAllGrades().iterator();
        int count = getIteratorCnt(gradeIterator);
        return count == prev_count + 1;
    }
}
