package cn.com.jnpc.meeting.bean;

import java.io.Serializable;
import java.util.Date;

public class MeetingPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    private String            id;
    private Date              starttime;
    private Date              endtime;
    private String            content;
    private String            leader;
    private String            depart;
    private String            remark;
    private Date              creattime;
    private String            commiterid;
    private String            commitdepart;
    private String            type;
    private String            presider;
    private String            grade;
    private String            category;
    private String            fdepart;
    private String            actual_costs;
    private String            contact;
    private String            address;
    private String            contactphone;
    private String            reserve_roomid;
    private String            reserve_address;
    private String            org;
    private String            meetingid;

    public MeetingPlan() {
        super();
    }

    public MeetingPlan(String id, Date starttime, Date endtime, String content, String leader, String depart,
            String remark, Date creattime, String commiterid, String commitdepart, String type, String presider,
            String grade, String category, String fdepart, String actual_costs, String contact, String address,
            String contactphone, String reserve_roomid, String reserve_address, String org, String meetingid) {
        super();
        this.id = id;
        this.starttime = starttime;
        this.endtime = endtime;
        this.content = content;
        this.leader = leader;
        this.depart = depart;
        this.remark = remark;
        this.creattime = creattime;
        this.commiterid = commiterid;
        this.commitdepart = commitdepart;
        this.type = type;
        this.presider = presider;
        this.grade = grade;
        this.category = category;
        this.fdepart = fdepart;
        this.actual_costs = actual_costs;
        this.contact = contact;
        this.address = address;
        this.contactphone = contactphone;
        this.reserve_roomid = reserve_roomid;
        this.reserve_address = reserve_address;
        this.org = org;
        this.meetingid = meetingid;
    }

    public String getActual_costs() {
        return actual_costs;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public String getCommitdepart() {
        return commitdepart;
    }

    public String getCommiterid() {
        return commiterid;
    }

    public String getContact() {
        if (contact == null) {
            return "";
        }
        return contact;
    }

    public String getContactphone() {
        if (contactphone == null) {
            return "";
        }
        return contactphone;
    }

    public String getContent() {
        return content;
    }

    public Date getCreattime() {
        return creattime;
    }

    public String getDepart() {
        return depart;
    }

    public Date getEndtime() {
        return endtime;
    }

    public String getFdepart() {
        return fdepart;
    }

    public String getGrade() {
        return grade;
    }

    public String getId() {
        return id;
    }

    public String getLeader() {
        return leader;
    }

    public String getMeetingid() {
        return meetingid;
    }

    public String getOrg() {
        return org;
    }

    public String getPresider() {
        return presider;
    }

    public String getRemark() {
        return remark;
    }

    public String getReserve_address() {
        return reserve_address;
    }

    public String getReserve_roomid() {
        return reserve_roomid;
    }

    public Date getStarttime() {
        return starttime;
    }

    public String getType() {
        return type;
    }

    public void setActual_costs(String actual_costs) {
        this.actual_costs = actual_costs;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCommitdepart(String commitdepart) {
        this.commitdepart = commitdepart;
    }

    public void setCommiterid(String commiterid) {
        this.commiterid = commiterid;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public void setFdepart(String fdepart) {
        this.fdepart = fdepart;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public void setMeetingid(String meetingid) {
        this.meetingid = meetingid;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public void setPresider(String presider) {
        this.presider = presider;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setReserve_address(String reserve_address) {
        this.reserve_address = reserve_address;
    }

    public void setReserve_roomid(String reserve_roomid) {
        this.reserve_roomid = reserve_roomid;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public void setType(String type) {
        this.type = type;
    }
}
