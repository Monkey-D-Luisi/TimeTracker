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
    //endregion

    //region -------------GETS-------------
    public LocalDateTime getInitialTime() { return initialTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public Task getOwner() { return owner; }
    public Duration getTime() { return time; }
    //endregion

    public void stopTicking(){
        Clock.getInstance().deleteObserver(this);
    }

    public void printer(){
        System.out.format("%-16s %-19s %-25s %-25s %-10s \n", "Interval:", "",
                initialTime.format(timeFormatter), endTime.format(timeFormatter), time.getSeconds());
    }
    @Override
    public void update(Observable o, Object arg) {
        this.time = time.plusSeconds(Clock.getPeriodo());
        this.endTime = (LocalDateTime)arg;
        this.owner.update(this.time, this.endTime);
        printer();
    }
}
