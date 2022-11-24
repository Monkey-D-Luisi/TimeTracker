import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Class that represents an interval inside a task and
 * implements observer pattern to be updated using the data of the observable
 */
public class Interval implements Observer {
  //region -------------ATRIBUTOS-------------
  private LocalDateTime initialTime;
  private LocalDateTime endTime;
  private final DateTimeFormatter timeFormatter
          = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private Task owner;
  private Duration time = Duration.ZERO;

  protected Logger logger = LoggerFactory.getLogger(Interval.class);

  protected Marker marker = MarkerFactory.getMarker("Milestone1");
  //endregion

  //region -------------CONSTRUCTORES-------------
  public Interval(Task owner, LocalDateTime begin, LocalDateTime end, Duration duration) {
    this.owner = owner;
    this.initialTime = begin;
    this.endTime = end;
    this.time = duration;
  }

  public Interval(LocalDateTime begin, LocalDateTime end, Duration duration) {
    this.initialTime = begin;
    this.endTime = end;
    this.time = duration;
  }

  public Interval(LocalDateTime begin, Task owner) {
    this.owner = owner;
    this.initialTime = begin;
  }

  public Interval(Task owner) {
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
  //endregion

  //region -------------MÉTODOS-------------
  private void printer() {
    String message = String.format("%-17s", "Interval:")
            + String.format("%-19s", "")
            + String.format("%-25s", initialTime.format(timeFormatter))
            + String.format("%-25s", endTime.format(timeFormatter))
            + String.format("%-18s", time.getSeconds());
    logger.info(marker, message);
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
