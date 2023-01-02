import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Implementación de Componente que reprsenta un proyecto
 */
public class Project extends Component {

  //region -------------ATRIBUTOS-------------
  private ArrayList<Component> compList = new ArrayList<>();
  //endregion

  //region -------------CONSTRUCTORES-------------

  public Project(int id, String name, Project father, LocalDateTime start,
                 LocalDateTime end, ArrayList<String> tags, Duration time) {
    super(id, name, father, start, end, tags, time);
    father.addComponent(this);
  }

  public Project(String name, Project father, LocalDateTime start,
                 LocalDateTime end, ArrayList<String> tags, Duration time) {
    super(name, father, start, end, tags, time);
    father.addComponent(this);
  }

  public Project(int id, String name, LocalDateTime start, LocalDateTime end,
                 ArrayList<String> tags, Duration time) {
    super(id, name, start, end, tags, time);
  }

  public Project(String name, LocalDateTime start, LocalDateTime end,
                 ArrayList<String> tags, Duration time) {
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
  public void setCompList(ArrayList<Component> compList) {
    this.compList = compList;
  }

  public ArrayList<Component> getCompList() {
    return this.compList;
  }
  //endregion

  //region -------------MÉTODOS-------------
  public void addComponent(Component c) {
    this.compList.add(c);
  }

  public Component searchById(int id){
    Component found = null;
    if (id == this.getId()){
      found = this;
    }
    else{
      if(this.compList != null) {
        int i = 0;
        while(i < this.compList.size()-1 && found == null) {
          found = this.compList.get(i).searchById(id);
          i++;
        }
      }
    }
    return found;
  }

  @Override
  public void accept(Visitor v) {
    v.visitProject(this);
  }

   //endregion
}
