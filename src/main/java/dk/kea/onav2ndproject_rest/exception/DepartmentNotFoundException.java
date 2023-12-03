package dk.kea.onav2ndproject_rest.exception;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(String s) {
        super(s);
    }
}
