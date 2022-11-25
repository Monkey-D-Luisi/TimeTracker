import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Clase principal del proyecto
 */
public class Client {
  public static void main(String[] args) throws InterruptedException {
    //testA();
    testB();
    //loadData("fita1.json");
    //testFita2();
    System.exit(0);
  }

  private static void testA() {
    Project root = createTree();
  }

  private static void testB() throws InterruptedException {
    Clock ourTimer = Clock.getInstance();

    Logger logger = LoggerFactory.getLogger(Component.class);
    Marker marker = MarkerFactory.getMarker("Milestone1");

    Project rootProject = new Project("root");
    Project softwareDesignProject = new Project("Software Design", rootProject,
            new ArrayList<String>(Arrays.asList("java", "flutter")));
    Project softwareTestingProject = new Project("Software Testing", rootProject,
            new ArrayList<String>(Arrays.asList("c++", "Java", "python")));
    Project databaseProject = new Project("Databases", rootProject,
            new ArrayList<String>(Arrays.asList("SQL", "python", "C++")));
    Task transportationTask = new Task("transportation", rootProject);

    Project timeTrackerProject = new Project("Time Tracker", softwareDesignProject);
    Task readHandoutTask = new Task("Read Handout", timeTrackerProject);
    Task firstMilestoneTask = new Task("First Milestone", timeTrackerProject,
            new ArrayList<String>(Arrays.asList("Java", "IntelliJ")));

    String message =  String.format("%-20s", "") + String.format("%-19s", "")
            + String.format("%-25s", "initial date") + String.format("%-25s", "final date")
            + String.format("%-4s", "duration");
    logger.info(marker, message);

    logger.info(marker, "start tests");

    transportationTask.start();

    Thread.sleep(6000);
    transportationTask.stop();

    Thread.sleep(2000);

    Project problemProject = new Project("Problems", softwareDesignProject);

    Task firsListTask = new Task("First List", problemProject,
            new ArrayList<String>(Arrays.asList("java")));
    Task secondListTask = new Task("Second List", problemProject,
            new ArrayList<String>(Arrays.asList("Dart")));

    firsListTask.start();

    Thread.sleep(6000);

    secondListTask.start();
    Thread.sleep(4000);

    firsListTask.stop();

    Thread.sleep(2000);
    secondListTask.stop();

    Thread.sleep(2000);

    transportationTask.start();
    Thread.sleep(4000);
    transportationTask.stop();

    logger.info(marker, "end of tests");

    saveData(rootProject);
  }

  private static Project createTree() {
    Project rootProject = new Project("root");
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

  private static void saveData(Project root) {
    JsonVisitor v = new JsonVisitor();
    root.accept(v);
    try {
      v.save("fita1.json");
      v.print();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void loadData(String filename) {
    JsonVisitor v = new JsonVisitor();
    Project root = null;
    try {
      root = v.load("./datos/" + filename);
      root.accept(v);
      v.print();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void testFita2() {
    Project root = createTree();

    ArrayList<String> tagList = new ArrayList<String>(Arrays.asList("java",
            "JAVA", "intellij", "c++", "python"));
    for (String tag : tagList) {
      Visitor v = new SearchByTag(tag);
      root.accept(v);
    }
  }
}