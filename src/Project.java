import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Project extends Component{

    //region -------------ATRIBUTOS-------------
    private ArrayList<Component> compList = new ArrayList<>();
    //endregion

    //region -------------CONSTRUCTORES-------------
    public Project(String name, Project father, LocalDateTime start, LocalDateTime end, ArrayList<String> tags, Duration time) {
        super(name, father, start, end, tags, time);
        father.addComponent(this);
    }

    public Project(String name, LocalDateTime start, LocalDateTime end, ArrayList<String> tags, Duration time) {
        super(name, start, end, tags, time);
    }

    public Project(String name, Project father, ArrayList<String> tags) {
        super(name, father, tags);
        father.addComponent(this);
    }

    public Project(String name, Project father) {
        super(name, father);
        father.addComponent(this);
    }

    public Project(String name) {
        super(name);
    }

    //endregion

    //region -------------GETs Y SETs-------------
    public void setCompList(ArrayList<Component> compList){ this.compList = compList; }
    public ArrayList<Component> getCompList() {
        return this.compList;
    }
    //endregion

    //region -------------MÉTODOS-------------
    public void addComponent(Component c){
        this.compList.add(c);
    }

    @Override
    public void accept(Visitor v){
        v.visitProject(this);
    }

    @Override
    public String toString() {
        if (father != null) {
            return "Project{" +
                    "compName='" + compName + ',' +
                    "\n father=" + father.getCompName() + ','+
                    "\n tagList=" + tagList + ','+
                    "\n compList= \n" + compList + ','+
                    '}';
        }
        else{
            return "Project{" +
                    "\n compName='" + compName + ',' +
                    "\n tagList=" + tagList + ','+
                    "\n compList= \n" + compList + ','+
                    "\n } \n";
        }
    }
    //endregion

}
