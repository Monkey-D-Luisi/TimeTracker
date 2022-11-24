import java.lang.reflect.Array;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Class that implements the visitor pattern to visit components and
 * generate files with logs given a marker
 */
public class SearchByTag implements Visitor {
  private String tag = "";
  private ArrayList<Component> result = new ArrayList<>();

  protected Logger logger = LoggerFactory.getLogger(SearchByTag.class);

  protected Marker marker = MarkerFactory.getMarker("Milestone2");

  //region -------------CONSTRUCTOR-------------
  public SearchByTag(String tag) {
    this.tag = tag;
  }
  //endregion

  // region -------------GETS-------------
  public ArrayList<Component> getResult() {
    return result;
  }
  //endregion

  //region -------------MÉTODOS-------------
  public void visitProject(Project p) {
    this.search(p, tag);
  }

  public void visitTask(Task t) {
    this.search(t, tag);
  }

  public void search(Project p, String tag) {
    for (String tagItem : p.getTagList()) {
      if (tag.equalsIgnoreCase(tagItem)) {
        result.add(p);
      }
    }

    for (Component compItem : p.getCompList()) {
      compItem.accept(this);
    }
    if (p.father == null) {
      print(result, tag);
    }
  }

  public void search(Task t, String tag) {
    for (String tagItem : t.getTagList()) {
      if (tag.equalsIgnoreCase(tagItem)) {
        result.add(t);
      }
    }
  }

  private void print(ArrayList<Component> list, String tag) {

    ArrayList<String> message = new ArrayList<>();
    for (Component item : list) {
      message.add(item.getCompName());
    }
    logger.info(marker, "Searched tag: " + tag
            + String.format("%10s", "-> ") + "{}", (Object) message);
  }
  //endregion
}
