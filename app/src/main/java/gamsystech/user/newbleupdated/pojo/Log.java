package gamsystech.user.newbleupdated.pojo;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable
{

    String treatmentStartTimeStamp;
    int  id,leftHandSystolic, leftHandDiastolic,leftHandPulse,rightHandSystolic,rightHandDiastolic,rightHandPulse;
    String mob;


    public Log()
    {
        this.treatmentStartTimeStamp = treatmentStartTimeStamp;
        this.leftHandSystolic = leftHandSystolic;
        this.id = id;
        this.leftHandDiastolic = leftHandDiastolic;
        this.leftHandPulse = leftHandPulse;
        this.rightHandSystolic = rightHandSystolic;
        this.rightHandDiastolic = rightHandDiastolic;
        this.rightHandPulse = rightHandPulse;
        this.mob=mob;
    }

    public Log(String s, int i, String toString, String string, int parseInt, String s1){
        this.treatmentStartTimeStamp = treatmentStartTimeStamp;
        this.leftHandSystolic = leftHandSystolic;

        this.leftHandDiastolic = leftHandDiastolic;
        this.leftHandPulse = leftHandPulse;
        this.rightHandSystolic = rightHandSystolic;
        this.rightHandDiastolic = rightHandDiastolic;
        this.rightHandPulse = rightHandPulse;
    }

    public String getTreatmentStartTimeStamp() {
        return treatmentStartTimeStamp;
    }

    public void setTreatmentStartTimeStamp(String treatmentStartTimeStamp) {
        this.treatmentStartTimeStamp = treatmentStartTimeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLeftHandSystolic() {
        return leftHandSystolic;
    }

    public void setLeftHandSystolic(int leftHandSystolic) {
        this.leftHandSystolic = leftHandSystolic;
    }

    public int getLeftHandDiastolic() {
        return leftHandDiastolic;
    }

    public void setLeftHandDiastolic(int leftHandDiastolic) {
        this.leftHandDiastolic = leftHandDiastolic;
    }

    public int getLeftHandPulse() {
        return leftHandPulse;
    }

    public void setLeftHandPulse(int leftHandPulse) {
        this.leftHandPulse = leftHandPulse;
    }

    public int getRightHandSystolic() {
        return rightHandSystolic;
    }

    public void setRightHandSystolic(int rightHandSystolic) {
        this.rightHandSystolic = rightHandSystolic;
    }

    public int getRightHandDiastolic() {
        return rightHandDiastolic;
    }

    public void setRightHandDiastolic(int rightHandDiastolic) {
        this.rightHandDiastolic = rightHandDiastolic;
    }

    public int getRightHandPulse() {
        return rightHandPulse;
    }

    public void setRightHandPulse(int rightHandPulse) {
        this.rightHandPulse = rightHandPulse;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public static void e(String s, String db_path) {
    }
}

