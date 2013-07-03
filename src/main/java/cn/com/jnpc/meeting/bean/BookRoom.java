package cn.com.jnpc.meeting.bean;

import java.util.Date;

public class BookRoom implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String            id;
    private String            roomid;
    private String            meetingid;
    private String            userid;
    private String            org;
    private Date              starttime;
    private Date              endTime;
    private String            stauts;

    public BookRoom() {
        super();
    }

    public BookRoom(String id, String roomid, String meetingid, String userid, String org, Date starttime,
            Date endTime, String stauts) {
        super();
        this.id = id;
        this.roomid = roomid;
        this.meetingid = meetingid;
        this.userid = userid;
        this.org = org;
        this.starttime = starttime;
        this.endTime = endTime;
        this.stauts = stauts;
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

    public String getMeetingid() {
        return meetingid;
    }

    public void setMeetingid(String meetingid) {
        this.meetingid = meetingid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }
}
