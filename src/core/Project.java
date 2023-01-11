package core;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/*
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

  public Project(String name, Project father, int id) {
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

  public void addActivity(boolean isProject, String name, Component father) {
    if (isProject) {
      Project newP = new Project(name, (Project) father);
    } else {
      Task newT = new Task(name, (Project) father);
    }
  }

  public Component searchById(int id) {
    Component found = null;
    if (id == this.getId()) {
      found = this;
    } else {
      if (this.compList != null) {
        int i = 0;
        while (i < this.compList.size() && found == null) {
          found = this.compList.get(i).searchById(id);
          i++;
        }
      }
    }
    return found;
  }

  @Override
  public Component searchByName(String name) {
    Component found = null;
    if (name.equals(this.getCompName())) {
      found = this;
    } else {
      if (this.compList != null) {
        int i = 0;
        while (i < this.compList.size() && found == null) {
          found = this.compList.get(i).searchByName(name);
          i++;
        }
      }
    }
    return found;
  }

  public JSONObject toJson(int depth) {
    JSONObject json = new JSONObject();
    json.put("class", "project");
    super.toJson(json);
    if (depth > 0) {
      JSONArray jsonActivities = new JSONArray();
      for (Component activity : compList) {
        jsonActivities.put(activity.toJson(depth - 1));
        // important: decrement depth
      }
      json.put("activities", jsonActivities);
    }
    return json;
  }

  @Override
  public void accept(Visitor v) {
    v.visitProject(this);
  }

  //endregion
}
