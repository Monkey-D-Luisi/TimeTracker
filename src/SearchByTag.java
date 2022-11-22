import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.ArrayList;


public class SearchByTag implements Visitor {
  private String tag = "";
  private ArrayList<Component> result = new ArrayList<>();

  protected Logger logger = LoggerFactory.getLogger(SearchByTag.class);

  protected Marker marker = MarkerFactory.getMarker("Milestone2");

  //region -------------CONSTRUCTOR-------------
  public SearchByTag(String t) {
    this.tag = t;
  }
  //endregion

  // region -------------GETS-------------
  public ArrayList<Component> getResult() {
    return result;
  }
  //endregion

  //region -------------MÉTODOS-------------
  public void visitProject(Project p) {
    this.search(p, this.tag);
  }

  public void visitTask(Task t) {
    this.search(t, this.tag);
  }

  public ArrayList<Component> search(Project p, String tag) {
    for (String tagItem : p.getTagList()) {
      if (tag.equalsIgnoreCase(tagItem)) {
        result.add(p);
      }
    }

    for (Component compItem : p.getCompList()) {
      compItem.accept(this);
    }
    return result;
  }

  public ArrayList<Component> search(Task t, String tag) {
    for (String tagItem : t.getTagList()) {
      if (tag.equalsIgnoreCase(tagItem)) {
        result.add(t);
      }
    }
    return result;
  }

  public void print(){
    for (Component item : this.getResult()) {
      logger.info(marker,item.getCompName());
    }
  }
  //endregion
}
