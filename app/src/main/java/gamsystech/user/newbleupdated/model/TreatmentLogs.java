package gamsystech.user.newbleupdated.model;

import java.util.Date;

public class TreatmentLogs
{

    private long id;
    private String IBatterlevelRH;
    private String IMAP;
    private String IsysRH;
    private String IdiaRH;
    private String IpulseRH;
    private String IHRADCRH;

    /*paramterized constructor*/

    public TreatmentLogs(long id, String IBatterlevelRH, String IMAP, String isysRH, String idiaRH, String ipulseRH, String IHRADCRH)
    {
        this.id = id;
        this.IBatterlevelRH = IBatterlevelRH;
        this.IMAP = IMAP;
        IsysRH = isysRH;
        IdiaRH = idiaRH;
        IpulseRH = ipulseRH;
        this.IHRADCRH = IHRADCRH;
    }

    public TreatmentLogs()
    {
    }
    public TreatmentLogs(long id)
    {
        this.id = id;
    }

    public String getIMAP()
    {
        return IMAP;
    }

    public void setIMAP(String IMAP) {
        this.IMAP = IMAP;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIBatterlevelRH() {
        return IBatterlevelRH;
    }

    public void setIBatterlevelRH(String IBatterlevelRH) {
        this.IBatterlevelRH = IBatterlevelRH;
    }

    public String getIsysRH() {
        return IsysRH;
    }

    public void setIsysRH(String isysRH) {
        IsysRH = isysRH;
    }

    public String getIdiaRH() {
        return IdiaRH;
    }

    public void setIdiaRH(String idiaRH) {
        IdiaRH = idiaRH;
    }

    public String getIpulseRH() {
        return IpulseRH;
    }

    public void setIpulseRH(String ipulseRH) {
        IpulseRH = ipulseRH;
    }

    public String getIHRADCRH() {
        return IHRADCRH;
    }

    public void setIHRADCRH(String IHRADCRH) {
        this.IHRADCRH = IHRADCRH;
    }

    /*to string object to string representation*/
    @Override
    public String toString()
    {
        return "TreatmentLogs{" +
                "id=" + id +
                ", IBatterlevelRH='" + IBatterlevelRH + '\'' +
                ", IsysRH='" + IsysRH + '\'' +
                ", IdiaRH='" + IdiaRH + '\'' +
                ", IpulseRH='" + IpulseRH + '\'' +
                ", IHRADCRH='" + IHRADCRH + '\'' +
                '}';
    }
}
