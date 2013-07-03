package cn.com.jnpc.meeting.bean;

public class MeetingMoney implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    
    private String id      ;
    private String org     ;
    private String orgname ;
    private String year    ;
    private String money   ;
    private String orgsort;
    public MeetingMoney() {
	super();
    }
    public MeetingMoney(String id, String org, String orgname, String year,
	    String money, String orgsort) {
	super();
	this.id = id;
	this.org = org;
	this.orgname = orgname;
	this.year = year;
	this.money = money;
	this.orgsort = orgsort;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOrg() {
        return org;
    }
    public void setOrg(String org) {
        this.org = org;
    }
    public String getOrgname() {
        return orgname;
    }
    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getMoney() {
        return money;
    }
    public void setMoney(String money) {
        this.money = money;
    }
    public String getOrgsort() {
        return orgsort;
    }
    public void setOrgsort(String orgsort) {
        this.orgsort = orgsort;
    }

}
