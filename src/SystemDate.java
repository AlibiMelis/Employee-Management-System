package src;
import src.Exceptions.ExDateInstanceAlreadyCreated;
import src.Exceptions.ExInvalidDate;

public class SystemDate extends Day {
    private static SystemDate instance;

    private SystemDate(String sDay) throws ExInvalidDate {
        super(sDay);
    }

    public static void createInstance(String sDay) throws ExInvalidDate, ExDateInstanceAlreadyCreated {
        if (instance == null) {
            instance = new SystemDate(sDay);
        } else {
            throw new ExDateInstanceAlreadyCreated();
        }
    }

    public static SystemDate getInstance() {
        return instance;
    }
}
