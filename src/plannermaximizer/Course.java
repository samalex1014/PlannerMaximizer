/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannermaximizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Samuel Smith
 */
public class Course extends Object{
    protected MeetingTimes[] schedule;
    protected MeetingTimes[] labTimes;
    protected int hours;
    protected final int MONDAY = 0;
    protected final int TUESDAY = 1;
    protected final int WEDNESDAY =2;
    protected final int THURSDAY = 3;
    protected final int FRIDAY = 4;
    String dept;
    String code;
    String section;
    
    private void initSched() {
        schedule = new MeetingTimes[5];
        
        schedule[MONDAY] = new MeetingTimes();
        schedule[TUESDAY] = new MeetingTimes();
        schedule[WEDNESDAY] = new MeetingTimes();
        schedule[THURSDAY] = new MeetingTimes();
        schedule[FRIDAY] = new MeetingTimes();
    }
    
    private void initLabSched() {
        labTimes = new MeetingTimes[5];
        
        labTimes[MONDAY] = new MeetingTimes();
        labTimes[TUESDAY] = new MeetingTimes();
        labTimes[WEDNESDAY] = new MeetingTimes();
        labTimes[THURSDAY] = new MeetingTimes();
        labTimes[FRIDAY] = new MeetingTimes();
    }
    
    private void initTimes() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter start hour in 24-hour format: ");
        String inStartHour = br.readLine();
        System.out.print("Enter start minute: ");
        String inStartMin = br.readLine();
        
        System.out.print("Enter the end hour in 24-hour format: ");
        String inEndHour = br.readLine();
        System.out.print("Enter the end minute: ");
        String inEndMinute = br.readLine();
    }
    public Course(String indept, String incode, String inSection, MeetingTimes[] inSched) {
        initSched();
        dept = indept;
        code = incode;
        section = inSection;
        schedule = inSched;
    }
    
    public Course(Course toBeCopied) {
        this.code = toBeCopied.code;
        this.dept = toBeCopied.dept;
        this.hours = toBeCopied.hours;
        this.schedule = toBeCopied.schedule.clone();
        if(toBeCopied.labTimes != null) {
        this.labTimes = toBeCopied.labTimes.clone();
        }
        this.section = toBeCopied.section;
    }
    
    public Course(String input) {
        String delims = ",";
        String[] info = input.split(delims);
        
        initSched();
        
        if(info.length > 0) {
        dept = info[0];
        code = info[1];
        section = info[2];
        hours = Integer.parseInt(info[3]);
        schedule[MONDAY] = new MeetingTimes(info[4]);
        schedule[TUESDAY] =new MeetingTimes(info[5]);
        schedule[WEDNESDAY]= new MeetingTimes(info[6]);
        schedule[THURSDAY] = new MeetingTimes(info[7]);
        if(info.length>8) {
            schedule[FRIDAY] = new MeetingTimes(info[8]);
        } 
        }
        
    }
    
    public void addLabTimes(String labMeets) {
        String delims = ",";
        String[] info = labMeets.split(delims);
        
        initLabSched();
        
        section = info[2];
        labTimes[MONDAY] = new MeetingTimes(info[4]);
        labTimes[TUESDAY] =new MeetingTimes(info[5]);
        labTimes[WEDNESDAY]= new MeetingTimes(info[6]);
        if(info.length > 7) {
        labTimes[THURSDAY] = new MeetingTimes(info[7]);
        }
        if(info.length > 8) {
            labTimes[FRIDAY] = new MeetingTimes(info[8]);
        } 
    }
    
    public boolean conflicts(Course option) {
        boolean doesntWork = false;
        
        for(int i = 0; i < schedule.length; i++) {
            if(schedule[i].conflictsWith(option.schedule[i])) {
                doesntWork = true;
            }
            if(this.labTimes != null && option.labTimes != null) {
                if(labTimes[i].conflictsWith(option.labTimes[i])) {
                    doesntWork = true;
                }
                
                if(labTimes[i].conflictsWith(option.schedule[i])) {
                    doesntWork = true;
                }
                
                if(schedule[i].conflictsWith(option.labTimes[i])) {
                    doesntWork = true;
                }
            } else if (this.labTimes != null && option.labTimes == null) {
                if(this.labTimes[i].conflictsWith(option.schedule[i])) {
                    doesntWork = true;
                }
            } else if (this.labTimes == null && option.labTimes != null) {
                if(schedule[i].conflictsWith(option.labTimes[i])) {
                    doesntWork = true;
                }
            }
            
            
        }
        
        return doesntWork;
    }
    
    public String getDept() {
        return dept;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getSect() {
        return section;
    }
    
    public boolean equals(Course check) {
        return this.dept.equals(check.dept) && this.code.equals(check.code) && this.section.equals(check.section);
    }
    
    public String toString() {
        String courseNote = dept + " " + code + ", Section: " + section;
        
        return courseNote;
        
    }
    
    @Override
    public Course clone() {
        return new Course(this);
    }
    
    public boolean sameCourse(Course check) {
        return this.dept.equals(check.dept) && this.code.equals(check.code);
    }
    
    public boolean sameCourse(String dept, String code) {
        return this.dept.equals(dept) && this.code.equals(code);
    }
    
    public int getHours() {
        return this.hours;
    }
}
