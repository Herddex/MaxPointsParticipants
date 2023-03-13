package ro.ubb.repository;

import ro.ubb.domain.Student;
import ro.ubb.validation.*;

public class StudentRepository extends AbstractCRUDRepository<String, Student> {
    public StudentRepository(Validator<Student> validator) {
        super(validator);
    }
}

