package src.Commands;

import src.Command;
import src.Company;

public class CmdListTeams implements Command {
    @Override
    public void execute(String[] cmdParts) {
        Company company = Company.getInstance();
        company.listTeams();
    }
}
