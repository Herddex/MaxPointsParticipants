package ro.ubb.repository;

import ro.ubb.domain.*;
import ro.ubb.validation.*;

public class GradeRepository extends AbstractCRUDRepository<Pair<String, String>, Grade> {
    public GradeRepository(Validator<Grade> validator) {
        super(validator);
    }
}
