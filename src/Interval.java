import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
    //region -------------ATRIBUTOS-------------
    private LocalDateTime initialTime;
    private LocalDateTime endTime;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Task owner;
    private Duration time = Duration.ZERO;
    //endregion

    //region -------------CONSTRUCTORES-------------
    public Interval(Task owner, LocalDateTime begin,LocalDateTime end, Duration duration){
        this.owner = owner;
        this.initialTime = begin;
        this.endTime = end;
        this.time = duration;
    }

    public Interval(LocalDateTime begin,LocalDateTime end, Duration duration){
        this.initialTime = begin;
        this.endTime = end;
        this.time = duration;
    }

    public Interval(LocalDateTime begin, Task owner){
        this.owner = owner;
        this.initialTime = begin;
    }

    public Interval(Task owner){
        this.owner = owner;
        this.initialTime = LocalDateTime.now();
    }
    //endregion

    //region -------------GETS-------------
    public LocalDateTime getInitialTime() { return initialTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public Task getOwner() { return owner; }
    public void setOwner(Task t){
        this.owner = t;
    }
    public Duration getTime() { return time; }
    //endregion

    //region -------------MÉTODOS-------------
    private void printer(){
        System.out.format("%-16s %-19s %-25s %-25s %-10s \n", "Interval:", "",
                initialTime.format(timeFormatter), endTime.format(timeFormatter), time.getSeconds());
    }
    @Override
    public void update(Observable o, Object arg) {
        this.time = this.time.plusSeconds(Clock.getPeriodo());
        this.endTime = (LocalDateTime)arg;
        printer();
        this.owner.update(this.endTime);
    }
    //endregion
}
