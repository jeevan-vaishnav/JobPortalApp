package com.example.jobportalapp.model;

public class DataModel {

    String Dtitle;
    String Ddescription;
    String Dskill;
    double Dsalary;

    String Did;
    String Ddate;

    public DataModel() {

    }

    public DataModel(String dtitle, String ddescription, String dskill, double dsalary, String did, String ddate) {
        Dtitle = dtitle;
        Ddescription = ddescription;
        Dskill = dskill;
        Dsalary = dsalary;
        Did = did;
        Ddate = ddate;
    }

    public String getDtitle() {
        return Dtitle;
    }

    public void setDtitle(String dtitle) {
        Dtitle = dtitle;
    }

    public String getDdescription() {
        return Ddescription;
    }

    public void setDdescription(String ddescription) {
        Ddescription = ddescription;
    }

    public String getDskill() {
        return Dskill;
    }

    public void setDskill(String dskill) {
        Dskill = dskill;
    }

    public double getDsalary() {
        return Dsalary;
    }

    public void setDsalary(double dsalary) {
        Dsalary = dsalary;
    }

    public String getDid() {
        return Did;
    }

    public void setDid(String did) {
        Did = did;
    }

    public String getDdate() {
        return Ddate;
    }

    public void setDdate(String ddate) {
        Ddate = ddate;
    }
}
