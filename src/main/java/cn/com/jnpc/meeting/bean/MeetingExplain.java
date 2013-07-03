package cn.com.jnpc.meeting.bean;

import java.io.Serializable;
import java.util.List;

public class MeetingExplain implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String style;
    private String unit;
    private String parentid;
    private String display;
    
    private List<MeetingExplain> child;

    public MeetingExplain() {
	super();
    }

    public MeetingExplain(String id, String name, String style, String unit,
	    String parentid,String display, List<MeetingExplain> child) {
	super();
	this.id = id;
	this.name = name;
	this.style = style;
	this.unit = unit;
	this.parentid = parentid;
	this.display = display;
	this.child = child;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public List<MeetingExplain> getChild() {
        return child;
    }

    public void setChild(List<MeetingExplain> child) {
        this.child = child;
    }

    @Override
    public String toString() {
	return "MeetingExplain [id=" + id + ", name=" + name + ", style="
		+ style + ", unit=" + unit + ", parentid=" + parentid
		+ ", child=" + child + "]";
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }


   
}
