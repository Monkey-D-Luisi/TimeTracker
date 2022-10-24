import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable {
    private LocalDateTime date;
    private Timer timer;
    private static final Clock clockTimer = new Clock();
    private static final int periodo = 2;

    private Clock(){
        timer = new Timer();
        start();
    }

    public LocalDateTime getDate() {
        return date;
    }
    public static int getPeriodo(){ return periodo; }
    public static Clock getInstance(){
        return clockTimer;
    }

    public void start(){
        TimerTask taskToRepeat = new TimerTask() {
            @Override
            public void run() {
                date = LocalDateTime.now();
                setChanged();
                notifyObservers(date);
            }
        };
        timer.scheduleAtFixedRate(taskToRepeat, 0, 1000*periodo);
    }
    public void stop(){ timer.cancel(); }
}