package src;
public class PStatus_Completed implements PStatus {
    private String status;
    private static PStatus_Completed instance = new PStatus_Completed();

    private PStatus_Completed() {
        status = "Completed";
    }
    
    public static PStatus_Completed getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return status;
    }
}