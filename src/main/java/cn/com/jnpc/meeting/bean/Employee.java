package cn.com.jnpc.meeting.bean;

/**
 * 员工信息
 */
public class Employee implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String            id;
    private String            name;
    private String            enname;
    private String            org;
    private String            orgname;

    public Employee() {
        super ();
    }

    public Employee(String id, String name, String enname, String org, String orgname) {
        super ();
        this.id = id;
        this.name = name;
        this.enname = enname;
        this.org = org;
        this.orgname = orgname;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEnname(){
        return enname;
    }

    public void setEnname(String enname){
        this.enname = enname;
    }

    public String getOrg(){
        return org;
    }

    public void setOrg(String org){
        this.org = org;
    }

    public String getOrgname(){
        return orgname;
    }

    public void setOrgname(String orgname){
        this.orgname = orgname;
    }

}
