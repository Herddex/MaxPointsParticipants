package ro.ubb.validation;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}