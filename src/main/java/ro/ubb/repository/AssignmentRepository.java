package ro.ubb.repository;

import ro.ubb.domain.Assignment;
import ro.ubb.validation.*;

public class AssignmentRepository extends AbstractCRUDRepository<String, Assignment> {
    public AssignmentRepository(Validator<Assignment> validator) {
        super(validator);
    }
}

