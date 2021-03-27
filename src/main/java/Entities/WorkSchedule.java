package Entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WorkSchedule {

    /** Sunday schedule */
    private StringProperty sunday;

    /** Monday schedule */
    private StringProperty monday;

    /** Tuesday schedule */
    private StringProperty tuesday;

    /** Wednesday schedule */
    private StringProperty wednesday;

    /** Thursday schedule */
    private StringProperty thursday;

    /** Friday schedule */
    private StringProperty friday;

    /** Saturday schedule */
    private StringProperty saturday;

    /**
     * Initialize a work schedule object instance for an employee.
     * @param sun Sunday schedule
     * @param mon Monday schedule
     * @param tues Tuesday schedule
     * @param wed Wednesday schedule
     * @param thur Thursday  schedule
     * @param fri Friday schedule
     * @param sat Saturday schedule
     */
    public WorkSchedule(String sun, String mon, String tues, String wed, String thur, String fri, String sat) {
        this.sunday = new SimpleStringProperty(sun);
        this.monday = new SimpleStringProperty(mon);
        this.tuesday = new SimpleStringProperty(tues);
        this.wednesday = new SimpleStringProperty(wed);
        this.thursday = new SimpleStringProperty(thur);
        this.friday = new SimpleStringProperty(fri);
        this.saturday = new SimpleStringProperty(sat);
    }

    /***
     * Setters and getters for Sunday schedule.
     */
    public String getSunday() { return sunday.get(); }

    public StringProperty sundayProperty() { return sunday; }

    public void setSunday(String sun) { this.sunday.set(sun); }

    /***
     * Setters and getters for Monday schedule.
     */
    public String getMonday() { return monday.get(); }

    public StringProperty mondayProperty() { return monday; }

    public void setMonday(String mon) { this.monday.set(mon); }

    /***
     * Setters and getters for Tuesday schedule.
     */
    public String getTuesday() { return tuesday.get(); }

    public StringProperty tuesdayProperty() { return tuesday; }

    public void setTuesday(String tues) { this.tuesday.set(tues); }

    /***
     * Setters and getters for Wednesday schedule.
     */
    public String getWednesday() { return wednesday.get(); }

    public StringProperty wednesdayProperty() { return wednesday; }

    public void setWednesday(String wed) { this.wednesday.set(wed); }

    /***
     * Setters and getters for Thursday schedule.
     */
    public String getThursday() { return thursday.get(); }

    public StringProperty thursdayProperty() { return thursday; }

    public void setThursday(String thur) { this.thursday.set(thur); }

    /***
     * Setters and getters for Friday schedule.
     */
    public String getFriday() { return friday.get(); }

    public StringProperty fridayProperty() { return friday; }

    public void setFriday(String fri) { this.friday.set(fri); }

    /***
     * Setters and getters for Saturday schedule.
     */
    public String getSaturday() { return saturday.get(); }

    public StringProperty saturdayProperty() { return saturday; }

    public void setSaturday(String sat) { this.saturday.set(sat); }
}
