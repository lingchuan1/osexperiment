package processScheduling;

public class Process implements Comparable<Process>{
    private String name;
    private int priority;
    private int arriveTime;
    private int needTime;
    private ProcessStatus status;
    private int beginTime;
    private int finishTime;

    public Process(String name, int priority, int arriveTime, int needTime, ProcessStatus status) {
        this.name = name;
        this.priority = priority;
        this.arriveTime = arriveTime;
        this.needTime = needTime;
        this.status = status;
        this.beginTime = -1;
        this.finishTime = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(int arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getNeedTime() {
        return needTime;
    }

    public void setNeedTime(int needTime) {
        this.needTime = needTime;
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "Process{" +
                "name=" + name +
                ", priority=" + priority +
                ", arriveTime=" + arriveTime +
                ", needTime=" + needTime +
                ", status=" + status +
                ", beginTime=" + beginTime +
                ", finishTime=" + finishTime +
                '}';
    }

    @Override
    public int compareTo(Process o) {
        return this.getFinishTime() - o.getFinishTime();
    }
}
