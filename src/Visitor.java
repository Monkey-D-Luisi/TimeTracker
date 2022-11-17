import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface Visitor {
    public void visitProject(Project p);
    public void visitTask(Task t);



}
