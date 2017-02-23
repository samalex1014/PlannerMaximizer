/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannermaximizer;

/**
 *
 * @author Samuel Smith
 */
public class MeetingTimes {
    private boolean times[][];
    
    private void initTimes() {
        times = new boolean[24][60];
        for(boolean[] hours : times) {
            for(boolean minutes: hours) {
                minutes = false;
            }
        }
    }
    
    public MeetingTimes() {
        initTimes();
    }
    
    public MeetingTimes(String in) {
        initTimes();
        if(in.length() > 0) {
        String startEndDelims = "-";
        String hourMinDelims = ":";
        String[] startEnd = in.split(startEndDelims);
        String[] startTimes = startEnd[0].split(hourMinDelims);
        String[] endTimes = startEnd[1].split(hourMinDelims);
        
        int startHour = Integer.parseInt(startTimes[0]);
        int startMin = Integer.parseInt(startTimes[1]);
        int endHour = Integer.parseInt(endTimes[0]);
        int endMin = Integer.parseInt(endTimes[1]);
        
        int hoursTaken = startHour;
        
        while(hoursTaken < endHour) {
            int min = 0;
            while(min < times[hoursTaken].length) {
                times[hoursTaken][min] = true;
                min++;
            }
            
            hoursTaken++;
        }
        
        for(int i = 0; i < endMin; i++) {
            times[hoursTaken][i] = true;
        }
        }
    }
    
    public boolean[][] getTimes() {
        return times.clone();
    }
    
    public boolean conflictsWith(MeetingTimes option) {
        boolean conflicts = false;
        boolean[][] optionTimes = option.getTimes();
        
        for(int i = 0; i < times.length; i++) {
            for(int j = 0; j < times[i].length; j++) {
                if(times[i][j] == true && optionTimes[i][j] == true) {
                    conflicts = true;
                }
            }
        }
        
        return conflicts;
    }
    
}
