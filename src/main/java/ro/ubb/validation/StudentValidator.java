package ro.ubb.validation;
import ro.ubb.domain.Student;

public class StudentValidator implements Validator<Student> {
    public void validate(Student student) throws ValidationException {
        if (student.getID() == null || student.getID().equals("")) {
            throw new ValidationException("Invalid ID\n");
        }
        if (student.getName() == null || student.getName().equals("")) {
            throw new ValidationException("Invalid name \n");
        }
        if (student.getGroup() <= 110 || student.getGroup() >= 938) {
            throw new ValidationException("Invalid group \n");
        }
    }
}

