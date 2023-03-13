package ro.ubb.validation;
import ro.ubb.domain.Assignment;

public class AssignmentValidator implements Validator<Assignment> {
    public void validate(Assignment assignment) throws ValidationException {
        if (assignment.getID() == null || assignment.getID().equals("")) {
            throw new ValidationException("ID invalid! \n");
        }
        if (assignment.getDescription() == null || assignment.getDescription().equals("")) {
            throw new ValidationException("Descriere invalida! \n");
        }
        if (assignment.getDeadlineWeek() < 1 || assignment.getDeadlineWeek() > 14 || assignment.getDeadlineWeek() < assignment.getStartWeek()) {
            throw new ValidationException("Deadline invalid! \n");
        }
        if (assignment.getStartWeek() < 1 || assignment.getStartWeek() > 14 || assignment.getStartWeek() > assignment.getDeadlineWeek()) {
            throw new ValidationException("Data de primire invalida! \n");
        }
    }
}

