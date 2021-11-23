package src.Exceptions;
public class ExInvalidDate extends Exception {
    public ExInvalidDate() {
        super("The date is invalid!");
    }
    public ExInvalidDate(String date) {
        super("The date " + date + " is invalid");
    }
}
