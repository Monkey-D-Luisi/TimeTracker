import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Implementación de Componente que representa una tarea dentro de un proyecto
 */
public class Task extends Component {
  //region -------------ATRIBUTOS-------------
  ArrayList<Interval> intervalList = new ArrayList<>();
  //endregion

  //region -------------CONSTRUCTORES-------------

  public Task(int id, String name, Project father, LocalDateTime start, LocalDateTime end,
              ArrayList<String> tags, Duration time) {
    super(id, name, father, start, end, tags, time);
  }

  public Task(String name, Project father, LocalDateTime start, LocalDateTime end,
              ArrayList<String> tags, Duration time) {
    super(name, father, start, end, tags, time);
  }

  public Task(String compName, Project father, ArrayList<String> tagList) {
    super(compName, father, tagList);
    father.addComponent(this);
  }

  public Task(String compName, Project father) {
    super(compName, father);
    father.addComponent(this);
  }

  //endregion

  //region -------------GETS Y SETS-------------
  public ArrayList<Interval> getIntervalList() {
    return intervalList;
  }

  public void setIntervalList(ArrayList<Interval> intervalList) {
    this.intervalList = intervalList;
  }
  //endregion

  //region -------------MÉTODOS-------------
  public void addInterval(Interval i) {
    intervalList.add(i);
  }

  public void start() {
    logger.warn(marker, this.compName + " started");
    Interval currInterval = new Interval(LocalDateTime.now(), this);
    intervalList.add(currInterval);
    Clock.getInstance().addObserver(currInterval);

    if (this.startDate == null) {
      this.setStartDate(LocalDateTime.now());
    }
  }

  public void stop() {
    logger.warn(marker, this.compName + " stopped");
    Interval interval = intervalList.get(intervalList.size() - 1);
    this.setEndDate(LocalDateTime.now());
    Clock.getInstance().deleteObserver(interval);;
  }

  @Override
  public Component searchById(int id){
    if (id == this.getId()){
      return this;
    }
    return null;
  }

  @Override
  public void accept(Visitor v) {
    v.visitTask(this);
  }

  //endregion
}
