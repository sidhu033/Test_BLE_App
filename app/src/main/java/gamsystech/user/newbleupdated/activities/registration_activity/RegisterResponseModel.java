package gamsystech.user.newbleupdated.activities.registration_activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponseModel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("isSucess")
    @Expose
    private Boolean isSucess;
    @SerializedName("redoxerUserDetailsModel")
    @Expose
    private RedoxerUserDetailsModel redoxerUserDetailsModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Boolean getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(Boolean isSucess) {
        this.isSucess = isSucess;
    }

    public RedoxerUserDetailsModel getRedoxerUserDetailsModel() {
        return redoxerUserDetailsModel;
    }

    public void setRedoxerUserDetailsModel(RedoxerUserDetailsModel redoxerUserDetailsModel) {
        this.redoxerUserDetailsModel = redoxerUserDetailsModel;
    }

    /*response model for userid*/
    public class RedoxerUserDetailsModel {

        @SerializedName("userId")
        @Expose
        private Integer userId;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }
}
