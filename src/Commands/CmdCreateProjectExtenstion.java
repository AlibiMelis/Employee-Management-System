package src.Commands;

import src.Exceptions.ExInsufficientCommand;
import src.Exceptions.ExProjectNotFound;
import src.Company;
import src.Project;
import src.RecordedCommand;

public class CmdCreateProjectExtenstion extends RecordedCommand {
    String title;
    String parentProjectCode;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientCommand {
        try {
            if (cmdParts.length < 3) {
                throw new ExInsufficientCommand();
            }
            Company company = Company.getInstance();

            parentProjectCode = cmdParts[1];
            title = cmdParts[2];
            Project p = company.createProjectExtenstion(parentProjectCode, title);

            addUndoCommand(this);
            clearRedoList();

            System.out.printf("Project created: %s", p);
        } catch (ExProjectNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        try {
            Company company = Company.getInstance();
            company.removeProjectExtension(parentProjectCode, title);
            addRedoCommand(this);
        } catch (ExProjectNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void redoMe() {
        try {
            Company company = Company.getInstance();
            company.createProjectExtenstion(parentProjectCode, title);
            addUndoCommand(this);
        } catch (ExProjectNotFound e) {
            System.out.println(e.getMessage());
        }
    }
}
