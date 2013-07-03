package cn.com.jnpc.meeting.bean;

import java.io.Serializable;

public class MeetingRoom implements Serializable {

    private static final long serialVersionUID = 1L;
    private String            id;
    private String            building;
    private String            room;
    private String            capacity;
    private String            remark;
    private String            deleted;
    private String            type;                 // 1:会议室;2:培训教室
    private String            isFree;               // 是否可用;1:可用；0：不可用；

    public MeetingRoom() {
        super();
    }

    public MeetingRoom(String id, String building, String room, String capacity, String remark,
            String type) {
        super();
        this.id = id;
        this.building = building;
        this.room = room;
        this.capacity = capacity;
        this.remark = remark;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }
}
