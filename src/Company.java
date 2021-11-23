package src;
import java.util.ArrayList;
import java.util.Collections;

import src.Exceptions.ExCannotSuggestAssignment;
import src.Exceptions.ExEmployeeAlreadyExists;
import src.Exceptions.ExEmployeeAlreadyInTeam;
import src.Exceptions.ExEmployeeNotFound;
import src.Exceptions.ExProjectAlreadyAssigned;
import src.Exceptions.ExProjectAlreadyCompleted;
import src.Exceptions.ExProjectNotAssigned;
import src.Exceptions.ExProjectNotFound;
import src.Exceptions.ExTeamAlreadyExists;
import src.Exceptions.ExTeamNotFound;

public class Company {
    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;
    private ArrayList<Project> allProjects;
    private int nextProjectCode;

    private static Company instance = new Company(); // Singleton pattern

    private Company() {
        allEmployees = new ArrayList<Employee>();
        allTeams = new ArrayList<Team>();
        allProjects = new ArrayList<Project>();
        nextProjectCode = 1;
    }

    public static Company getInstance() {
        return instance;
    }


    // EMPLOYEES MANAGEMENT
    
    public void addEmployee(String eName) throws ExEmployeeAlreadyExists {
        Employee newEmployee = Employee.create(eName, allEmployees);
        allEmployees.add(newEmployee);
        Collections.sort(allEmployees);
    }
    public void removeEmployee(String eName) throws ExEmployeeNotFound {
        Employee e = Employee.findEmployee(eName, allEmployees);
        allEmployees.remove(e);
    }
    public void joinTeam(String tName, String eName) throws ExEmployeeNotFound, ExTeamNotFound, ExEmployeeAlreadyInTeam {
        Employee employee = Employee.findEmployee(eName, allEmployees);
        employee.checkAvailability(); // Don't want to search for team before knowing employee availability
        Team team = Team.findTeam(tName, allTeams);
        team.addMember(employee);
    }
    public void leaveTeam(String teamName, String employeeName) throws ExEmployeeNotFound, ExTeamNotFound {
        Employee employee = Employee.findEmployee(employeeName, allEmployees);
        Team team = Team.findTeam(teamName, allTeams);
        team.removeMember(employee);
    }


    // TEAMS MANAGEMENT

    public void setupTeam(String tName, String leaderName) throws ExEmployeeNotFound, ExTeamAlreadyExists, ExEmployeeAlreadyInTeam {
        Employee leader = Employee.findEmployee(leaderName, allEmployees);
        Team team = Team.create(tName, leader, allTeams);
        allTeams.add(team);
        Collections.sort(allTeams);
    }
    public void disbandTeam(String teamName) throws ExTeamNotFound {
        Team team = Team.findTeam(teamName, allTeams);
        team.disband();
        allTeams.remove(team);
    }


    // PROJECTS MANAGEMENT

    public Project createProject(String title) {
        Project project = Project.create(title, nextProjectCode++);
        allProjects.add(project);
        Collections.sort(allProjects);
        return project;
    }
    public void removeProject(String title) throws ExProjectNotFound {
        Project project = Project.findProject(title, allProjects);
        allProjects.remove(project);
        nextProjectCode--;
    }
    public ProjectExtenstion createProjectExtenstion(String pCode, String title) throws ExProjectNotFound {
        Project p = Project.findProjectByCode(pCode, allProjects);
        ProjectExtenstion extension = p.createProjectExtenstion(title);
        allProjects.add(extension);
        Collections.sort(allProjects);
        return extension;
    }
    public void removeProjectExtension(String pCode, String title) throws ExProjectNotFound {
        Project project = Project.findProject(title, allProjects);
        allProjects.remove(project);
        Project parent = Project.findProjectByCode(pCode, allProjects);
        parent.removeExtension();
    }
    public void assignProject(String pCode, String tName, String[] supStaff) throws ExProjectNotFound, ExTeamNotFound, ExProjectAlreadyAssigned, ExEmployeeNotFound {
        Project project = Project.findProjectByCode(pCode, allProjects);
        project.checkAvailability(); // Don't want to create team and employee objects before knowing if project is assigned
        Team team = Team.findTeam(tName, allTeams);
        ArrayList<Employee> supportStaff = Employee.createSupportStaff(supStaff, allEmployees);
        project.assign(team, supportStaff);
    }
    public void unassignProject(String pCode) throws ExProjectNotFound {
        Project p = Project.findProjectByCode(pCode, allProjects);
        p.unassign();
    }
    public void markProjectCompletion(String pCode) throws ExProjectNotFound, ExProjectNotAssigned, ExProjectAlreadyCompleted {
        Project p = Project.findProjectByCode(pCode, allProjects);
        p.markCompletion();
    }
    public void unmarkProjectCompletion(String pCode) throws ExProjectNotFound {
        Project p = Project.findProjectByCode(pCode, allProjects);
        p.unmarkCompletion();
    }


    // LISTING 

    public void listEmployees() {
        Employee.list(allEmployees);
    }
    public void listEmployeeParticipations() {
        Employee.listParticipations(allEmployees);
    }
    public void listTeams() {
        Team.list(allTeams);
    }
    public void listTeamProjects() {
        Team.listProjects(allTeams);
    }
    public void listProjects() {
        Project.list(allProjects);
    }
    public void listProjectStaff(String pCode) throws ExProjectNotFound, ExProjectNotAssigned {
        Project p = Project.findProjectByCode(pCode, allProjects);
        Project.listStaff(p);
    }
    
    public void giveAssignmentSuggestions(String projectCode) throws ExProjectNotFound, ExCannotSuggestAssignment {
        Project p = Project.findProjectByCode(projectCode, allProjects);
        p.checkIfCanSuggest();
        
        ArrayList<Team> relatedTeams = Team.findRelatedTeams(projectCode, allTeams);
        ArrayList<Employee> relatedEmployees = Employee.findRelatedEmployees(projectCode, allEmployees);

        if (relatedEmployees.isEmpty() && relatedTeams.isEmpty()) {
            System.out.println("No team or staff has worked on related projects.");
        } else {
            if (!relatedTeams.isEmpty()) {
                System.out.println("These teams have worked on related projects:");
                for (Team t: relatedTeams) {
                    System.out.printf("%s: %s\n", t.getName(), t.relevantProjectsToString(projectCode));
                }
            }
            if (!relatedEmployees.isEmpty()) {
                System.out.println("These staff have worked on related projects:");
                for (Employee e: relatedEmployees) {
                    System.out.printf("%s: %s\n", e.getName(), e.relevantProjectsToString(projectCode));
                }
            }
        }
    }
}