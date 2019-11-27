package jobScheduling;

public class WorkTime {
    private int startTime;   //开始时间
    private int finishTime;  //完成时间
    private double Ti;       //周转时间
    private double Wi;       //带权周转时间

    public WorkTime(int startTime, int finishTime, double ti, double wi) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        Ti = ti;
        Wi = wi;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public double getTi() {
        return Ti;
    }

    public void setTi(double ti) {
        Ti = ti;
    }

    public double getWi() {
        return Wi;
    }

    public void setWi(double wi) {
        Wi = wi;
    }
}
