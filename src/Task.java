import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task extends Component{
    //region -------------ATRIBUTOS-------------
    ArrayList<Interval> intervalList = new ArrayList<>();
    //endregion

    //region -------------CONSTRUCTORES-------------
    public Task(String name, LocalDateTime start, LocalDateTime end, ArrayList<String> tags) {
        super(name, start, end, tags);
    }

    public Task(String compName, Project father, ArrayList<String> tagList) {
        super(compName, father, tagList);
        father.addComponent(this);
    }

    public Task(String compName, Project father) {
        super(compName, father);
        father.addComponent(this);
    }

    public Task(String name, ArrayList<String> tags) {
        super(name, tags);
    }
    public Task(String name) {
        super(name);
    }
    //endregion

    @Override
    public String toString() {
        return "Task{" +
                "\n compName='" + compName + ',' +
                "\n father=" + father.getCompName() + ','+
                "\n tagList=" + tagList + ','+
                "\n } \n";
    }

    @Override
    public String getType() {
        return "Task";
    }
}
