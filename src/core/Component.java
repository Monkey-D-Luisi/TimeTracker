package core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/*
 * Clase abstracta que representa un elemento con final, duración y, quizás, fin y padre
 * La implementración podrá actualizar su porpio final y duración así como, recursivamente,
 * los de sus predecesores
 * La implementación también puede pintar los datos
 */
public abstract class Component {


  //region -------------ATRIBUTOS-------------
  protected int id;
  protected String compName = null;
  protected Project father = null;
  protected LocalDateTime startDate = null;
  protected LocalDateTime endDate = null;
  protected ArrayList<String> tagList = new ArrayList<>();
  protected boolean active;
  private final DateTimeFormatter
          timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private Duration time = Duration.ZERO;

  protected Logger logger = LoggerFactory.getLogger(Component.class);

  protected Marker marker = MarkerFactory.getMarker("Milestone1");
  //endregion

  //region -------------CONSTRUCTORES-------------

  public Component(int id, String name, Project father, LocalDateTime start, LocalDateTime end,
                   ArrayList<String> tags, Duration time) {
    this.id = id;
    this.compName = name;
    this.startDate = start;
    this.endDate = end;
    this.tagList = tags;
    this.father = father;
    this.time = time;
  }

  public Component(String name, Project father, LocalDateTime start, LocalDateTime end,
                    ArrayList<String> tags, Duration time) {
    this.id = IdManager.getNewComponentId();
    this.compName = name;
    this.startDate = start;
    this.endDate = end;
    this.tagList = tags;
    this.father = father;
    this.time = time;
  }

  public Component(int id, String name, LocalDateTime start, LocalDateTime end,
                   ArrayList<String> tags, Duration time) {
    this.id = id;
    this.compName = name;
    this.startDate = start;
    this.endDate = end;
    this.tagList = tags;
    this.time = time;
  }

  public Component(String name, LocalDateTime start, LocalDateTime end,
                      ArrayList<String> tags, Duration time) {
    this.id = IdManager.getNewComponentId();
    this.compName = name;
    this.startDate = start;
    this.endDate = end;
    this.tagList = tags;
    this.time = time;
  }

  public Component(String compName, Project father, ArrayList<String> tagList) {
    this.id = IdManager.getNewComponentId();
    this.compName = compName;
    this.father = father;
    this.tagList = tagList;
  }

  public Component(String compName, Project father) {
    this.id = IdManager.getNewComponentId();
    this.compName = compName;
    this.father = father;
  }

  public Component(String name) {
    this.id = IdManager.getNewComponentId();
    this.compName = name;
  }
  //endregion

  //region -------------GET Y SET-------------
  public String getCompName() {
    return this.compName;
  }

  public Project getFather() {
    return this.father;
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
    if (this.father != null && this.father.getStartDate() == null) {
      this.father.setStartDate(startDate);
    }
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
    if (this.father != null) {
      this.father.setEndDate(endDate);
    }
  }

  public Duration getTime() {
    return this.time;
  }

  public ArrayList<String> getTagList() {
    return tagList;
  }

  public int getId() {
    return id;
  }
  //endregion

  //region -------------MÉTODOS-------------
  public void update(LocalDateTime endTime) {
    this.endDate = endTime;
    this.time = this.time.plusSeconds(Clock.getPeriodo());
    printer();
    if (this.father != null) {
      this.father.update(this.endDate);
    }
  }

  public void printer() {
    String message =  String.format("%-16s", "activity:")
            + String.format("%-19s", this.getCompName())
            + String.format("%-25s", this.getStartDate().format(timeFormatter))
            + String.format("%-25s", this.getEndDate().format(timeFormatter))
            + String.format("%-10s", this.getTime().getSeconds());
    logger.info(marker, message);
  }

  public abstract Component searchById(int id);

  public abstract Component searchByName(String name);

  public abstract void addActivity(boolean isProject, String name, Component father);

  /*
   * Método del patrón visitor
   * @param v el visitante que estamos aceptando
   */
  public abstract void accept(Visitor v);

  public abstract JSONObject toJson(int depth); // added 16-dec-2022

  protected void toJson(JSONObject json) {
    json.put("id", id);
    json.put("name", compName);
    json.put("initialDate", startDate == null
            ? JSONObject.NULL : timeFormatter.format(startDate));
    json.put("finalDate", endDate == null
            ? JSONObject.NULL : timeFormatter.format(endDate));
    json.put("duration", time.toSeconds());
  }


  //endregion
}
