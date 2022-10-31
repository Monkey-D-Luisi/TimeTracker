import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
    private LocalDateTime date;
    private Timer timer;
    private static final Clock clockTimer = new Clock();
    private static final int period = 2;

    private Clock(){
        timer = new Timer();
        start();
    }

    //region -------------GETS-------------
    public LocalDateTime getDate() {
        return date;
    }
    public static int getPeriod(){ return period; }
    public static Clock getInstance(){
        return clockTimer;
    }
    //endregion

    //region -------------MÉTODOS-------------
    public void start(){
        timer = new Timer();
        TimerTask taskToRepeat = new TimerTask() {
            @Override
            public void run() {
                date = LocalDateTime.now();
                setChanged();
                notifyObservers(date);
            }
        };
        timer.scheduleAtFixedRate(taskToRepeat, 0, 1000* period);
    }
    public void stop(){ timer.cancel(); }
    //endregion
}
