package gamsystech.user.newbleupdated.DatabaseHelper.localstorage;

import com.orhanobut.hawk.Hawk;

public class SharedPreference {
    private static final SharedPreference ourInstance = new SharedPreference();

    public static SharedPreference getInstance() {
        return ourInstance;
    }

    private SharedPreference() {
    }

    /**
     * Saves any type including any collection, primitive values or custom objects
     *
     * @param key   is required to differentiate the given data
     * @param value is the data that is going to be encrypted and persisted
     *
     * @return true if the operation is successful. Any failure in any step will return false
     */
    public <T> boolean save(String key, T value) {
        return Hawk.put(key,value);
    }

    /**
     * Gets the original data along with original type by the given key.
     * This is not guaranteed operation since Hawk uses serialization. Any change in in the requested
     * data type might affect the result. It's guaranteed to return primitive types and String type
     *
     * @param key is used to get the persisted data
     *
     * @return the original object
     */
    public <T> T get(String key) {
        return Hawk.get(key);
    }

    /**
     * Gets the saved data, if it is null, default value will be returned
     *
     * @param key          is used to get the saved data
     * @param defaultValue will be return if the response is null
     *
     * @return the saved object
     */
    public <T> T get(String key, T defaultValue) {
        return Hawk.get(key,defaultValue);
    }

    /**
     * Removes the given key/value from the storage
     *
     * @param key is used for removing related data from storage
     *
     * @return true if delete is successful
     */
    public boolean delete(String key){
        return Hawk.delete(key);
    }

    /**
     * Checks the given key whether it exists or not
     *
     * @param key is the key to check
     *
     * @return true if it exists in the storage
     */
    public boolean contains(String key){
        return Hawk.contains(key);
    }

    /**
     * Clears the storage, note that crypto data won't be deleted such as salt key etc.
     * Use resetCrypto in order to deleteAll crypto information
     *
     * @return true if deleteAll is successful
     */
    public boolean deleteAll() {
        return Hawk.deleteAll();
    }

    /**
     * Size of the saved data. Each key will be counted as 1
     *
     * @return the size
     */
    public long count() {
        return Hawk.count();
    }
}
