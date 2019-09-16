package gamsystech.user.newbleupdated.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TreatmentRequestModel implements Serializable
{



        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("SYSBeforeLeft")
        @Expose
        private Integer sYSBeforeLeft;
        @SerializedName("SYSAfterLeft")
        @Expose
        private Integer sYSAfterLeft;
        @SerializedName("DIABeforeLeft")
        @Expose
        private Integer dIABeforeLeft;
        @SerializedName("DIAAfterLeft")
        @Expose
        private Integer dIAAfterLeft;
        @SerializedName("PulseBeforeLeft")
        @Expose
        private Integer pulseBeforeLeft;
        @SerializedName("PulseAfterLeft")
        @Expose
        private Integer pulseAfterLeft;
        @SerializedName("SYSBeforeRight")
        @Expose
        private Integer sYSBeforeRight;
        @SerializedName("SYSAfterRight")
        @Expose
        private Integer sYSAfterRight;
        @SerializedName("DIABeforeRight")
        @Expose
        private Integer dIABeforeRight;
        @SerializedName("DIAAfterRight")
        @Expose
        private Integer dIAAfterRight;
        @SerializedName("PulseBeforeRight")
        @Expose
        private Integer pulseBeforeRight;
        @SerializedName("PulseAfterRight")
        @Expose
        private Integer pulseAfterRight;
        @SerializedName("TreatmentDate")
        @Expose
        private String treatmentDate;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getSYSBeforeLeft() {
            return sYSBeforeLeft;
        }

        public void setSYSBeforeLeft(Integer sYSBeforeLeft) {
            this.sYSBeforeLeft = sYSBeforeLeft;
        }

        public Integer getSYSAfterLeft() {
            return sYSAfterLeft;
        }

        public void setSYSAfterLeft(Integer sYSAfterLeft) {
            this.sYSAfterLeft = sYSAfterLeft;
        }

        public Integer getDIABeforeLeft() {
            return dIABeforeLeft;
        }

        public void setDIABeforeLeft(Integer dIABeforeLeft) {
            this.dIABeforeLeft = dIABeforeLeft;
        }

        public Integer getDIAAfterLeft() {
            return dIAAfterLeft;
        }

        public void setDIAAfterLeft(Integer dIAAfterLeft) {
            this.dIAAfterLeft = dIAAfterLeft;
        }

        public Integer getPulseBeforeLeft() {
            return pulseBeforeLeft;
        }

        public void setPulseBeforeLeft(Integer pulseBeforeLeft) {
            this.pulseBeforeLeft = pulseBeforeLeft;
        }

        public Integer getPulseAfterLeft() {
            return pulseAfterLeft;
        }

        public void setPulseAfterLeft(Integer pulseAfterLeft) {
            this.pulseAfterLeft = pulseAfterLeft;
        }

        public Integer getSYSBeforeRight() {
            return sYSBeforeRight;
        }

        public void setSYSBeforeRight(Integer sYSBeforeRight) {
            this.sYSBeforeRight = sYSBeforeRight;
        }

        public Integer getSYSAfterRight() {
            return sYSAfterRight;
        }

        public void setSYSAfterRight(Integer sYSAfterRight) {
            this.sYSAfterRight = sYSAfterRight;
        }

        public Integer getDIABeforeRight() {
            return dIABeforeRight;
        }

        public void setDIABeforeRight(Integer dIABeforeRight) {
            this.dIABeforeRight = dIABeforeRight;
        }

        public Integer getDIAAfterRight() {
            return dIAAfterRight;
        }

        public void setDIAAfterRight(Integer dIAAfterRight) {
            this.dIAAfterRight = dIAAfterRight;
        }

        public Integer getPulseBeforeRight() {
            return pulseBeforeRight;
        }

        public void setPulseBeforeRight(Integer pulseBeforeRight) {
            this.pulseBeforeRight = pulseBeforeRight;
        }

        public Integer getPulseAfterRight() {
            return pulseAfterRight;
        }

        public void setPulseAfterRight(Integer pulseAfterRight) {
            this.pulseAfterRight = pulseAfterRight;
        }

        public String getTreatmentDate() {
            return treatmentDate;
        }

        public void setTreatmentDate(String treatmentDate) {
            this.treatmentDate = treatmentDate;
        }



}

