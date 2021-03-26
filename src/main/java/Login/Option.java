package Login;

public enum Option {
    AIRPORTMANAGER("Airport Manager"), AIRPORTEMPLOYEE("Airport Employee"), AIRLINEEMPLOYEE("Airline Employee"),
    PASSENGER("Passenger"), ADMIN("Admin");

    private final String toString;

    Option(String toString) {
        this.toString = toString;
    }

    public String toString(){
        return toString;
    }

    public static Option fromvalue(String v){
        return valueOf(v);

    }
}
