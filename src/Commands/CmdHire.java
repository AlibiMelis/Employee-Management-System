package src.Commands;
import src.Exceptions.ExEmployeeAlreadyExists;
import src.Exceptions.ExEmployeeNotFound;
import src.Exceptions.ExInsufficientCommand;
import src.Company;
import src.RecordedCommand;

public class CmdHire extends RecordedCommand {
    String eName;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientCommand {
        try {
            if (cmdParts.length < 2) {
                throw new ExInsufficientCommand();
            }
            Company company = Company.getInstance();

            eName = cmdParts[1];
            company.addEmployee(eName);

            addUndoCommand(this);
            clearRedoList();

            System.out.println("Done.");
        } catch (ExEmployeeAlreadyExists e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        try {
            Company company = Company.getInstance();
            company.removeEmployee(eName);
            addRedoCommand(this);
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void redoMe() {
        try {
            Company company = Company.getInstance();
            company.addEmployee(eName);
            addUndoCommand(this);
        } catch (ExEmployeeAlreadyExists e) {
            System.out.println(e.getMessage());
        }
    }
}
