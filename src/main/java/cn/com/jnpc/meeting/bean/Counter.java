package cn.com.jnpc.meeting.bean;


public class Counter {

    private String depart;     //部门
    private String curCount;   //当前月召开次数
    private String totalCount; //累计召开次数
    private String nextCount;  //计划召开次数

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getCurCount() {
        return curCount;
    }

    public void setCurCount(String curCount) {
        this.curCount = curCount;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getNextCount() {
        return nextCount;
    }

    public void setNextCount(String nextCount) {
        this.nextCount = nextCount;
    }
}
