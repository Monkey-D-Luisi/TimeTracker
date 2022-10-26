import java.util.ArrayList;
import java.util.Arrays;

public class Client {
    private static void testA(){
        Project rootProject = new Project("root");
        Project softwareDesignProject = new Project("Software Design", rootProject, new ArrayList<String>(Arrays.asList("java", "flutter")));
        Project softwareTestingProject = new Project("Software Testing", rootProject, new ArrayList<String>(Arrays.asList("c++", "Java", "python")));
        Project databaseProject = new Project("Databases", rootProject, new ArrayList<String>(Arrays.asList("SQL", "python", "C++")));
        Task transportationTask = new Task("transportation", rootProject);

        Project problemProject = new Project("Problems", softwareDesignProject);
        Project timeTrackerProject = new Project("Time Tracker", softwareDesignProject);

        Task firsListTask = new Task("First List", problemProject);
        Task secondListTask = new Task("Second List", problemProject, new ArrayList<String>(Arrays.asList("Dart")));

        Task readHandoutTask = new Task("Read Handout", timeTrackerProject);
        Task firstMilestoneTask = new Task("First Milestone", timeTrackerProject, new ArrayList<String>(Arrays.asList("Java", "IntelliJ")));

        System.out.println(rootProject.toString());
    }

    private static void testB(){

    }
    public static void saveData(){

    }
    public static void loadData(){

    }

    public static void main(String[] args) {
        testA();
        //testB();
        //saveData();
        //loadData();
    }
}