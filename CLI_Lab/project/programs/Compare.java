package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Spayk on 24.10.2016.
 */
public class Compare {
    private List<Task> sortedTask = new ArrayList<Task>();
    Machine m1, m2;
    public Compare(Machine m1, Machine m2){
        this.m1 = m1;
        this.m2 = m2;
        this.sort();

    }

    public void sort(){
        List<Task> taskTmp1 = new ArrayList<Task>();
        List<Task> taskTmp2 = new ArrayList<Task>();

        for(int i=0;i<m1.getTaskList().size();i++){
            if(m1.getTask(i).getTime() <= m2.getTask(i).getTime() ){
                taskTmp1.add(m1.getTask(i));
            }else {
                taskTmp2.add(m1.getTask(i));
            }
        }
        Collections.sort(taskTmp1, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.getTime() > o2.getTime()) return 1;
                if(o1.getTime() < o2.getTime()) return -1;
                return 0;
            }
        });

        Collections.sort(taskTmp2, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(m2.getTask(o1.getIndex()).getTime() < m2.getTask(o2.getIndex()).getTime()) return 1;
                if(m2.getTask(o1.getIndex()).getTime() > m2.getTask(o2.getIndex()).getTime()) return -1;
                return 0;
            }
        });

        sortedTask.addAll(taskTmp1);
        sortedTask.addAll(taskTmp2);



    }

    public List<Task> getSortedTask(){
        return sortedTask;
    }

    public int getStartTaskTime(int indexSortedTask, int machine){
        if(machine == 1){
            if(indexSortedTask==0) return 0;
            return getEndTaskTime(indexSortedTask-1, 1);
        }
        if(machine == 2){
            if(indexSortedTask == 0 ) return getEndTaskTime(indexSortedTask, 1);
            if(getEndTaskTime(indexSortedTask, 1) >= getEndTaskTime(indexSortedTask-1, 2)) return getEndTaskTime(indexSortedTask, 1);
            else return getEndTaskTime(indexSortedTask-1, 2);
        }
        return 0;
    }

    public int getEndTaskTime(int indexSortedTask, int machine){
        if(machine == 1){
            if(indexSortedTask == 0) return sortedTask.get(0).getTime();
            return sortedTask.get(indexSortedTask).getTime()+getEndTaskTime(indexSortedTask-1,1);
        }if(machine == 2){
            if(indexSortedTask == 0 ) return m2.getTask(sortedTask.get(indexSortedTask).getIndex()).getTime()+getEndTaskTime(indexSortedTask, 1);
            if(getEndTaskTime(indexSortedTask, 1) >= getEndTaskTime(indexSortedTask-1, 2)) return m2.getTask(sortedTask.get(indexSortedTask).getIndex()).getTime()+getEndTaskTime(indexSortedTask, 1);
            else return m2.getTask(sortedTask.get(indexSortedTask).getIndex()).getTime() + getEndTaskTime(indexSortedTask-1,2);
        }
        return 0;
    }
}
