/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannermaximizer;

import plannermaximizer.PossibleSchedule;

/**
 *
 * @author Samuel Smith
 */
public class MeetingTimes {

    private int startTime;
    private int endTime;
    
    public MeetingTimes() {
        startTime = 0;
        endTime = 0;
    }

    public MeetingTimes(String in) {
        
        if (in.length() > 0) {
            String startEndDelims = "-";
            String hourMinDelims = ":";
            String[] startEnd = in.split(startEndDelims);
            String[] startTimes = startEnd[0].split(hourMinDelims);
            String[] endTimes = startEnd[1].split(hourMinDelims);

            int startHour = Integer.parseInt(startTimes[0]);
            int startMin = Integer.parseInt(startTimes[1]);
            int endHour = Integer.parseInt(endTimes[0]);
            int endMin = Integer.parseInt(endTimes[1]);

            this.startTime = (startHour*60)+startMin;
            this.endTime = (endHour*60) + endMin;
        }
    }

    

    public boolean conflictsWith(MeetingTimes option) {
        boolean conflicts = false;
        
        if(option.startTime >= this.startTime && option.startTime <= this.endTime) {
            conflicts = true;
        } else if (this.startTime >= option.startTime && this.startTime <= option.endTime) {
            conflicts = true;
        }
        

        return conflicts;
    }

    public MeetingTimes clone() {
        MeetingTimes copy = new MeetingTimes();

        copy.startTime = this.startTime;
        copy.endTime = this.endTime;

        return copy;
    }
}
