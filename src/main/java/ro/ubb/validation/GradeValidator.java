package ro.ubb.validation;
import ro.ubb.domain.Grade;

public class GradeValidator implements Validator<Grade> {
    public void validate(Grade grade) throws ValidationException {
        if (grade.getID().getObject1() == null || grade.getID().equals("")) {
            throw new ValidationException("ID Student invalid! \n");
        }
        if (grade.getID().getObject2() == null || grade.getID().equals("")) {
            throw new ValidationException("ID Tema invalid! \n");
        }
        if (grade.getValue() < 0 || grade.getValue() > 10) {
            throw new ValidationException("Nota invalida! \n");
        }
        if (grade.getDeliveryWeek() < 0) {
            throw new ValidationException("Saptamana de predare invalida! \n");
        }
    }
}
