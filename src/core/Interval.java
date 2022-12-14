package core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/*
 * Clase que representa un intervalo dentro de una tarea y
   implementa el patrón observer para ser actualizado
   utilizando la información de la instancia observada
 */
public class Interval implements Observer {
  //region -------------ATRIBUTOS-------------
  protected int id;
  private LocalDateTime initialTime;
  private LocalDateTime endTime;
  private final DateTimeFormatter timeFormatter
          = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private Task owner;
  private Duration time = Duration.ZERO;
  private boolean active;

  protected Logger logger = LoggerFactory.getLogger(Interval.class);

  protected Marker marker = MarkerFactory.getMarker("Milestone1");
  //endregion

  //region -------------CONSTRUCTORES-------------
  public Interval(int id, Task owner, LocalDateTime begin, LocalDateTime end, Duration duration) {
    this.id = id;
    this.owner = owner;
    this.initialTime = begin;
    this.endTime = end;
    this.time = duration;
  }

  public Interval(Task owner, LocalDateTime begin, LocalDateTime end, Duration duration) {
    this.id = IdManager.getNewIntervalId();
    this.owner = owner;
    this.initialTime = begin;
    this.endTime = end;
    this.time = duration;
  }

  public Interval(LocalDateTime begin, LocalDateTime end, Duration duration) {
    this.id = IdManager.getNewIntervalId();
    this.initialTime = begin;
    this.endTime = end;
    this.time = duration;
  }

  public Interval(LocalDateTime begin, Task owner) {
    this.id = IdManager.getNewIntervalId();
    this.owner = owner;
    this.initialTime = begin;
  }

  public Interval(Task owner) {
    this.id = IdManager.getNewIntervalId();
    this.owner = owner;
    this.initialTime = LocalDateTime.now();
  }
  //endregion

  //region -------------GETS-------------
  public LocalDateTime getInitialTime() {
    return initialTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public Task getOwner() {
    return owner;
  }

  public void setOwner(Task t) {
    this.owner = t;
  }

  public Duration getTime() {
    return time;
  }

  public int getId() {
    return id;
  }
  //endregion

  //region -------------MÉTODOS-------------
  private void printer() {
    String message = String.format("%-17s", "core.Interval:")
            + String.format("%-19s", "")
            + String.format("%-25s", initialTime.format(timeFormatter))
            + String.format("%-25s", endTime.format(timeFormatter))
            + String.format("%-18s", time.getSeconds());
    logger.info(marker, message);
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("class", "interval");
    json.put("id", id);
    json.put("initialDate", initialTime == null
            ? JSONObject.NULL : timeFormatter.format(initialTime));
    json.put("finalDate", endTime == null
            ? JSONObject.NULL : timeFormatter.format(endTime));
    json.put("duration", time.toSeconds());
    json.put("active", active);
    return json;
  }

  @Override
  public void update(Observable o, Object arg) {
    this.time = this.time.plusSeconds(Clock.getPeriodo());
    this.endTime = (LocalDateTime) arg;
    printer();
    this.owner.update(this.endTime);
  }
  //endregion
}
