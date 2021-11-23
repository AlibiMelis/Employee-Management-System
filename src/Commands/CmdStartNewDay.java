package src.Commands;
import src.Exceptions.ExDateInstanceAlreadyCreated;
import src.Exceptions.ExInsufficientCommand;
import src.Exceptions.ExInvalidDate;
import src.RecordedCommand;
import src.SystemDate;

public class CmdStartNewDay extends RecordedCommand {
    String oldDate;
    String newDate;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientCommand {
        try {
            if (cmdParts.length < 2) {
                throw new ExInsufficientCommand();
            }
            SystemDate sysDate = SystemDate.getInstance();
            newDate = cmdParts[1];
            if (sysDate == null) {
                SystemDate.createInstance(newDate);
            } else {
                oldDate = sysDate.toString();
                sysDate.set(newDate);
                addUndoCommand(this);
                clearRedoList();
                System.out.println("Done.");
            }
        } catch (ExInvalidDate e) {
            System.out.println(e.getMessage());
        } catch (ExDateInstanceAlreadyCreated e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        try {
            SystemDate sysDate = SystemDate.getInstance();
            sysDate.set(oldDate);
            addRedoCommand(this);
        } catch (ExInvalidDate e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void redoMe() {
        try {
            SystemDate sysDate = SystemDate.getInstance();
            sysDate.set(newDate);
            addUndoCommand(this);
        } catch (ExInvalidDate e) {
            System.out.println(e.getMessage());
        }
    }
}
