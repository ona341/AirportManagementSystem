package Entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DailyTasks {
    private StringProperty employeeId;
    private StringProperty from;
    private StringProperty to;
    private StringProperty tasks;


    public DailyTasks(String employeeId, String from, String to, String tasks) {
        this.employeeId = new SimpleStringProperty(employeeId);
        this.from = new SimpleStringProperty(from);
        this.to = new SimpleStringProperty(to);
        this.tasks = new SimpleStringProperty(tasks);
    }

    public String getFrom() { return from.get(); }

    public StringProperty fromProperty() { return from; }

    public void setfrom(String from) { this.from.set(from); }


    public String getTo() { return to.get(); }

    public StringProperty toProperty() { return to; }

    public void setTo(String to) { this.to.set(to); }


    public String getTasks() { return tasks.get(); }

    public StringProperty tasksProperty() { return tasks; }

    public void setTasks(String tasks) { this.tasks.set(tasks); }

    public String getEmployeeId() { return employeeId.get(); }

    public StringProperty employeeIdProperty() { return employeeId; }

    public void setEmployeeId(String tasks) { this.employeeId.set(tasks); }
}
