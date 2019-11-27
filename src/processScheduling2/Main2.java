package processScheduling2;

import java.util.*;

/**
 * aa 0 5
 * bb 0 4
 * cc 2 1
 */
public class Main2 {
    /**
     * 短进程优先调度（可抢占）
     * @param args
     */
    public static void main(String[] args) {
        List<PCB> processList = new LinkedList<>();
        System.out.print("请输入要调度的进程数量：");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        for (int i = 0; i < num; i++) {
            System.out.print("第" + (i + 1) + "个进程名： ");
            in = new Scanner(System.in);
            String name = in.nextLine();
            System.out.print("到达时间：");
            in = new Scanner(System.in);
            int arriveTime = in.nextInt();
            System.out.print("需要运行时间：");
            in = new Scanner(System.in);
            int allNeedTime = in.nextInt();
            System.out.println("*****************");
            PCB pcb = new PCB(name, arriveTime, allNeedTime, allNeedTime,0, ProcessStatus.WAIT);
            processList.add(pcb);
        }
        int curTime = 0;
        //用count记录进程总数量，用来在循环中作为调度终止条件
        int count = processList.size();
        //使用优先队列作为就绪队列的数据结构，排序规则是根据进程还需要CPU时间由小到大排序
        PriorityQueue<PCB> queue = new PriorityQueue<>(new Comparator<PCB>() {
            @Override
            public int compare(PCB o1, PCB o2) {
                return o1.getStillNeedTime() - o2.getStillNeedTime();
            }
        });
        while(count > 0) {
            for (PCB pcb : processList) {
                //如果当前队列没有且已到达且还未完成的进程，加入就绪队列
                if (!queue.contains(pcb) && pcb.getArriveTime() <= curTime && !pcb.getStatus().equals(ProcessStatus.FINISHED))
                    queue.add(pcb);
            }
            //就绪队列不为空时执行算法
            if(!queue.isEmpty()){
                //取出就绪队列的第一个进程
                PCB cur = queue.peek();
                //设置进程状态为RUNNING
                cur.setStatus(ProcessStatus.RUNNING);
                //打印输出
                System.out.println("****当前时间是：" + curTime);
                System.out.println("****当前正在运行的进程是：" + cur.getName());
                System.out.println("进程名     "  + "状态     " + "运行需要时间     " + "已经占用CPU时间");
                System.out.println(cur.getName() + "        " + cur.getStatus() + "       " + cur.getAllNeedTime() + "             " + cur.getUseTime());
                System.out.println("****当前就绪队列的进程");
                System.out.println("进程名     "  + "状态     " + "运行需要时间      " + "已经占用CPU时间");
                for(PCB pcb:queue){
                    System.out.println(pcb.getName() + "       " + pcb.getStatus() + "      " + pcb.getAllNeedTime() + "            " + pcb.getUseTime());
                }
                //进程已经占用CPU时间 加1
                cur.setUseTime(cur.getUseTime() + 1);
                //进程还需要CPU时间 减1
                cur.setStillNeedTime(cur.getStillNeedTime() - 1);
                //判断当前进程是否已经完成，如果是，设置进程状态为FINISHED，并将count减1，若不是，设置进程状态为WAIT
                if(cur.getAllNeedTime() <= cur.getUseTime()){
                    cur.setStatus(ProcessStatus.FINISHED);
                    System.out.println("进程" + cur.getName() + "已完成");
                    queue.poll();
                    count --;
                }else {
                    cur.setStatus(ProcessStatus.WAIT);
                }
            }
            System.out.println();
            curTime ++;
        }
    }
}
