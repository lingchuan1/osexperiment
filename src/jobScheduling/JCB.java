package jobScheduling;

public class JCB {
    private String name;            //作业名
    private int submitTime;         //作业提交时间
    private int allNeedTime;        //作业供需运行时间
    private JcbStatus status;       //作业状态
    private String resource;          //作业所需资源

    private WorkTime workTime;      //时间信息
    private double priority;        //优先权

    public JCB(String name, int submitTime, int allNeedTime, JcbStatus status, String resource) {
        this.name = name;
        this.submitTime = submitTime;
        this.allNeedTime = allNeedTime;
        this.status = status;
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(int submitTime) {
        this.submitTime = submitTime;
    }

    public int getAllNeedTime() {
        return allNeedTime;
    }

    public void setAllNeedTime(int allNeedTime) {
        this.allNeedTime = allNeedTime;
    }

    public JcbStatus getStatus() {
        return status;
    }

    public void setStatus(JcbStatus status) {
        this.status = status;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public WorkTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(WorkTime workTime) {
        this.workTime = workTime;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }
}
