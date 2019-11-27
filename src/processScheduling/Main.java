package processScheduling;

import java.util.*;

public class Main {

    /**
     * A 1 0 4
     * B 2 1 3
     * C 3 2 5
     * D 4 3 2
     * E 5 4 4
     */
    public static List<Process> processList = new ArrayList<>(); // 总进程
    public static int curTime = 0;
    public static int piece = 1;//时间片大小
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入进程数:");
        int num = sc.nextInt();
        for (int i = 0; i < num; i++) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请依次输入进程的name,priority,arriveTime,needTime");
            String name = scanner.next();
            int priority = scanner.nextInt();
            int arriveTime = scanner.nextInt();
            int needTime = scanner.nextInt();
            Process process = new Process(name,priority,arriveTime,needTime,ProcessStatus.READY);
            processList.add(process);
        }
        System.out.println("name" + '\t' + "priority" + '\t' + "arriveTime" + '\t' + "needTime" + '\t' + "status");
        for (int i = 0; i < processList.size(); i++) {
            System.out.println(processList.get(i).getName() + "          "
                    + processList.get(i).getPriority() + "            "
                    + processList.get(i).getArriveTime() + "          "
                    + processList.get(i).getNeedTime() + "        "
                    + processList.get(i).getStatus() );
        }
        int count = processList.size();
        Algorithm algorithm = Algorithm.SLF;
       Queue<Process> priorityQueue = null;
        switch (algorithm){
            //时间片轮转
            case RR:
                priorityQueue = new LinkedList<>();
                while(count > 0){
                    for(Process process:processList){
                        if(!priorityQueue.contains(process) && !process.getStatus().equals(ProcessStatus.FINSHED) && process.getArriveTime() <= curTime){
                            ((LinkedList<Process>) priorityQueue).addFirst(process);
                        }
                    }
                    if(!priorityQueue.isEmpty()){
                        Process process = priorityQueue.poll();
                        if(process.getBeginTime() == -1){
                            process.setBeginTime(curTime);
                        }
                        curTime = piece > process.getNeedTime() ? curTime + process.getNeedTime() : piece + curTime;
                        process.setNeedTime(process.getNeedTime() - piece);
                        if(process.getNeedTime() <= 0){
                            process.setFinishTime(curTime);
                            process.setStatus(ProcessStatus.FINSHED);
                            count --;
                        }else {
                            priorityQueue.add(process);
                        }
                    }else{
                        curTime ++;
                    }
                }
                break;
            //最高优先权优先（不可抢占）
            case PSA:
                priorityQueue = new PriorityQueue<>(new Comparator<Process>() {
                    @Override
                    public int compare(Process o1, Process o2) {
                        return o2.getPriority() - o1.getPriority();
                    }
                });
                while(count > 0){
                    for(Process process:processList){
                        if(!priorityQueue.contains(process) && !process.getStatus().equals(ProcessStatus.FINSHED) && process.getArriveTime() <= curTime){
                            priorityQueue.add(process);
                        }
                    }
                    if(!priorityQueue.isEmpty()) {
                        Process process = priorityQueue.poll();
                        process.setBeginTime(curTime);
                        curTime += process.getNeedTime();
                        process.setFinishTime(curTime);
                        process.setStatus(ProcessStatus.FINSHED);
                        count--;
                    }
                    else curTime ++;
                }
                break;
            //短作业优先（不可抢占）
            case SLF:
                priorityQueue = new PriorityQueue<>(new Comparator<Process>() {
                    @Override
                    public int compare(Process o1, Process o2) {
                        return o1.getNeedTime() - o2.getNeedTime();
                    }
                });
                while(count > 0){
                    for(Process process:processList){
                        if(!priorityQueue.contains(process) && !process.getStatus().equals(ProcessStatus.FINSHED) && process.getArriveTime() <= curTime){
                            priorityQueue.add(process);
                        }
                    }
                    if(!priorityQueue.isEmpty()) {
                        Process process = priorityQueue.poll();
                        process.setBeginTime(curTime);
                        curTime += process.getNeedTime();
                        process.setFinishTime(curTime);
                        process.setStatus(ProcessStatus.FINSHED);
                        count--;
                    }
                    else curTime ++;
                }
        }
        Collections.sort(processList);
        System.out.println("name" + '\t' + "priority" + '\t' + "arriveTime" + '\t' + "needTime" + '\t' + "beginTime" + '\t' + "finishTime");
        for (int i = 0; i < processList.size(); i++) {
            System.out.println(processList.get(i).getName() + "          "
                    + processList.get(i).getPriority() + "            "
                    + processList.get(i).getArriveTime() + "          "
                    + processList.get(i).getNeedTime() + "           "
                    + processList.get(i).getBeginTime() + "          "
                    + processList.get(i).getFinishTime());
        }
    }
}
