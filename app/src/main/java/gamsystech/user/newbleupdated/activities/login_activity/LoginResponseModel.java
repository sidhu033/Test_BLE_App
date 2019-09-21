package gamsystech.user.newbleupdated.activities.login_activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("isSucess")
    @Expose
    private Boolean isSucess;
    @SerializedName("token")
    @Expose
    private Object token;
    @SerializedName("userLoginResponseModel")
    @Expose
    private UserLoginResponseModel userLoginResponseModel;

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

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public UserLoginResponseModel getUserLoginResponseModel() {
        return userLoginResponseModel;
    }

    public void setUserLoginResponseModel(UserLoginResponseModel userLoginResponseModel) {
        this.userLoginResponseModel = userLoginResponseModel;
    }

    public class UserLoginResponseModel {

        @SerializedName("userId")
        @Expose
        private Integer userId;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("isFirstTimeLogin")
        @Expose
        private Boolean isFirstTimeLogin;
        @SerializedName("isHavingProduct")
        @Expose
        private Boolean isHavingProduct;
        @SerializedName("isProductConfigured")
        @Expose
        private Boolean isProductConfigured;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Boolean getIsFirstTimeLogin() {
            return isFirstTimeLogin;
        }

        public void setIsFirstTimeLogin(Boolean isFirstTimeLogin) {
            this.isFirstTimeLogin = isFirstTimeLogin;
        }

        public Boolean getIsHavingProduct() {
            return isHavingProduct;
        }

        public void setIsHavingProduct(Boolean isHavingProduct) {
            this.isHavingProduct = isHavingProduct;
        }

        public Boolean getIsProductConfigured() {
            return isProductConfigured;
        }

        public void setIsProductConfigured(Boolean isProductConfigured) {
            this.isProductConfigured = isProductConfigured;
        }
    }
}
