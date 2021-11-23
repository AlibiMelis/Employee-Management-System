package src.Commands;
import src.Exceptions.ExInsufficientCommand;
import src.Exceptions.ExProjectNotFound;
import src.Company;
import src.Project;
import src.RecordedCommand;

public class CmdCreateProject extends RecordedCommand {
    String title;
    
    @Override
    public void execute(String[] cmdParts) throws ExInsufficientCommand {
        if (cmdParts.length < 2) {
            throw new ExInsufficientCommand();
        }
        Company company = Company.getInstance();

        title = cmdParts[1];
        Project p = company.createProject(title);

        addUndoCommand(this);
        clearRedoList();

        System.out.printf("Project created: %s", p);
    }

    @Override
    public void undoMe() {
        try {
            Company company = Company.getInstance();
            company.removeProject(title);
            addRedoCommand(this);
        } catch (ExProjectNotFound e) {
            System.out.println(e.getMessage());  
        };
    }
    @Override
    public void redoMe() {
        Company company = Company.getInstance();

        company.createProject(title);
        addUndoCommand(this);
    }
}
