package cn.com.jnpc.meeting.bean;

import java.util.Date;

public class Meeting implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String            id;
    private String            roomid;
    private Date              starttime;
    private Date              endtime;
    private String            content;
    private String            leader;
    private String            depart;
    private String            remark;
    private Date              committime;
    private String            commiterid;
    private String            commitdepart;
    private String            approverid;
    private String            alloterid;
    private String            status;
    private String            type;
    private String            presider;
    private String            grade;
    private String            category;
    private String            fdepart;
    private String            budget;
    private String            actual_costs;
    private String            address;              // 外部会议地址
    private String            address1;
    private String            contact;
    private String            contactphone;
    private String            reserve_roomid;
    private String            reserve_address;
    private String            org;
    private String            flow;
    private String            detail;

    public Meeting() {

        super();
    }

    public Meeting(String id, String roomid, Date starttime, Date endtime, String content, String leader,
            String depart, String remark, Date committime, String commiterid, String commitdepart, String approverid,
            String alloterid, String status, String type, String presider, String grade, String category,
            String fdepart, String budget, String actual_costs, String address, String contact, String contactphone,
            String reserve_roomid, String reserve_address, String org) {

        super();
        this.id = id;
        this.roomid = roomid;
        this.starttime = starttime;
        this.endtime = endtime;
        this.content = content;
        this.leader = leader;
        this.depart = depart;
        this.remark = remark;
        this.committime = committime;
        this.commiterid = commiterid;
        this.commitdepart = commitdepart;
        this.approverid = approverid;
        this.alloterid = alloterid;
        this.status = status;
        this.type = type;
        this.presider = presider;
        this.grade = grade;
        this.category = category;
        this.fdepart = fdepart;
        this.budget = budget;
        this.actual_costs = actual_costs;
        this.address = address;
        this.contact = contact;
        this.contactphone = contactphone;
        this.reserve_roomid = reserve_roomid;
        this.reserve_address = reserve_address;
        this.org = org;
    }

    public String getAddress1() {

        return address1;
    }

    public void setAddress1(String address1) {

        this.address1 = address1;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getRoomid() {

        return roomid;
    }

    public void setRoomid(String roomid) {

        this.roomid = roomid;
    }

    public Date getStarttime() {

        return starttime;
    }

    public void setStarttime(Date starttime) {

        this.starttime = starttime;
    }

    public Date getEndtime() {

        return endtime;
    }

    public void setEndtime(Date endtime) {

        this.endtime = endtime;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public String getLeader() {

        return leader;
    }

    public void setLeader(String leader) {

        this.leader = leader;
    }

    public String getDepart() {

        return depart;
    }

    public void setDepart(String depart) {

        this.depart = depart;
    }

    public String getRemark() {

        return remark;
    }

    public void setRemark(String remark) {

        this.remark = remark;
    }

    public Date getCommittime() {

        return committime;
    }

    public void setCommittime(Date committime) {

        this.committime = committime;
    }

    public String getCommiterid() {

        return commiterid;
    }

    public void setCommiterid(String commiterid) {

        this.commiterid = commiterid;
    }

    public String getCommitdepart() {

        return commitdepart;
    }

    public void setCommitdepart(String commitdepart) {

        this.commitdepart = commitdepart;
    }

    public String getApproverid() {

        return approverid;
    }

    public void setApproverid(String approverid) {

        this.approverid = approverid;
    }

    public String getAlloterid() {

        return alloterid;
    }

    public void setAlloterid(String alloterid) {

        this.alloterid = alloterid;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getPresider() {

        return presider;
    }

    public void setPresider(String presider) {

        this.presider = presider;
    }

    public String getGrade() {

        return grade;
    }

    public void setGrade(String grade) {

        this.grade = grade;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {

        this.category = category;
    }

    public String getFdepart() {

        return fdepart;
    }

    public void setFdepart(String fdepart) {

        this.fdepart = fdepart;
    }

    public String getBudget() {

        return budget;
    }

    public void setBudget(String budget) {

        this.budget = budget;
    }

    public String getActual_costs() {

        return actual_costs;
    }

    public void setActual_costs(String actual_costs) {

        this.actual_costs = actual_costs;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public String getContact() {

        if (contact == null) {
            return "";
        }
        return contact;
    }

    public void setContact(String contact) {

        this.contact = contact;
    }

    public String getContactphone() {

        if (contactphone == null) {
            return "";
        }
        return contactphone;
    }

    public void setContactphone(String contactphone) {

        this.contactphone = contactphone;
    }

    public String getReserve_roomid() {

        return reserve_roomid;
    }

    public void setReserve_roomid(String reserve_roomid) {

        this.reserve_roomid = reserve_roomid;
    }

    public String getReserve_address() {

        return reserve_address;
    }

    public void setReserve_address(String reserve_address) {

        this.reserve_address = reserve_address;
    }

    public String getOrg() {

        return org;
    }

    public void setOrg(String org) {

        this.org = org;
    }

    public String getFlow() {

        return flow;
    }

    public void setFlow(String flow) {

        this.flow = flow;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
