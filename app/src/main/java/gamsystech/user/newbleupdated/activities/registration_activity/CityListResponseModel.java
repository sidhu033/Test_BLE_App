package gamsystech.user.newbleupdated.activities.registration_activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityListResponseModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("isSucess")
    @Expose
    private Boolean isSucess;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Getcountries")
    @Expose
    private Object getcountries;
    @SerializedName("StatesList")
    @Expose
    private Object statesList;
    @SerializedName("CitiesList")
    @Expose
    private List<CitiesList> citiesList = null;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getGetcountries() {
        return getcountries;
    }

    public void setGetcountries(Object getcountries) {
        this.getcountries = getcountries;
    }

    public Object getStatesList() {
        return statesList;
    }

    public void setStatesList(Object statesList) {
        this.statesList = statesList;
    }

    public List<CitiesList> getCitiesList() {
        return citiesList;
    }

    public void setCitiesList(List<CitiesList> citiesList) {
        this.citiesList = citiesList;
    }


    public class CitiesList {

        @SerializedName("CityId")
        @Expose
        private Integer cityId;
        @SerializedName("CityName")
        @Expose
        private String cityName;
        @SerializedName("StateId")
        @Expose
        private Integer stateId;

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }


    }
}