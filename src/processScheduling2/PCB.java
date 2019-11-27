package processScheduling2;

/**
 * 进程控制块
 */
public class PCB implements Comparable<PCB>{
    private String name;            //进程名
    private int arriveTime;         //到达时间
    private int allNeedTime;        //需要运行时间
    private int useTime;            //已用CPU时间
    private int stillNeedTime;      //还需要运行时间
    private ProcessStatus status;   //进程状态

    public PCB(String name, int arriveTime, int allNeedTime,int stillNeedTime, int useTime, ProcessStatus status) {
        this.name = name;
        this.arriveTime = arriveTime;
        this.allNeedTime = allNeedTime;
        this.stillNeedTime = stillNeedTime;
        this.useTime = useTime;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(int arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getAllNeedTime() {
        return allNeedTime;
    }

    public void setAllNeedTime(int allNeedTime) {
        this.allNeedTime = allNeedTime;
    }

    public int getStillNeedTime(){
        return stillNeedTime;
    }

    public void setStillNeedTime(int stillNeedTime){
        this.stillNeedTime = stillNeedTime;
    }

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    @Override
    public int compareTo(PCB o) {
        return this.getAllNeedTime() - o.getAllNeedTime();
    }
}
