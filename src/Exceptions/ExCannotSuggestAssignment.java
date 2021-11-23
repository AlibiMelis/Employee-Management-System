package src.Exceptions;
public class ExCannotSuggestAssignment extends Exception {
    public ExCannotSuggestAssignment() {
        super("Project has been assigned or completed!");
    }
}
