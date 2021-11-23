package src.Commands;
import src.Command;
import src.Company;

public class CmdListProjects implements Command {
    @Override
    public void execute(String[] cmdParts) {
        Company company = Company.getInstance();
        company.listProjects();
    }
}
