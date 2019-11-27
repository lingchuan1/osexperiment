package jobScheduling;

import java.util.*;

/**
 * 单道处理系统的作业等待模拟程序
 * A 1 3
 * B 2 10
 * C 3 3
 */
public class Main {
    public static void main(String[] args) {
        List<JCB> jcbList = new LinkedList<>();
        //输入作业信息
        System.out.print("请输入作业数：");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        for (int i = 0; i < num; i++) {
            System.out.println("第" + (i + 1) + "个作业");
            System.out.print("输入作业名：");
            in = new Scanner(System.in);
            String name = in.nextLine();
            System.out.print("输入提交时间：");
            in = new Scanner(System.in);
            int submitTime = in.nextInt();
            System.out.print("输入作业运行时间：");
            in = new Scanner(System.in);
            int allNeedTime = in.nextInt();
            System.out.print("输入作业需要资源(a,b,c)");
            in = new Scanner(System.in);
            String resourceName =  in.nextLine();
            JCB jcb = new JCB(name, submitTime, allNeedTime, JcbStatus.WAIT, resourceName);
            jcbList.add(jcb);
        }
        //设置资源
        Map<String,Integer> resourceMap = new HashMap<>();
        resourceMap.put("a",0);
        resourceMap.put("b",0);
        resourceMap.put("c",0);
        while (true) {
            System.out.println("      1.先来先服务（FCFS）");
            System.out.println("      2.响应比高者优先（HRN）");
            System.out.println("      3.退出");
            System.out.print("请输入你的选择：");
            in = new Scanner(System.in);
            int choice = in.nextInt();
            switch (choice) {
                case 1:
                    int curTime = 0;
                    Collections.sort(jcbList, new Comparator<JCB>() {
                        @Override
                        public int compare(JCB o1, JCB o2) {
                            return o1.getSubmitTime() - o2.getSubmitTime();
                        }
                    });
                    int i = 0;
                    while (i < jcbList.size()){
                        JCB cur = jcbList.get(i ++);
                        cur.setStatus(JcbStatus.RUNNING);
                        resourceMap.put(cur.getResource(),1);
                        if(curTime < cur.getSubmitTime())
                            curTime = cur.getSubmitTime();
                        cur.setWorkTime(new WorkTime(curTime,curTime + cur.getAllNeedTime(),curTime + cur.getAllNeedTime() - cur.getSubmitTime(),(curTime + cur.getAllNeedTime() - cur.getSubmitTime())/cur.getAllNeedTime()));
                        System.out.println("######执行第" + i + "个作业");
                        System.out.println("####当前正在运行的作业是：" + cur.getName());
                        System.out.println("作业   状态   服务时间   开始运行时刻   完成时刻   周转时间   带权周转时间");
                        System.out.println(cur.getName() + "      " + cur.getStatus() + "     " + cur.getAllNeedTime() + "        " +cur.getWorkTime().getStartTime() + "           " +cur.getWorkTime().getFinishTime() + "           " + cur.getWorkTime().getTi() + "           " + cur.getWorkTime().getWi() );
                        System.out.println("####当前就绪队列为：");
                        System.out.println("作业   状态   服务时间");
                        for(int j = i;j < jcbList.size();j++){
                            JCB jcb = jcbList.get(j);
                            if(jcb.getSubmitTime() < curTime + cur.getAllNeedTime() )
                                System.out.println(jcb.getName() + "      " + jcb.getStatus() + "      " + jcb.getAllNeedTime());
                        }
                        System.out.println("####进程" + cur.getName() + "已完成");
                        System.out.println();
                        resourceMap.put(cur.getResource(),0);
                        cur.setStatus(JcbStatus.FINISHED);
                        curTime += cur.getAllNeedTime();
                    }
                    double allTi = 0,allWi = 0;
                    for(int j = 0;j < jcbList.size();j ++){
                         allTi += jcbList.get(j).getWorkTime().getTi();
                         allWi += jcbList.get(j).getWorkTime().getWi();
                    }
                    System.out.println("*****作业平均周转时间：" + allTi/jcbList.size());
                    System.out.println("*****作业平均带权周转时间：" + allWi/jcbList.size());
                    System.out.println();
                    for(JCB jcb:jcbList)
                        jcb.setStatus(JcbStatus.WAIT);
                    break;
                case 2:
                    PriorityQueue<JCB> queue = new PriorityQueue<>(new Comparator<JCB>() {
                        @Override
                        public int compare(JCB o1, JCB o2) {
                            return o2.getPriority() - o1.getPriority() < 0 ? 1 : -1 ;
                        }
                    });
                    int count = jcbList.size();
                    curTime = 0;
                    while(count > 0){
                        for(JCB jcb:jcbList){
                            if(!queue.contains(jcb) && jcb.getSubmitTime() <= curTime && !jcb.getStatus().equals(JcbStatus.FINISHED)){
                                queue.add(jcb);
                            }
                        }
                        //调度前计算优先权
                        for(JCB jcb:queue){
                            jcb.setPriority((curTime - jcb.getSubmitTime() + jcb.getAllNeedTime())/jcb.getAllNeedTime());
                        }
                        if(!queue.isEmpty()){
                            JCB cur = queue.poll();
                            cur.setStatus(JcbStatus.RUNNING);
                            resourceMap.put(cur.getResource(),1);
                            cur.setWorkTime(new WorkTime(curTime,curTime + cur.getAllNeedTime(),curTime + cur.getAllNeedTime() - cur.getSubmitTime(),(curTime + cur.getAllNeedTime() - cur.getSubmitTime())/cur.getAllNeedTime()));
                            System.out.println("######执行第" + (jcbList.size() - count + 1) + "个作业");
                            System.out.println("####当前正在运行的作业是：" + cur.getName());
                            System.out.println("作业   状态   服务时间   开始运行时刻   完成时刻   周转时间   带权周转时间");
                            System.out.println(cur.getName() + "      " + cur.getStatus() + "     " + cur.getAllNeedTime() + "        " +cur.getWorkTime().getStartTime() + "           " +cur.getWorkTime().getFinishTime() + "           " + cur.getWorkTime().getTi() + "           " + cur.getWorkTime().getWi() );
                            System.out.println("####当前就绪队列为：");
                            System.out.println("作业   状态   服务时间");
                            for(JCB jcb:queue){
                                System.out.println(jcb.getName() + "      " + jcb.getStatus() + "      " + jcb.getAllNeedTime());
                            }
                            System.out.println("####进程" + cur.getName() + "已完成");
                            System.out.println();
                            resourceMap.put(cur.getResource(),0);
                            cur.setStatus(JcbStatus.FINISHED);
                            curTime += cur.getAllNeedTime();
                            count --;
                        }else curTime++;
                    }
                    allTi = 0;
                    allWi = 0;
                    for(int j = 0;j < jcbList.size();j ++){
                        allTi += jcbList.get(j).getWorkTime().getTi();
                        allWi += jcbList.get(j).getWorkTime().getWi();
                    }
                    System.out.println("*****作业平均周转时间：" + allTi/jcbList.size());
                    System.out.println("*****作业平均带权周转时间：" + allWi/jcbList.size());
                    System.out.println();
                    for(JCB jcb:jcbList)
                        jcb.setStatus(JcbStatus.WAIT);
                    break;
                default:
                    break;
            }
        }
    }
}
