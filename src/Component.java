import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Component {
    //region -------------ATRIBUTOS-------------
    protected String compName = null;
    protected Project father = null;
    protected LocalDateTime startDate = null;
    protected LocalDateTime endDate = null;
    protected ArrayList<String> tagList = new ArrayList<>();
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
}
