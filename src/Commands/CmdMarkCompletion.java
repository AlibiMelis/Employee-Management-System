package src.Commands;
import src.Exceptions.ExInsufficientCommand;
import src.Exceptions.ExProjectAlreadyCompleted;
import src.Exceptions.ExProjectNotAssigned;
import src.Exceptions.ExProjectNotFound;
import src.Company;
import src.RecordedCommand;

public class CmdMarkCompletion extends RecordedCommand {
    String pCode;
    
    @Override
    public void execute(String[] cmdParts) throws ExInsufficientCommand {
        try {
            if (cmdParts.length < 2) {
                throw new ExInsufficientCommand();
            }
            Company company = Company.getInstance();

            pCode = cmdParts[1];
            company.markProjectCompletion(pCode);

            addUndoCommand(this);
            clearRedoList();

            System.out.printf("Done.");
        } catch (ExProjectNotFound e) {
            System.out.print(e.getMessage());
        } catch (ExProjectNotAssigned e) {
            System.out.println(e.getMessage());
        } catch (ExProjectAlreadyCompleted e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        try {
            Company company = Company.getInstance();
            company.unmarkProjectCompletion(pCode);
            addRedoCommand(this);
        } catch (ExProjectNotFound e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void redoMe() {
        try {
            Company company = Company.getInstance();
            company.markProjectCompletion(pCode);

            addUndoCommand(this);
        } catch (ExProjectNotFound e) {
            System.out.print(e.getMessage());
        } catch (ExProjectNotAssigned e) {
            System.out.println(e.getMessage());
        } catch (ExProjectAlreadyCompleted e) {
            System.out.println(e.getMessage());
        }
    }
}
