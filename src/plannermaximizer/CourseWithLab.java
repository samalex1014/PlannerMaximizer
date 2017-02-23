/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannermaximizer;

/**
 *
 * @author samal
 */
public class CourseWithLab extends Course {
    private MeetingTimes[] labSchedule;
    
    
    public CourseWithLab(String inFirst, String labTimes) {
        super(inFirst);
        
        labSchedule = new MeetingTimes[5];
        String[] labSched = labTimes.split(",");
        
        for(int i = 0; i < labSchedule.length; i++) {
            labSchedule[i] = new MeetingTimes(labSched[i]);
        }
    }
    
    public CourseWithLab(Course lect, String labTimes) {
        super(lect);
        
        labSchedule = new MeetingTimes[5];
        String[] labSched = labTimes.split(",");
        
        for(int i = 0; i < labSchedule.length; i++) {
            labSchedule[i] = new MeetingTimes(labSched[i]);
        }
        
    }
    
    @Override
    public boolean conflicts(Course check) {
        boolean doesntWork = false;
        
        for(int i = 0; i < schedule.length; i++) {
            if(schedule[i].conflictsWith(check.schedule[i])) {
                doesntWork = true;
            }
        }
        
        for(int i = 0; i < labSchedule.length; i++) {
            if(schedule[i].conflictsWith(check.schedule[i])) {
                doesntWork = true;
            }
        }
        
        return doesntWork;
    }
    
    
}
