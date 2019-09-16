package gamsystech.user.newbleupdated.activities.readingdemo_activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TreatmentGetResponseModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("isSucess")
    @Expose
    private Boolean isSucess;
    @SerializedName("treatmentDetails")
    @Expose
    private List<TreatmentDetail> treatmentDetails = null;

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

    public List<TreatmentDetail> getTreatmentDetails() {
        return treatmentDetails;
    }

    public void setTreatmentDetails(List<TreatmentDetail> treatmentDetails) {
        this.treatmentDetails = treatmentDetails;
    }


    public class TreatmentDetail {

        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("SYSBeforeLeft")
        @Expose
        private Float sYSBeforeLeft;
        @SerializedName("SYSAfterLeft")
        @Expose
        private Float sYSAfterLeft;
        @SerializedName("DIABeforeLeft")
        @Expose
        private Float dIABeforeLeft;
        @SerializedName("DIAAfterLeft")
        @Expose
        private Float dIAAfterLeft;
        @SerializedName("PulseBeforeLeft")
        @Expose
        private Float pulseBeforeLeft;
        @SerializedName("PulseAfterLeft")
        @Expose
        private Float pulseAfterLeft;
        @SerializedName("SYSBeforeRight")
        @Expose
        private Float sYSBeforeRight;
        @SerializedName("SYSAfterRight")
        @Expose
        private Float sYSAfterRight;
        @SerializedName("DIABeforeRight")
        @Expose
        private Float dIABeforeRight;
        @SerializedName("DIAAfterRight")
        @Expose
        private Float dIAAfterRight;
        @SerializedName("PulseBeforeRight")
        @Expose
        private Float pulseBeforeRight;
        @SerializedName("PulseAfterRight")
        @Expose
        private Float pulseAfterRight;
        @SerializedName("TreatmentDate")
        @Expose
        private String treatmentDate;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Float getSYSBeforeLeft() {
            return sYSBeforeLeft;
        }

        public void setSYSBeforeLeft(Float sYSBeforeLeft) {
            this.sYSBeforeLeft = sYSBeforeLeft;
        }

        public Float getSYSAfterLeft() {
            return sYSAfterLeft;
        }

        public void setSYSAfterLeft(Float sYSAfterLeft) {
            this.sYSAfterLeft = sYSAfterLeft;
        }

        public Float getDIABeforeLeft() {
            return dIABeforeLeft;
        }

        public void setDIABeforeLeft(Float dIABeforeLeft) {
            this.dIABeforeLeft = dIABeforeLeft;
        }

        public Float getDIAAfterLeft() {
            return dIAAfterLeft;
        }

        public void setDIAAfterLeft(Float dIAAfterLeft) {
            this.dIAAfterLeft = dIAAfterLeft;
        }

        public Float getPulseBeforeLeft() {
            return pulseBeforeLeft;
        }

        public void setPulseBeforeLeft(Float pulseBeforeLeft) {
            this.pulseBeforeLeft = pulseBeforeLeft;
        }

        public Float getPulseAfterLeft() {
            return pulseAfterLeft;
        }

        public void setPulseAfterLeft(Float pulseAfterLeft) {
            this.pulseAfterLeft = pulseAfterLeft;
        }

        public Float getSYSBeforeRight() {
            return sYSBeforeRight;
        }

        public void setSYSBeforeRight(Float sYSBeforeRight) {
            this.sYSBeforeRight = sYSBeforeRight;
        }

        public Float getSYSAfterRight() {
            return sYSAfterRight;
        }

        public void setSYSAfterRight(Float sYSAfterRight) {
            this.sYSAfterRight = sYSAfterRight;
        }

        public Float getDIABeforeRight() {
            return dIABeforeRight;
        }

        public void setDIABeforeRight(Float dIABeforeRight) {
            this.dIABeforeRight = dIABeforeRight;
        }

        public Float getDIAAfterRight() {
            return dIAAfterRight;
        }

        public void setDIAAfterRight(Float dIAAfterRight) {
            this.dIAAfterRight = dIAAfterRight;
        }

        public Float getPulseBeforeRight() {
            return pulseBeforeRight;
        }

        public void setPulseBeforeRight(Float pulseBeforeRight) {
            this.pulseBeforeRight = pulseBeforeRight;
        }

        public Float getPulseAfterRight() {
            return pulseAfterRight;
        }

        public void setPulseAfterRight(Float pulseAfterRight) {
            this.pulseAfterRight = pulseAfterRight;
        }

        public String getTreatmentDate() {
            return treatmentDate;
        }

        public void setTreatmentDate(String treatmentDate) {
            this.treatmentDate = treatmentDate;
        }
    }
}
