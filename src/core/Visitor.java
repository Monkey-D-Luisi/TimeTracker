package core;

public interface Visitor {
  public void visitProject(Project p);

  public void visitTask(Task t);
}
