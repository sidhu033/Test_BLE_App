package gamsystech.user.newbleupdated.DatabaseHelper.localstorage;

public class SharedInstance {
    private static final SharedInstance ourInstance = new SharedInstance();

    public static SharedInstance getInstance() {
        return ourInstance;
    }

    private SharedInstance() {
    }

}
