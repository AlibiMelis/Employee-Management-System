package src;
import java.util.ArrayList;
import java.util.Collections;

import src.Exceptions.ExEmployeeAlreadyExists;
import src.Exceptions.ExEmployeeAlreadyInTeam;
import src.Exceptions.ExEmployeeNotFound;

public class Employee implements Comparable<Employee> {
    private String name;
    private Team team;
    private ArrayList<Project> projectsParticipation;

    private Employee(String name) {
        this.name = name;
        this.team = null;
        projectsParticipation = new ArrayList<Project>();
    }

    // static creation function with private constructor to handle exceptions related to Employee
    public static Employee create(String eName, ArrayList<Employee> allEmployees) throws ExEmployeeAlreadyExists {
        if (employeeExists(eName, allEmployees)) {
            throw new ExEmployeeAlreadyExists();
        }
        return new Employee(eName);
    }
    
    // TEAM CONTROL
    public void checkAvailability() throws ExEmployeeAlreadyInTeam {
        if (team != null) {
            throw new ExEmployeeAlreadyInTeam(name, team.getName());
        }
    }
    public void setTeam(Team team) {
        this.team = team;
    }

    // PROJECTPARTICIPATION CONTROL
    public void addParticipation(Project p) {
        projectsParticipation.add(p);
        Collections.sort(projectsParticipation);
    }
    public void removeParticipation(Project p) {
        projectsParticipation.remove(p);
    }
    
    // STATIC SERVICE FUNCTIONS
    public static Employee findEmployee(String eName, ArrayList<Employee> allEmployees) throws ExEmployeeNotFound {
        for (Employee e: allEmployees) {
            if (e.name.equals(eName)) {
                return e;
            }
        }
        throw new ExEmployeeNotFound(eName);
    }
    public static ArrayList<Employee> findRelatedEmployees(String pCode, ArrayList<Employee> allEmployees) {
        ArrayList<Employee> relatedEmployees = new ArrayList<Employee>();
        for (Employee e: allEmployees) {
            ArrayList<Project> relatedProjects = Project.findRelatedProjects(pCode, e.projectsParticipation);
            if (!relatedProjects.isEmpty()) {
                relatedEmployees.add(e);
            }
        }
        return relatedEmployees;
    }
    public static ArrayList<Employee> createSupportStaff(String[] supStaff, ArrayList<Employee> allEmployees) throws ExEmployeeNotFound {
        ArrayList<Employee> supportStaff = new ArrayList<Employee>();
        for (String eName: supStaff) {
            Employee e = findEmployee(eName, allEmployees);
            supportStaff.add(e);
        }
        return supportStaff;
    }
    public static void list(ArrayList<Employee> allEmployees) {
        for (Employee e: allEmployees) {
            System.out.println(e.toString());
        }
    }
    public static void listParticipations(ArrayList<Employee> allEmployees) {
        for (Employee e: allEmployees) {
            ArrayList<String> participationsToString = new ArrayList<String>();
            for (Project p: e.projectsParticipation) {
                participationsToString.add(p.toShortString());
            }
            String participation = Utilities.listWithComma(participationsToString);
            System.out.printf("%s: %s\n", e.getName(), (participation == "" ? "(no project)" : participation));
        }
    }
    // used to check existance of employee inside creation function/constructor
    private static boolean employeeExists(String eName, ArrayList<Employee> allEmployees) {
        for (Employee e: allEmployees) {
            if (e.name.equals(eName)) {
                return true;
            }
        }
        return false;
    }

    // ACCESSORS
    public String getName() {
        return name;
    }
    public String relevantProjectsToString(String pCode) {
        ArrayList<Project> relevantProjects = Project.findRelatedProjects(pCode, projectsParticipation);
        ArrayList<String> relevantProjectsToString = new ArrayList<String>();
        for (Project p: relevantProjects) {
            relevantProjectsToString.add(p.toShortString());
        }
        return Utilities.listWithComma(relevantProjectsToString);
    }

    @Override
    public String toString() {
        String e = this.name;
        if (team != null) {
            e += String.format(" (%s)", team.getName());
        }
        return e;
    }
    @Override
    public int compareTo(Employee another) {
        return this.name.compareTo(another.name);
    }
}