package src.Commands;

import src.Exceptions.ExEmployeeAlreadyInTeam;
import src.Exceptions.ExEmployeeNotFound;
import src.Exceptions.ExInsufficientCommand;
import src.Exceptions.ExTeamNotFound;
import src.Company;
import src.RecordedCommand;

public class CmdJoinTeam extends RecordedCommand {
    private String employeeName;
    private String teamName;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientCommand {
        try {
            if (cmdParts.length < 3) {
                throw new ExInsufficientCommand();
            }
            Company company = Company.getInstance();

            teamName = cmdParts[1];
            employeeName = cmdParts[2];

            company.joinTeam(teamName, employeeName);

            addUndoCommand(this);
            clearRedoList();

            System.out.println("Done.");
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeAlreadyInTeam e) {
            System.out.println(e.getMessage());
        } catch (ExTeamNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        try {
            Company company = Company.getInstance();
            company.leaveTeam(teamName, employeeName);
            addRedoCommand(this);
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExTeamNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void redoMe() {
        try {
            Company company = Company.getInstance();
            company.joinTeam(teamName, employeeName);
            addUndoCommand(this);
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeAlreadyInTeam e) {
            System.out.println(e.getMessage());
        } catch (ExTeamNotFound e) {
            System.out.println(e.getMessage());
        }
    }
}
