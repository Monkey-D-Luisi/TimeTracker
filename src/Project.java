import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Project extends Component{

    //region -------------ATRIBUTOS-------------
    private ArrayList<Component> compList = new ArrayList<>();
    //endregion

    //region -------------CONSTRUCTORES-------------
    public Project(String name, Project father, LocalDateTime start, LocalDateTime end, ArrayList<String> tags) {
        super(name, start, end, tags);
        father.addComponent(this);
    }

    public Project(String name, Project father, ArrayList<String> tags) {
        super(name, father, tags);
        father.addComponent(this);
    }

    public Project(String name, Project father) {
        super(name, father);
        father.addComponent(this);
    }

    public Project(String name, ArrayList<String> tags) {
        super(name, tags);
    }
    public Project(String name) {
        super(name);
    }
    //endregion

    //region -------------GETs Y SETs-------------
    public ArrayList<Component> getCompList(){ return compList; }
    public void setCompList(ArrayList<Component> compList){ this.compList = compList; }
    //endregion

    //region -------------MÉTODOS-------------
    public void addComponent(Component c){
        this.compList.add(c);
    }
    //endregion

    @Override
    public void accept(Visitor v){
        for (Component element: this.compList){
            element.accept(v);
        }
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

    @Override
    public String getType() {
        return "Project";
    }
}
