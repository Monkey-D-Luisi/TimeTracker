public final class IdManager {

  public static void setComponentLastId(int componentLastId) {
    if(ComponentLastId < componentLastId) ComponentLastId = componentLastId;
  }

  public static void setIntervalLastId(int intervalLastId) {
    if(IntervalLastId < intervalLastId) IntervalLastId = intervalLastId;
  }

  protected static int ComponentLastId = 0;
  protected static int IntervalLastId = 0;
  private static IdManager Instance;

  private IdManager(int componentLastId, int intervalLastId) {
    ComponentLastId = componentLastId;
    IntervalLastId = intervalLastId;
  }

  public static IdManager getInstance(int componentLastId, int intervalLastId) {
    if(Instance == null)
      Instance = new IdManager(componentLastId, intervalLastId);
    return Instance;
  }

  public static IdManager getInstance() {
    if(Instance == null)
      Instance = new IdManager(0, 0);
    return Instance;
  }

  public static int getNewComponentId() {
    return ++ComponentLastId;
  }

  public static int getNewIntervalId() {
    return ++IntervalLastId;
  }
}
