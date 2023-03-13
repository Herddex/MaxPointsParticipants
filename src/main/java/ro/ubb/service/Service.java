package ro.ubb.service;

import ro.ubb.domain.*;
import ro.ubb.repository.*;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Service {
    private final StudentXMLRepository studentXmlRepo;
    private final AssignmentXMLRepository assignmentXmlRepo;
    private final GradeXMLRepository gradeXmlRepo;

    public Service(StudentXMLRepository studentXmlRepo, AssignmentXMLRepository assignmentXmlRepo, GradeXMLRepository gradeXmlRepo) {
        this.studentXmlRepo = studentXmlRepo;
        this.assignmentXmlRepo = assignmentXmlRepo;
        this.gradeXmlRepo = gradeXmlRepo;
    }

    public Iterable<Student> findAllStudents() { return studentXmlRepo.findAll(); }

    public Iterable<Assignment> findAllAssignments() { return assignmentXmlRepo.findAll(); }

    public Iterable<Grade> findAllGrades() { return gradeXmlRepo.findAll(); }

    public int saveStudent(String id, String name, int group) {
        Student student = new Student(id, name, group);
        Student result = studentXmlRepo.save(student);

        if (result == null) {
            return 1;
        }
        return 0;
    }

    public int saveAssignment(String id, String description, int deadline, int startWeek) {
        Assignment assignment = new Assignment(id, description, deadline, startWeek);
        Assignment result = assignmentXmlRepo.save(assignment);

        if (result == null) {
            return 1;
        }
        return 0;
    }

    public int saveGrade(String idStudent, String idAssignment, double gradeVal, int deliveryWeek, String feedback) {
        if (studentXmlRepo.findOne(idStudent) == null || assignmentXmlRepo.findOne(idAssignment) == null) {
            return -1;
        }
        else {
            int deadline = assignmentXmlRepo.findOne(idAssignment).getDeadlineWeek();

            if (deliveryWeek - deadline > 2) {
                gradeVal =  1;
            } else if (deliveryWeek > deadline) {
                gradeVal =  gradeVal - 2.5 * (deliveryWeek - deadline);
            }
            Grade grade = new Grade(new Pair<>(idStudent, idAssignment), gradeVal, deliveryWeek, feedback);
            Grade result = gradeXmlRepo.save(grade);

            if (result == null) {
                return 1;
            }
            return 0;
        }
    }

    public int deleteStudent(String id) {
        Student result = studentXmlRepo.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int deleteAssignment(String id) {
        Assignment result = assignmentXmlRepo.delete(id);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int updateStudent(String id, String numeNou, int grupaNoua) {
        Student studentNou = new Student(id, numeNou, grupaNoua);
        Student result = studentXmlRepo.update(studentNou);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int updateAssignment(String id, String newDescription, int newDeadline, int newStartWeek) {
        Assignment newAssignment = new Assignment(id, newDescription, newDeadline, newStartWeek);
        Assignment result = assignmentXmlRepo.update(newAssignment);

        if (result == null) {
            return 0;
        }
        return 1;
    }

    public int extendDeadline(String id, int numberOfWeeks) {
        Assignment assignment = assignmentXmlRepo.findOne(id);

        if (assignment != null) {
            LocalDate date = LocalDate.now();
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int currentWeek = date.get(weekFields.weekOfWeekBasedYear());

            if (currentWeek >= 39) {
                currentWeek = currentWeek - 39;
            } else {
                currentWeek = currentWeek + 12;
            }

            if (currentWeek <= assignment.getDeadlineWeek()) {
                int deadlineNou = assignment.getDeadlineWeek() + numberOfWeeks;
                return updateAssignment(assignment.getID(), assignment.getDescription(), deadlineNou, assignment.getStartWeek());
            }
        }
        return 0;
    }

    public void createStudentFile(String idStudent, String idAssignment) {
        Grade grade = gradeXmlRepo.findOne(new Pair<>(idStudent, idAssignment));

        gradeXmlRepo.createFile(grade);
    }
}
