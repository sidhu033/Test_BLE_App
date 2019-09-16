package gamsystech.user.newbleupdated.model;

public class PinModel
{
    private long setpin_id;
    private String setpin_pass;

    /*constructor for pin set*/
    public PinModel(long setpin_id, String setpin_pass)
    {
        this.setpin_id = setpin_id;
        this.setpin_pass = setpin_pass;
    }

    public long getSetpin_id() {
        return setpin_id;
    }

    public void setSetpin_id(long setpin_id) {
        this.setpin_id = setpin_id;
    }

    public String getSetpin_pass() {
        return setpin_pass;
    }

    public void setSetpin_pass(String setpin_pass) {
        this.setpin_pass = setpin_pass;
    }

    @Override
    public String toString() {
        return "PinModel{" +
                "setpin_id=" + setpin_id +
                ", setpin_pass='" + setpin_pass + '\'' +
                '}';
    }
}
