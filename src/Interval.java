import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
    //region -------------ATRIBUTOS-------------
    private LocalDateTime initialTime;
    private LocalDateTime endTime;
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
    public LocalDateTime getInitialTime() {
        return initialTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public Task getOwner() {
        return owner;
    }
    public Duration getTime() {
        return time;
    }
    //endregion

    @Override
    public void update(Observable o, Object arg) {

    }
}
