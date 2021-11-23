package src;
import java.util.ArrayList;
import java.util.Collections;

import src.Exceptions.ExCannotSuggestAssignment;
import src.Exceptions.ExProjectAlreadyAssigned;
import src.Exceptions.ExProjectAlreadyCompleted;
import src.Exceptions.ExProjectNotAssigned;
import src.Exceptions.ExProjectNotFound;

public class Project implements Comparable<Project> {
    private static int CODE_DIGITS_NUM = 3; // single point of access for format

    private String title;
    private String code;
    private Day dateCreated;
    
    private Team assignedTeam;
    private Day dateAssigned;
    private Day dateCompleted;
    private ArrayList<Employee> supportStaff;
    
    private PStatus status;
    private int nextExtensionCode;

    protected Project(String title, String code) { // protected to make it accessible in Project Extension
        this.title = title;
        this.code = code;
        this.dateCreated = SystemDate.getInstance().clone();

        this.assignedTeam = null;
        this.dateAssigned = null;
        this.dateCompleted = null;
        this.supportStaff = new ArrayList<Employee>();

        this.status = PStatus_Pending.getInstance();
        this.nextExtensionCode = 1;
    }

    // static creation function and hidden constructor to handle format 
    // and possible exceptions related to project
    // Making Project model similar to Employee and Team
    public static Project create(String title, int code) { // project is concerned about code format, not the company
        String pCode = String.format("P%03d", code);       // consider static variable for number of digits in format
        Project project = new Project(title, pCode);
        return project;
    }

    // ASSIGNMENT CONTROL
    public void checkAvailability() throws ExProjectAlreadyAssigned {
        if (status != PStatus_Pending.getInstance()) {
            throw new ExProjectAlreadyAssigned(getCode(), assignedTeam.getName(), dateAssigned.toString());
        }
    }
    public void assign(Team team, ArrayList<Employee> supportStaff) {
        assignedTeam = team;
        dateAssigned = SystemDate.getInstance().clone();
        status = PStatus_InProgress.getInstance();
        team.assignProject(this);
        this.supportStaff = supportStaff;
        Collections.sort(this.supportStaff);
        for (Employee e: this.supportStaff) {
            e.addParticipation(this);
        }
    }
    public void unassign() {
        assignedTeam.removeProject(this);
        assignedTeam = null;
        dateAssigned = null;
        status = PStatus_Pending.getInstance();
        for (Employee e: this.supportStaff) {
            e.removeParticipation(this);
        }
        supportStaff.clear();
    }

    // COMPLETION CONTROL
    public void markCompletion() throws ExProjectNotAssigned, ExProjectAlreadyCompleted {
        if (status == PStatus_Pending.getInstance()) {
            throw new ExProjectNotAssigned(getCode());
        } else if (status == PStatus_Completed.getInstance()) {
            throw new ExProjectAlreadyCompleted(getCode());
        }
        dateCompleted = SystemDate.getInstance().clone();
        status = PStatus_Completed.getInstance();
    }
    public void unmarkCompletion() {
        dateCompleted = null;
        status = PStatus_InProgress.getInstance();
    }

    // PROJECT EXSTENSION CONTROL
    public ProjectExtenstion createProjectExtenstion(String title) {
        ProjectExtenstion extension = ProjectExtenstion.create(title, code, nextExtensionCode++);
        return extension;
    }
    public void removeExtension() {
        nextExtensionCode--;
    }

    // SUGGESTIONS CONTROL
    public void checkIfCanSuggest() throws ExCannotSuggestAssignment {
        if (status != PStatus_Pending.getInstance()) {
            throw new ExCannotSuggestAssignment();
        }
    }
    public static ArrayList<Project> findRelatedProjects(String pCode, ArrayList<Project> allProjects) {
        String parentCode = pCode.substring(0, CODE_DIGITS_NUM + 1); // getting parent/common code
        ArrayList<Project> relatedProjects = new ArrayList<Project>();
        for (Project p: allProjects) {
            if (p.code.equals(parentCode)) {
                relatedProjects.add(p);
            }
        }
        return relatedProjects;
    }

    // STATIC SERVICE FUNCTIONS
    public static Project findProject(String title, ArrayList<Project> allProjects) throws ExProjectNotFound {
        for (Project p: allProjects) {
            if (p.title.equals(title)) {
                return p;
            }
        }
        throw new ExProjectNotFound(title);
    }
    public static Project findProjectByCode(String code, ArrayList<Project> all) throws ExProjectNotFound {
        for (Project p: all) {
            if (p.getCode().equals(code)) { // use getCode() as extension includes exstension number
                return p;
            }
        }
        throw new ExProjectNotFound(code);
    }
    public static void list(ArrayList<Project> allProjects) {
        System.out.printf("%-9s%-23s%-13s%-13s%-14s%-13s%-13s\n", "Code", "Project Title", "Created on", "Status", "Assigned to", "Assigned on", "Completed on");
        for (Project p: allProjects) {
            System.out.printf("%-9s%-23s%-13s%-13s%-14s%-13s%-13s\n", p.getCode(), p.title, p.dateCreated, p.status, p.getTeamName(), p.getAssignDate(), p.getCompleteDate());
        }
    }
    public static void listStaff(Project p) throws ExProjectNotAssigned {
        if (p.status == PStatus_Pending.getInstance()) {
            throw new ExProjectNotAssigned(p.getCode());
        }
        String pInfo = "Project team: " + p.assignedTeam.getName();

        pInfo += "\nProject team members: " + p.assignedTeam.getLeaderName() + " (The Leader)";
        String members = p.assignedTeam.membersToString();
        pInfo += (members != "" ? String.format(", %s", members) : "");
        
        pInfo += "\nExternal support: ";
        ArrayList<String> supStaffToString = new ArrayList<String>();
        for (Employee e : p.supportStaff) {
            supStaffToString.add(e.getName());
        }
        String supStaff = Utilities.listWithComma(supStaffToString);
        pInfo += String.format("%s", (supStaff == "" ? "(none)" : supStaff));
        System.out.println(pInfo);
    }

    // ACCESSORS
    public boolean inProgress() {
        return status == PStatus_InProgress.getInstance();
    }
    public String getCode() { // if invoked by a normal project returns its number,
        return code;          // but overwritten by ProjectExtension to include extension number
    }
    private String getTeamName() {
        if (assignedTeam != null) {
            return assignedTeam.getName();
        }
        return "--";
    }
    private String getAssignDate() {
        if (dateAssigned != null) {
            return dateAssigned.toString();
        }
        return "--";
    }
    private String getCompleteDate() {
        if (dateCompleted != null) {
            return dateCompleted.toString();
        }
        return "--";
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", getCode(), title, dateCreated);
    }
    public String toShortString() { // shorter version for some lists
        return String.format("%s(%s)", getCode(), this.status);
    }
    @Override
    public int compareTo(Project another) {
        return this.getCode().compareTo(another.getCode());
    }
}
