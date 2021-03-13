package Entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WorkSchedule {

    private StringProperty sunday;
    private StringProperty monday;
    private StringProperty tuesday;
    private StringProperty wednesday;
    private StringProperty thursday;
    private StringProperty friday;
    private StringProperty saturday;


    public WorkSchedule(String sun, String mon, String tues, String wed, String thur, String fri, String sat) {
        this.sunday = new SimpleStringProperty(sun);
        this.monday = new SimpleStringProperty(mon);
        this.tuesday = new SimpleStringProperty(tues);
        this.wednesday = new SimpleStringProperty(wed);
        this.thursday = new SimpleStringProperty(thur);
        this.friday = new SimpleStringProperty(fri);
        this.saturday = new SimpleStringProperty(sat);
    }


    public String getSunday() { return sunday.get(); }

    public StringProperty sundayProperty() { return sunday; }

    public void setSunday(String sun) { this.sunday.set(sun); }



    public String getMonday() { return monday.get(); }

    public StringProperty mondayProperty() {
        return monday;
    }

    public void setMonday(String mon) { this.monday.set(mon); }



    public String getTuesday() { return tuesday.get(); }

    public StringProperty tuesdayProperty() {
        return tuesday;
    }

    public void setTuesday(String tues) { this.tuesday.set(tues); }



    public String getWednesday() { return wednesday.get(); }

    public StringProperty wednesdayProperty() {
        return wednesday;
    }

    public void setWednesday(String wed) { this.wednesday.set(wed); }



    public String getThursday() { return thursday.get(); }

    public StringProperty thursdayProperty() {
        return thursday;
    }

    public void setThursday(String thur) { this.thursday.set(thur); }



    public String getFriday() { return friday.get(); }

    public StringProperty fridayProperty() {
        return friday;
    }

    public void setFriday(String fri) { this.friday.set(fri); }



    public String getSaturday() { return saturday.get(); }

    public StringProperty saturdayProperty() {
        return saturday;
    }

    public void setSaturday(String sat) { this.saturday.set(sat); }
}
