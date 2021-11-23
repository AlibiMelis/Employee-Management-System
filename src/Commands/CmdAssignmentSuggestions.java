package src.Commands;

import src.Exceptions.ExCannotSuggestAssignment;
import src.Exceptions.ExInsufficientCommand;
import src.Exceptions.ExProjectNotFound;
import src.Command;
import src.Company;

public class CmdAssignmentSuggestions implements Command {
    @Override
    public void execute(String[] cmdParts) throws ExInsufficientCommand {
        try {
            if (cmdParts.length < 2) {
                throw new ExInsufficientCommand();
            }
            String projectCode = cmdParts[1];
            Company company = Company.getInstance();
            company.giveAssignmentSuggestions(projectCode);
        } catch (ExProjectNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExCannotSuggestAssignment e) {
            System.out.println(e.getMessage());
        }
        
    }
}