package processScheduling2;

import java.util.*;

public class Main {
    /**
     * 短进程优先调度+轮转调度，时间片大小为1
     *
     * @param args
     */
    public static void main(String[] args) {
        List<PCB> processList = new LinkedList<>();
        System.out.print("请输入要调度的进程数量：");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        for (int i = 0; i < num; i++) {
            System.out.print("第" + (i + 1) + "个进程名：");
            in = new Scanner(System.in);
            String name = in.nextLine();
            System.out.print("到达时间：");
            in = new Scanner(System.in);
            int arriveTime = in.nextInt();
            System.out.print("需要运行时间：");
            in = new Scanner(System.in);
            int allNeedTime = in.nextInt();
            System.out.println("*****************");
            PCB pcb = new PCB(name, arriveTime, allNeedTime, allNeedTime, 0, ProcessStatus.WAIT);
            processList.add(pcb);
        }
        Collections.sort(processList);//进程按照执行时间由小到大排序
        int curTime = 0;
        while (true) {
            boolean isFinished = true;//设置结束标志位
            for (int i = 0; i < processList.size(); i++) {
                if (processList.get(i).getArriveTime() <= curTime && processList.get(i).getStatus().equals(ProcessStatus.WAIT)) {
                    int cur = i;
                    PCB pcb = processList.get(cur);
                    //进程调度
                    pcb.setStatus(ProcessStatus.RUNNING);
                    curTime++;
                    //打印输出
                    System.out.println("****第" + curTime + "次调度");
                    System.out.println("****当前正在运行的进程是：" + pcb.getName());
                    System.out.println("进程名     " + "状态     " + "运行需要时间     " + "已经占用CPU时间");
                    System.out.println(pcb.getName() + "        " + pcb.getStatus() + "       " + pcb.getAllNeedTime() + "             " + pcb.getUseTime());
                    System.out.println("****当前就绪队列的进程");
                    System.out.println("进程名     " + "状态     " + "运行需要时间      " + "已经占用CPU时间");
                    for (PCB pcb1 : processList) {
                        if (pcb1.getArriveTime() <= curTime && pcb1.getStatus().equals(ProcessStatus.WAIT)) {
                            isFinished = false;
                            System.out.println(pcb1.getName() + "       " + pcb1.getStatus() + "      " + pcb1.getAllNeedTime() + "            " + pcb1.getUseTime());
                        }
                    }
                    //进程状态改变
                    pcb.setUseTime(pcb.getUseTime() + 1);
                    if (pcb.getUseTime() >= pcb.getAllNeedTime()) {
                        pcb.setStatus(ProcessStatus.FINISHED);
                        System.out.println("进程" + pcb.getName() + "已完成");
                    } else {
                        isFinished = false;
                        pcb.setStatus(ProcessStatus.WAIT);
                    }
                    System.out.println();
                }
            }
            if (isFinished)
                break;
        }
    }
}
