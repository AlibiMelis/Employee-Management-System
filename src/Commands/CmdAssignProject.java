package src.Commands;
import java.util.Arrays;

import src.Company;
import src.RecordedCommand;
import src.Exceptions.ExEmployeeNotFound;
import src.Exceptions.ExInsufficientCommand;
import src.Exceptions.ExProjectAlreadyAssigned;
import src.Exceptions.ExProjectNotFound;
import src.Exceptions.ExTeamNotFound;

public class CmdAssignProject extends RecordedCommand {
    String pCode;
    String tName;
    String[] supportStaff;
    
    @Override
    public void execute(String[] cmdParts) throws ExInsufficientCommand {
        try {
            if (cmdParts.length < 3) {
                throw new ExInsufficientCommand();
            }
            Company company = Company.getInstance();

            pCode = cmdParts[1];
            tName = cmdParts[2];
            supportStaff = Arrays.copyOfRange(cmdParts, 3, cmdParts.length);

            company.assignProject(pCode, tName, supportStaff);

            addUndoCommand(this);
            clearRedoList();

            System.out.printf("Done.");
        } catch (ExProjectNotFound e) {
            System.out.print(e.getMessage());
        } catch (ExProjectAlreadyAssigned e) {
            System.out.println(e.getMessage());
        } catch (ExTeamNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        try {
            Company company = Company.getInstance();
            company.unassignProject(pCode);
            addRedoCommand(this);
        } catch (ExProjectNotFound e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void redoMe() {
        try {
            Company company = Company.getInstance();
            company.assignProject(pCode, tName, supportStaff);

            addUndoCommand(this);
        } catch (ExProjectNotFound e) {
            System.out.print(e.getMessage());
        } catch (ExProjectAlreadyAssigned e) {
            System.out.println(e.getMessage());
        } catch (ExTeamNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        }
    }
}
