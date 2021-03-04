package loginapp;

public enum option {
    Manager, Passenger;

    public String value(){
        return name();
    }

    public static option fromvalue(String v){
        return valueOf(v);

    }
}
