package src;
import java.util.ArrayList;
import java.util.Collections;

import src.Exceptions.ExEmployeeAlreadyInTeam;
import src.Exceptions.ExTeamAlreadyExists;
import src.Exceptions.ExTeamNotFound;

public class Team implements Comparable<Team> {
    private String name;
    private Employee leader;
    private Day dateCreated;
    private ArrayList<Employee> members;
    private ArrayList<Project> projects;

    private Team(String name, Employee leader) {
        this.name = name;
        this.leader = leader;
        this.leader.setTeam(this);
        this.dateCreated = SystemDate.getInstance().clone();
        members = new ArrayList<Employee>();
        projects = new ArrayList<Project>();
    }

    // static creation function with private constructor to handle exceptions related to Team
    public static Team create(String tName, Employee leader, ArrayList<Team> allTeams) throws ExTeamAlreadyExists, ExEmployeeAlreadyInTeam {
        if (teamExists(tName, allTeams)) {
            throw new ExTeamAlreadyExists();
        }
        leader.checkAvailability(); // Don't want to create team object before knowing leader availability
        Team team = new Team(tName, leader);
        return team;
    }

    // MEMBERS CONTROL
    public void addMember(Employee e) {
        members.add(e);
        for (Project p: projects) {
            if (p.inProgress()) {      // member participates only if in a team while 
                e.addParticipation(p); // project is active
            }
        }
        Collections.sort(members);
        e.setTeam(this);
    }
    public void removeMember(Employee e) {
        members.remove(e);
        for (Project p : projects) {
            if (p.inProgress()) {         // participation is not counted if member
                e.removeParticipation(p); // wasn't in a team till project completion
            }
        }
        e.setTeam(null);
    }

    // MEMBERS TEAM CONTROL (in case of team disband)
    public void disband() {
        leader.setTeam(null);
        for (Employee e : members) {
            e.setTeam(null);
        }
    }

    // PROJECT ASSIGNMENT CONTROL
    public void assignProject(Project p) {
        projects.add(p);
        leader.addParticipation(p);
        for (Employee e : members) {
            e.addParticipation(p);
        }
        Collections.sort(projects);
    }
    public void removeProject(Project p) {
        projects.remove(p);
        leader.removeParticipation(p);
        for (Employee e: members) {
            e.removeParticipation(p);
        }
    }

    // STATIC SERVICE FUNCTIONS
    public static Team findTeam(String tName, ArrayList<Team> allTeams) throws ExTeamNotFound {
        for (Team t: allTeams) {
            if (t.name.equals(tName)) {
                return t;
            }
        }
        throw new ExTeamNotFound(tName);
    }
    public static ArrayList<Team> findRelatedTeams(String pCode, ArrayList<Team> allTeams) {
        ArrayList<Team> relatedTeams = new ArrayList<Team>();
        for (Team t: allTeams) {
            ArrayList<Project> relatedProjects = Project.findRelatedProjects(pCode, t.projects);
            if (!relatedProjects.isEmpty()) {
                relatedTeams.add(t);
            }
        }
        return relatedTeams;
    }
    public static void list(ArrayList<Team> allTeams) {
        System.out.printf("%-15s%-10s%-13s%s\n", "Team Name", "Leader", "Setup Date", "Members");
        for (Team t: allTeams) {
            System.out.printf("%-15s%-10s%-13s%s\n", t.name, t.leader.getName(), t.dateCreated, (t.membersToString() == "" ? "(no member)" : t.membersToString()));
        }
    }
    public static void listProjects(ArrayList<Team> all) {
        for (Team t: all) {
            ArrayList<String> projectsToString = new ArrayList<String>();
            for (Project p: t.projects) {
                projectsToString.add(p.toShortString());
            }
            String allProjects = Utilities.listWithComma(projectsToString);
            System.out.printf("%s: %s\n", t.name, allProjects);
        }
    }
    // used to check existance of team inside creation function/constructor
    private static boolean teamExists(String tName, ArrayList<Team> allTeams) {
        for (Team team : allTeams) {
            if (team.name.equals(tName)) {
                return true;
            }
        }
        return false;
    }

    // ACCESSORS
    public String getName() {
        return name;
    }
    public String getLeaderName() {
        return leader.getName();
    }
    public String membersToString() {
        ArrayList<String> membersToString = new ArrayList<String>();
        for (Employee e: members) {
            membersToString.add(e.getName());
        }
        return Utilities.listWithComma(membersToString);
    }
    public String relevantProjectsToString(String pCode) {
        ArrayList<Project> relevantProjects = Project.findRelatedProjects(pCode, projects);
        ArrayList<String> relevantProjectsToString = new ArrayList<String>();
        for (Project p: relevantProjects) {
            relevantProjectsToString.add(p.toShortString());
        }
        return Utilities.listWithComma(relevantProjectsToString);
    }

    @Override
    public int compareTo(Team another) {
        return this.name.compareTo(another.getName());
    }
}
