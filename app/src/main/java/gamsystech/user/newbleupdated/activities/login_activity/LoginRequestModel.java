package gamsystech.user.newbleupdated.activities.login_activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequestModel {
    @SerializedName("DeviceId")
    @Expose
    private String DeviceId;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("Pin")
    @Expose
    private String pin;


    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

}
