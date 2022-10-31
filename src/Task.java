import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task extends Component{
    //region -------------ATRIBUTOS-------------
    ArrayList<Interval> intervalList = new ArrayList<>();
    //endregion

    //region -------------CONSTRUCTORES-------------
    public Task(String name, LocalDateTime start, LocalDateTime end, ArrayList<String> tags) {
        super(name, start, end, tags);
    }

    public Task(String compName, Project father, ArrayList<String> tagList) {
        super(compName, father, tagList);
        father.addComponent(this);
    }

    public Task(String compName, Project father) {
        super(compName, father);
        father.addComponent(this);
    }

    public Task(String name, ArrayList<String> tags) {
        super(name, tags);
    }
    public Task(String name) {
        super(name);
    }
    //endregion

    public ArrayList<Interval> getIntervalList() {
        return intervalList;
    }

    public void addInterval(Interval i){
        intervalList.add(i);
    }


    public void start(){
        Interval currInterval = new Interval(LocalDateTime.now(), this);
        intervalList.add(currInterval);
        Clock.getInstance().addObserver(currInterval);

        if(this.getStartDate() == null ){
            this.setStartDate(LocalDateTime.now());
            //this.setEndDate(LocalDateTime.now());
        }
        if(this.getFather().getStartDate() == null){
            this.getFather().setStartDate(LocalDateTime.now());
        }
        this.updateStartDate(LocalDateTime.now());


    }

    public void stop(){
        Interval interval = intervalList.get(intervalList.size() - 1);
        this.updateEndDate(LocalDateTime.now());
        this.setEndDate(LocalDateTime.now());
        Clock.getInstance().deleteObserver(interval);;
    }


    @Override
    public String toString() {
        return "Task{" +
                "\n compName='" + compName + ',' +
                "\n father=" + father.getCompName() + ','+
                "\n tagList=" + tagList + ','+
                "\n } \n";
    }

    @Override
    public String getType() {
        return "Task";
    }
    @Override
    public void accept(Visitor v){
        v.visitTask(this);
    }
}
