package core;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/*
 * Clase que implmenta el patrón visitor para visitar componentes y
   así generar archivos con datos en formato json o logs en html
 */
public class JsonVisitor implements Visitor {
  //region -------------ATRIBUTOS-------------
  JSONObject proyectoActual = new JSONObject();
  JSONObject proyectoAnterior = new JSONObject();

  JSONArray proyectos = new JSONArray();
  JSONArray tareas = new JSONArray();

  protected Logger logger = LoggerFactory.getLogger(JsonVisitor.class);

  protected Marker marker = MarkerFactory.getMarker("Milestone1");
  //endregion

  //region -------------MÉTODOS-------------
  public void visitProject(Project p) {
    for (Component element : p.getCompList()) {
      element.accept(this);
    }
    createjsonproject(p);
  }

  public void visitTask(Task t) {
    createjsontask(t);
  }

  private static JSONObject createjsoncomponent(Component c) {
    JSONObject tempObject = new JSONObject();

    String father = c.getFather() != null ? c.getFather().getCompName() : null;

    tempObject.put("father", father);
    tempObject.put("startDate", c.getStartDate());
    tempObject.put("endDate", c.getEndDate());
    tempObject.put("duration", c.getTime());
    tempObject.put("tagList", c.getTagList());
    tempObject.put("name", c.getCompName());
    tempObject.put("id", c.getId());

    return tempObject;
  }

  /*
    * Llamamos al método createComponent(p) para inicializar el proyecto actual como JSONObject
    * Comprobamos que haya tareas asociadas al proyecto y en caso afirmativo las añadimos al JSON
    * Comprobamos si el proyecto actual es el primer proyecto y en ese caso miramos que el padre
      no sea nulo
      para que no haya errores al querer obtener los valores de sus parámetros.
    * Finalmente, añadimos las tareas del proyecto al JSON y establecemos el proyecto actual que
      hemos incializado
      como proyeto anterior para preparar la siguiente iteración.
  */
  private void createjsonproject(Project p) {
    proyectoActual = createjsoncomponent(p);
    proyectoActual.put("class", "Proyecto");

    if (tareas.length() != 0) {
      proyectoActual.put("componentes", tareas);
    }
    if (proyectoAnterior.length() == 0
            || (p.getFather() != null && proyectoAnterior.get("father")
            == p.getFather().getCompName())) {
      proyectos.put(proyectoActual);
    } else {
      if (tareas.length() != 0) {
        for (Object item : tareas) {
          proyectos.put(item);
        }
      }
      proyectoActual.put("componentes", proyectos);
      proyectos = new JSONArray();
      proyectos.put(proyectoActual);
    }
    tareas = new JSONArray();
    proyectoAnterior = proyectoActual;
  }

  /*
    *   Inicializamos las tareas del proyecto como un nuevo JSONObject
    *   Inicializamos el array de intervalos de la tarea como un JSONArray
    *   Recorremos el array de intervalos y generamos un JSONObject para
        cada uno de ellos, añadiendo sus datos
    *   Añadimos los intervalos generados como JSONObject al JSONArray de
        intervalos asociado a la tarea
   */
  private void createjsontask(Task t) {
    JSONObject task = new JSONObject();
    JSONArray arrayIntervalos = new JSONArray();

    task = createjsoncomponent(t);
    task.put("class", "Tarea");

    for (Interval interval : t.getIntervalList()) {
      JSONObject intervalos = new JSONObject();
      intervalos.put("startDate", interval.getInitialTime());
      intervalos.put("endDate", interval.getEndTime());
      intervalos.put("duration", interval.getTime());
      intervalos.put("id", interval.getId());
      arrayIntervalos.put(intervalos);
    }
    task.put("intervalos", arrayIntervalos);
    tareas.put(task);
  }

  public void save(String fileName) throws IOException {
    File file = new File(fileName);
    try (FileWriter writer = new FileWriter("./datos/" + file)) {
      proyectoAnterior.write(writer);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void print() {
    logger.debug(marker, proyectoAnterior.toString(4));
  }

  public Project load(String fileName) throws FileNotFoundException {
    IdManager.getInstance();
    JSONObject jobject;
    JSONTokener jtokener;
    File targetFile = new File(fileName);
    InputStream is = new FileInputStream(targetFile);
    jtokener = new JSONTokener(is);
    jobject = new JSONObject(jtokener);

    Project p = (Project) createTree(jobject, null);
    return p;
  }

  /*
    Creamos el árbol de la siguiente manera:
    * Comprobamos si el objeto tiene padre y en caso de que sí tenga:
        * Comprobamos si el objeto es una Tarea:
          * 1 Llamamos al constructor de Tarea con los parámetros adecuados
            (father, name, startDate, endDate, time, tags)
          * 2 Creamos los intervalos
          * 3 Añadimos los intervalos a la Tarea
          * 4 Devolvemos Tarea como un Componente de un Proyecto
        * En el caso de que sea un Proyecto:
          * 1 Llamamos al constructor de Proyecto con los parámetros adecuados
            (father, name, startDate, endDate, time, tags)
          * 2 De manera recursiva creamos los componentes del Proyecto
          * 3 Esto componentes se añaden al Proyecto father
          * 4 Devolvemos el Proyecto root
    * En el caso de que el father sea nulo:
      * Creamos un proyecto root
      * Creamos la lista de componentes de dicho proyecto de forma recursiva
  */
  private Component createTree(JSONObject jsonObject, Project father) {
    ArrayList<Component> components = new ArrayList<>();

    if (father != null) {
      if (jsonObject.get("class").equals("Tarea")) {
        Task task = new Task(
                    getId(jsonObject, "id"),
                    getName(jsonObject, "name"),
                    father, getDate(jsonObject, "startDate"),
                    getDate(jsonObject, "endDate"),
                    getTags(jsonObject, "tagList"),
                    getTime(jsonObject, "duration"));
        ArrayList<Interval> intervals = new ArrayList<>();

        for (Object item : jsonObject.getJSONArray("intervalos")) {
          JSONObject jsonObjectTemp = (JSONObject) item;
          Interval interval = new Interval(getId(jsonObject, "id"), task,
                            getDate(jsonObjectTemp, "startDate"),
                            getDate(jsonObjectTemp, "endDate"),
                            getTime(jsonObjectTemp, "duration"));
          intervals.add(interval);
        }

        task.setIntervalList(intervals);
        father.addComponent(task);
        return task;

      } else {
        Project project = new Project(getId(jsonObject, "id"), getName(jsonObject, "name"),
                        father, getDate(jsonObject, "startDate"),
                        getDate(jsonObject, "endDate"),
                        getTags(jsonObject, "tagList"), getTime(jsonObject, "duration"));

        JSONArray componentsList = (JSONArray) getComponents(jsonObject, "componentes");

        for (int i = 0; i < componentsList.length(); i++) {
          JSONObject jsonObjectTemp = (JSONObject) componentsList.get(i);
          Component temp = createTree(jsonObjectTemp, father);
          components.add(temp);
        }

        project.setCompList(components);
        return project;
      }
    } else {
      Project root = new Project("root");
      Project project = new Project(getId(jsonObject, "id"),
                    getName(jsonObject, "name"), getDate(jsonObject, "startDate"),
                    getDate(jsonObject, "endDate"), getTags(jsonObject, "tagList"),
                    getTime(jsonObject, "duration"));

      JSONArray componentsList = getComponents(jsonObject, "componentes");

      for (int i = 0; i < componentsList.length(); i++) {
        JSONObject jsonObjectTemp = (JSONObject) componentsList.get(i);
        Component temp = createTree(jsonObjectTemp, root);
        components.add(temp);
      }

      project.setCompList(components);
      return project;
    }
  }

  private String getName(JSONObject gsonObj, String key) {
    return gsonObj.isNull(key) ? null : String.valueOf(gsonObj.get(key));
  }

  private int getId(JSONObject gsonObj, String key) {
    return gsonObj.isNull(key) ? null : Integer.parseInt(String.valueOf(gsonObj.get(key)));
  }

  private LocalDateTime getDate(JSONObject obj, String key) {
    LocalDateTime localDateTime = null;
    if (obj.isNull(key)) {
      return localDateTime;
    } else {
      String initalDate = getName(obj, key);
      DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      localDateTime = LocalDateTime.parse(initalDate);
    }
    return localDateTime;
  }

  private JSONArray getComponents(JSONObject gsonObj, String key) {
    return gsonObj.isNull(key) ? new JSONArray() : gsonObj.getJSONArray(key);
  }

  private ArrayList<String> getTags(JSONObject gsonObj, String key) {
    ArrayList<String> tags = new ArrayList<>();
    if (gsonObj.isNull(key)) {
      return tags;
    } else {
      for (Object item : gsonObj.getJSONArray(key)) {
        tags.add((String) item);
      }
    }
    return tags;
  }

  /*
   * Transforma un valor String a Duration
   */
  private Duration getTime(JSONObject obj, String key) {
    Duration duration = null;
    if (obj.isNull(key)) {
      return duration;
    } else {
      String strDuration = (String) getName(obj, key);
      duration = Duration.parse(strDuration);
    }
    return duration;
  }
  //endregion
}
