package src.Commands;

import src.Exceptions.ExInsufficientCommand;
import src.Exceptions.ExProjectNotAssigned;
import src.Exceptions.ExProjectNotFound;
import src.Command;
import src.Company;

public class CmdListProjectStaff implements Command {
    @Override
    public void execute(String[] cmdParts) throws ExInsufficientCommand {
        try {
            if (cmdParts.length < 2) {
                throw new ExInsufficientCommand();
            }
            String projectCode = cmdParts[1];
            Company company = Company.getInstance();
            company.listProjectStaff(projectCode);
        } catch (ExProjectNotFound e) {
            System.out.print(e.getMessage());
        } catch (ExProjectNotAssigned e) {
            System.out.print(e.getMessage());
        }
        
    }
}
