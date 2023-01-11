package webserver;


import core.Clock;
import core.Component;
import core.Project;
import core.Task;
import java.util.ArrayList;
import java.util.Arrays;

public class MainWebServer {
  public static void main(String[] args) {
    webServer();
  }

  public static void webServer() {
    final Project root = makeTreeCourses();
    // implement this method that returns the tree of
    // appendix A in the practicum handout

    // start your clock
    Clock ourTimer = Clock.getInstance();

    new WebServer(root);
  }

  private static Project makeTreeCourses() {
    Project rootProject = new Project("Home");
    Project softwareDesignProject = new Project("Software Design", rootProject,
                new ArrayList<String>(Arrays.asList("java", "flutter")));
    Project softwareTestingProject = new Project("Software Testing", rootProject,
                new ArrayList<String>(Arrays.asList("c++", "Java", "python")));
    Project databaseProject = new Project("Databases", rootProject,
                new ArrayList<String>(Arrays.asList("SQL", "python", "C++")));
    Task transportationTask = new Task("transportation", rootProject);

    Project problemProject = new Project("Problems", softwareDesignProject);
    Project timeTrackerProject = new Project("Time Tracker", softwareDesignProject);
    Task firsListTask = new Task("First List", problemProject,
                new ArrayList<String>(Arrays.asList("java")));
    Task secondListTask = new Task("Second List", problemProject,
                new ArrayList<String>(Arrays.asList("Dart")));
    Task readHandoutTask = new Task("Read Handout", timeTrackerProject);
    Task firstMilestoneTask = new Task("First Milestone", timeTrackerProject,
                new ArrayList<String>(Arrays.asList("Java", "IntelliJ")));

    return rootProject;
  }
}
