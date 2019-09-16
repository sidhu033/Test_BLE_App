package gamsystech.user.newbleupdated.DatabaseHelper.localstorage;

import gamsystech.user.newbleupdated.activities.registration_activity.RegisterRequestModel;

public class SharedPreferenceManager {
        private static final String TAG = SharedPreferenceManager.class.getSimpleName();
    private static final String KEY_USER_LOGGED = "USER_LOGGED";
    private static final String KEY_USER_ID = "SALE_POINT_ID";
    private static final String KEY_STUDENT_BACK = "KEY_STUDENT_BACK";
    private static final String KEY_REGISTER_MODEL = "REGISTER_MODEL";

    /*============================================================================================*/

    /**
     * check user is logged or not
     *
     * @param b
     */
    public static void setUserLogged(boolean b) {
        SharedPreference.getInstance().save(KEY_USER_LOGGED, b);
    }

    /**
     * check user is logged or not
     */
    public static boolean getUserLogged() {
        return SharedPreference.getInstance().get(KEY_USER_LOGGED, false);
    }

    /**
     * save sale point id here
     *
     * @param salePointName
     */
    public static void setUserId(String salePointName) {
        SharedPreference.getInstance().save(KEY_USER_ID, salePointName);
    }

    /**
     * get sale point id here
     */
    public static String getUserId() {
        return SharedPreference.getInstance().get(KEY_USER_ID);
    }

    public static void removeAll() {
        SharedPreference.getInstance().deleteAll();
    }

    public static void saveRegisterRequestModel(RegisterRequestModel registerRequestModel) {
        SharedPreference.getInstance().save(KEY_REGISTER_MODEL, registerRequestModel);
    }

    public static RegisterRequestModel getRegisterRequestModel() {
       return SharedPreference.getInstance().get(KEY_REGISTER_MODEL);
    }
}