package gamsystech.user.newbleupdated.model;

public class TreatmentPin
{
    String Pin;

    public TreatmentPin(String pin)
    {
        Pin = pin;
    }

    public String getPin() {
        return Pin;
    }

    public void setPin(String pin) {
        Pin = pin;
    }

    @Override
    public String toString()
    {
        return "TreatmentPin{" +
                "Pin='" + Pin + '\'' +
                '}';
    }
}
