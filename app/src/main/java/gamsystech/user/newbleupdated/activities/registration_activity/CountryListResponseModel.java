package gamsystech.user.newbleupdated.activities.registration_activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryListResponseModel {

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
    private List<StatesList> statesList = null;
    @SerializedName("CitiesList")
    @Expose
    private List<CitiesList> citiesList;

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

    public List<StatesList> getStatesList() {
        return statesList;
    }

    public void setStatesList(List<StatesList> statesList) {
        this.statesList = statesList;
    }

    public List<CitiesList> getCitiesList() {
        return citiesList;
    }

    public void setCitiesList(List<CitiesList> citiesList) {
        this.citiesList = citiesList;
    }

    static class StatesList {

        @SerializedName("StateId")
        @Expose
        private Integer stateId;
        @SerializedName("StateName")
        @Expose
        private String stateName;
        @SerializedName("CountryId")
        @Expose
        private Integer countryId;

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public Integer getCountryId() {
            return countryId;
        }

        public void setCountryId(Integer countryId) {
            this.countryId = countryId;
        }

    }

    static class CitiesList {

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
