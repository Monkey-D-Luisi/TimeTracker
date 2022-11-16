import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface Visitor {
    public void visitProject(Project p);
    public void visitTask(Task t);

    public void save(String s) throws IOException;
    public Project load(String name)throws FileNotFoundException;

    public abstract ArrayList<Component> searchByTag(Project p, String tag);
    public abstract ArrayList<Component> searchByTag(Task t, String tag);
}
