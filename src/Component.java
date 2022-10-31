import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

public abstract class Component {
    //region -------------ATRIBUTOS-------------
    protected String compName = null;
    protected Project father = null;
    protected LocalDateTime startDate = null;
    protected LocalDateTime endDate = null;
    protected ArrayList<String> tagList = new ArrayList<>();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Duration time = Duration.ZERO;
    //endregion

    //region -------------CONSTRUCTORES-------------
    public Component (String name, LocalDateTime start, LocalDateTime end, ArrayList<String> tags){
        this.compName = name;
        this.startDate = start;
        this.endDate = end;
        this.tagList = tags;
    }

    public Component(String compName, Project father, ArrayList<String> tagList) {
        this.compName = compName;
        this.father = father;
        this.tagList = tagList;
    }

    public Component(String compName, Project father) {
        this.compName = compName;
        this.father = father;
    }

    public Component (String name, ArrayList<String> tags){
        this.compName = name;
        this.tagList = tags;
    }
    public Component (String name){
        this.compName = name;
    }
    //endregion

    //region -------------GET Y SET-------------
    public String getCompName(){ return this.compName; }

    public LocalDateTime getStartDate(){ return startDate; }
    public void setStartDate(LocalDateTime startDate){ this.startDate = startDate; }
    public LocalDateTime getEndDate(){ return endDate; }
    public void setEndDate(LocalDateTime endDate){ this.endDate = endDate; }

    public ArrayList<String> getTagList(){ return tagList; }
    //endregion

    public void update(Duration duration, LocalDateTime endTime){
        this.time = time.plus(duration);
        this.endDate = endTime;
        if(this.father != null)
            this.father.update(this.time, this.endDate);
        printer();
    }
    public void accept(Visitor v){
        //void
    }

    public void printer(){
        System.out.format("%-16s %-19s %-25s %-25s %-10s \n", this.getType(), compName,
            startDate.format(timeFormatter), endDate.format(timeFormatter), time.getSeconds());
    }

    public String getType() {
        return "Component";
    }
}
