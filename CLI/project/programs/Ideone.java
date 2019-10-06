import java.util.*;
import java.lang.*;
import java.io.*;

class Pair { 
  public final Integer first; 
  public final Integer second; 
  public Pair(Integer first, Integer second) { 
    this.first = first; 
    this.second = second; 
  } 

  public String toString() {
    return "[" + String.valueOf(this.first) + ", " + String.valueOf(this.second) + "]";
  }
} 

class ScheduleManager {

  private final Integer mStartTime;
  private final Integer mEndTime;

  public ScheduleManager(Integer startTime, Integer endTime) {
    mStartTime = startTime;
    mEndTime = endTime;
  }

  private ArrayList<ArrayList<Pair>> mSchedules = new ArrayList();

  public void addSchedule(ArrayList<Pair> schedule) {
    mSchedules.add(schedule);
  }

  public ArrayList<Pair> getFreeSchedule() {
    ArrayList<Pair> mergedSchedules = new ArrayList();
    for (ArrayList<Pair> schedule : mSchedules) {
      for (Pair time : schedule) {
        mergedSchedules.add(time);
      }
    }
    Collections.sort(mergedSchedules, new Comparator<Pair>() {
        @Override
        public int compare(Pair time1, Pair time2) {
            return  time1.first.compareTo(time2.first);
        }
    });
    ArrayList<Pair> freeSchedule = new ArrayList();
    Pair busyRange = new Pair(mStartTime, mStartTime);

    for (int i = 0; i < mergedSchedules.size(); i++) {
      Pair current = mergedSchedules.get(i);
      if (busyRange.second < current.first) {
        freeSchedule.add(new Pair(busyRange.second, current.first));
        busyRange = new Pair(current.first, current.second);
      } else if (current.second <= busyRange.second) {
        // do nothing 
      } else if (busyRange.second > current.first && current.second > busyRange.second) {
        busyRange = new Pair(busyRange.first, current.second);
      } else {
        // shouldn't happen
      }
    }

    // last section of free time
    if (busyRange.second < mEndTime) {
      freeSchedule.add(new Pair(busyRange.second, mEndTime));
    }

    return freeSchedule;
  }
}

/* Name of the class has to be "Main" only if the class is public. */
class Ideone {
  public static void main (String[] args) throws java.lang.Exception {
    ScheduleManager scheduleManager = new ScheduleManager(0, 24);
    ArrayList<Pair> schedule1 = new ArrayList();
    schedule1.add(new Pair(10,11));
    schedule1.add(new Pair(12,13));
    schedule1.add(new Pair(14,15));
    scheduleManager.addSchedule(schedule1);
    ArrayList<Pair> schedule2 = new ArrayList();
    schedule2.add(new Pair(11,13));
    scheduleManager.addSchedule(schedule2);
    ArrayList<Pair> schedule3 = new ArrayList();
    schedule3.add(new Pair(10,12));
    scheduleManager.addSchedule(schedule3);
    System.out.println(scheduleManager.getFreeSchedule().toString());
  }
}
