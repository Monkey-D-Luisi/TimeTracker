

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public abstract class Component {
  //region -------------ATRIBUTOS-------------
  protected String compName = null;
    protected Project father = null;
    protected LocalDateTime startDate = null;
    protected LocalDateTime endDate = null;
    protected ArrayList<String> tagList = new ArrayList<>();
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Duration time = Duration.ZERO;

    protected Logger logger = LoggerFactory.getLogger(Component.class);

    protected Marker marker = MarkerFactory.getMarker("Milestone1");
    //endregion

    //region -------------CONSTRUCTORES-------------
    public Component (String name, Project father, LocalDateTime start, LocalDateTime end, ArrayList<String> tags, Duration time){
        this.compName = name;
        this.startDate = start;
        this.endDate = end;
        this.tagList = tags;
        this.father = father;
        this.time = time;
    }

    public Component (String name, LocalDateTime start, LocalDateTime end, ArrayList<String> tags, Duration time){
        this.compName = name;
        this.startDate = start;
        this.endDate = end;
        this.tagList = tags;
        this.time = time;
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

    public Component (String name){
        this.compName = name;
    }
    //endregion

    //region -------------GET Y SET-------------
    public String getCompName(){ return this.compName; }
    public Project getFather() { return this.father; }

    public LocalDateTime getStartDate(){ return startDate; }
    public void setStartDate(LocalDateTime startDate){
        this.startDate = startDate;
        if(this.father != null && this.father.getStartDate() == null)
            this.father.setStartDate(startDate);
    }

    public LocalDateTime getEndDate(){ return endDate; }
    public void setEndDate(LocalDateTime endDate){
        this.endDate = endDate;
        if(this.father != null)
            this.father.setEndDate(endDate);
    }

    public Duration getTime(){ return this.time; }

    public ArrayList<String> getTagList(){ return tagList; }
    //endregion

    //region -------------MÉTODOS-------------
    public void update(LocalDateTime endTime){
        this.endDate = endTime;
        this.time = this.time.plusSeconds(Clock.getPeriodo());
        printer();
        if(this.father != null) {
            this.father.update(this.endDate);
        }
    }

    public void printer(){
        logger.info(marker,("%-16s %-19s %-25s %-25s %-10s \n"+ "activity:"+ this.getCompName()+
                this.getStartDate().format(timeFormatter)+this.getEndDate().format(timeFormatter)+ this.getTime().getSeconds()));
    }

    public abstract void accept(Visitor v);
    //endregion
}
