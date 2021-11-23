package src.Commands;

import src.Exceptions.ExEmployeeAlreadyInTeam;
import src.Exceptions.ExEmployeeNotFound;
import src.Exceptions.ExInsufficientCommand;
import src.Exceptions.ExTeamAlreadyExists;
import src.Exceptions.ExTeamNotFound;
import src.Company;
import src.RecordedCommand;

public class CmdSetupTeam extends RecordedCommand {
    String tName;
    String eName;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientCommand {
        try {
            if (cmdParts.length < 3) {
                throw new ExInsufficientCommand();
            }
            Company company = Company.getInstance();

            tName = cmdParts[1];
            eName = cmdParts[2];
            company.setupTeam(tName, eName);

            addUndoCommand(this);
            clearRedoList();

            System.out.println("Done.");
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExTeamAlreadyExists e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeAlreadyInTeam e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        try {
            Company company = Company.getInstance();
            company.disbandTeam(tName);
            
            addRedoCommand(this);
        } catch (ExTeamNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void redoMe() {
        try {
            Company company = Company.getInstance();
            company.setupTeam(tName, eName);

            addUndoCommand(this);
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExTeamAlreadyExists e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeAlreadyInTeam e) {
            System.out.println(e.getMessage());
        }
    }
}
