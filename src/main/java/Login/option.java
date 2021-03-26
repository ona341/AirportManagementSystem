package Login;

public enum option {
    AIRPORTMANAGER("Airport Manager"), AIRPORTEMPLOYEE("Airport Employee"), AIRLINEEMPLOYEE("Airline Employee"),
    PASSENGER("Passenger"), ADMIN("Admin");

    private final String toString;

    option(String toString) {
        this.toString = toString;
    }

    public String toString(){
        return toString;
    }

    public static option fromvalue(String v){
        return valueOf(v);

    }
}
