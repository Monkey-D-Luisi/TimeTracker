import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private static void testB() throws InterruptedException{
        Visitor v = new ElementVisitor();
        Clock OurTimer = Clock.getInstance();

        Project rootProject = new Project("root");
        Project softwareDesignProject = new Project("software design", rootProject, new ArrayList<String>(Arrays.asList("java", "flutter")));
        Project softwareTestingProject = new Project("software testing", rootProject, new ArrayList<String>(Arrays.asList("c++", "Java", "python")));
        Project databaseProject = new Project("databases", rootProject, new ArrayList<String>(Arrays.asList("SQL", "python", "C++")));
        Task transportationTask = new Task("transportation", rootProject);

        Project problemProject = new Project("problems", softwareDesignProject);
        Project timeTrackerProject = new Project("time tracker", softwareDesignProject);

        Task firsListTask = new Task("first list", problemProject);
        Task secondListTask = new Task("second list", problemProject, new ArrayList<String>(Arrays.asList("Dart")));

        Task readHandoutTask = new Task("read handout", timeTrackerProject);
        Task firstMilestoneTask = new Task("first milestone", timeTrackerProject, new ArrayList<String>(Arrays.asList("Java", "IntelliJ")));

        //Thread.sleep(1500);
        System.out.format("%-16s %-19s %-25s %-25s %-10s \n", "", "", "initial date", "final date", "duration");
        System.out.println("start test");

        transportationTask.start();
        System.out.println("transportation starts");
        Thread.sleep(6000);
        transportationTask.stop();
        System.out.println("transportation stops");

        Thread.sleep(2000);


        firsListTask.start();
        System.out.println("first list starts");
        Thread.sleep(6000);

        secondListTask.start();
        System.out.println("second list starts");
        Thread.sleep(4000);

        firsListTask.stop();
        System.out.println("first list stops");

        Thread.sleep(2000);
        secondListTask.stop();
        System.out.println("second list stops");

        Thread.sleep(2000);

        transportationTask.start();
        System.out.println("transportation starts");
        Thread.sleep(4000);
        transportationTask.stop();
        System.out.println("transportation stops");

        System.out.println("end of tests");

        rootProject.accept(v);


    }
    public static void saveData(){

    }
    public static void loadData(){

    }

    public static void main(String[] args) throws InterruptedException{
        //testA();
        testB();
        //saveData();
        //loadData();
        System.exit(0);
    }
}