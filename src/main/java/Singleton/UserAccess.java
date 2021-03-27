package Singleton;

import Entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


/**
 * A list of employees using the singleton pattern
 */
public class UserAccess {

    /**
     * The main list of users
     */
    private static ObservableList<User> users;
    /**
     * A searchable copy of the list
     */
    private static FilteredList<User> searchedUsers;

    private UserAccess() {}

    /**
     * Get the main list
     */
    public static ObservableList<User> getInstance() {
        if (users == null) initialize();
        return users;
    }

    /**
     * Get the searchable list
     * THIS LIST IS IMMUTABLE
     */
    public static FilteredList<User> getSearchInstance() {
        if (searchedUsers == null) searchedUsers = new FilteredList<User>(getInstance());
        return searchedUsers;
    }

    /**
     *
     */
    private static void initialize() {
        if (users == null) {
            users = FXCollections.observableArrayList();
        }
    }
}
