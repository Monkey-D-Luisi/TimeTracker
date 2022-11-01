import java.io.FileNotFoundException;
import java.io.IOException;

public interface Visitor {
    public void visitProject(Project p);
    public void visitTask(Task t);

    public void save(String s) throws IOException;
    public Project load(String name)throws FileNotFoundException;
}
