package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import src.Commands.CmdAssignProject;
import src.Commands.CmdAssignmentSuggestions;
import src.Commands.CmdCreateProject;
import src.Commands.CmdCreateProjectExtenstion;
import src.Commands.CmdHire;
import src.Commands.CmdJoinTeam;
import src.Commands.CmdListEmployees;
import src.Commands.CmdListProjectStaff;
import src.Commands.CmdListProjects;
import src.Commands.CmdListStaffParticipations;
import src.Commands.CmdListTeamProjects;
import src.Commands.CmdListTeams;
import src.Commands.CmdMarkCompletion;
import src.Commands.CmdSetupTeam;
import src.Commands.CmdStartNewDay;
import src.Exceptions.ExDateInstanceAlreadyCreated;
import src.Exceptions.ExInsufficientCommand;
import src.Exceptions.ExInvalidDate;
import src.Exceptions.ExWrongCommand;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);

		System.out.print("Please input the file pathname: ");
		String filepathname = in.nextLine();
		
        Scanner inFile = null;

		try {
            inFile = new Scanner(new File(filepathname));
            
            String cmdLine1 = inFile.nextLine();
            String[] cmdLine1Parts = cmdLine1.split("\\|");
            System.out.println("\n> " + cmdLine1);
            SystemDate.createInstance(cmdLine1Parts[1]);
            
            while (inFile.hasNext()) {
                String cmdLine = inFile.nextLine().trim();
                
                if (cmdLine.equals("")) continue;  

                System.out.println("\n> " + cmdLine);
                
                String[] cmdParts = cmdLine.split("\\|"); 
                try {
                    if (cmdParts[0].equals("hire")) {
                        (new CmdHire()).execute(cmdParts);
                    } else if (cmdParts[0].equals("listEmployees")) {
                        (new CmdListEmployees()).execute(cmdParts);
                    } else if (cmdParts[0].equals("undo")) {
                        RecordedCommand.undoOneCommand();
                    } else if (cmdParts[0].equals("redo")) {
                        RecordedCommand.redoOneCommand();
                    } else if (cmdParts[0].equals("startNewDay")) {
                        (new CmdStartNewDay()).execute(cmdParts);
                    } else if (cmdParts[0].equals("setupTeam")) {
                        (new CmdSetupTeam()).execute(cmdParts);
                    } else if (cmdParts[0].equals("listTeams")) {
                        (new CmdListTeams()).execute(cmdParts);
                    } else if (cmdParts[0].equals("joinTeam")) {
                        (new CmdJoinTeam()).execute(cmdParts);
                    } else if (cmdParts[0].equals("createProject")) {
                        (new CmdCreateProject()).execute(cmdParts);
                    } else if (cmdParts[0].equals("listProjects")) {
                        (new CmdListProjects()).execute(cmdParts);
                    } else if (cmdParts[0].equals("assignProject")) {
                        (new CmdAssignProject()).execute(cmdParts);
                    } else if (cmdParts[0].equals("markCompletion")) {
                        (new CmdMarkCompletion()).execute(cmdParts);
                    } else if (cmdParts[0].equals("listTeamProjects")) {
                        (new CmdListTeamProjects()).execute(cmdParts);
                    } else if (cmdParts[0].equals("createExtensionProject")) {
                        (new CmdCreateProjectExtenstion()).execute(cmdParts);
                    } else if (cmdParts[0].equals("listProjectStaff")) {
                        (new CmdListProjectStaff()).execute(cmdParts);
                    } else if (cmdParts[0].equals("listStaffParticipations")) {
                        (new CmdListStaffParticipations()).execute(cmdParts);
                    } else if (cmdParts[0].equals("giveAssignmentSuggestions")) {
                        (new CmdAssignmentSuggestions()).execute(cmdParts);
                    } else {
                        throw new ExWrongCommand();
                    }
                } catch (ExInsufficientCommand e) {
                    System.out.println(e.getMessage());
                } catch (ExWrongCommand e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (ExInvalidDate e) {
            System.out.println(e.getMessage());
        } catch (ExDateInstanceAlreadyCreated e) {
            System.out.println(e.getMessage());
        } finally {
            if (inFile != null) {
                inFile.close();
            }
            in.close();
        }
	}
}