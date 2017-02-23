/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannermaximizer;

import java.util.ArrayList;

/**
 *
 * @author Samuel Smith
 */
public class PossibleSchedule {
    private ArrayList<Course> schedule;
    
    private int creditHours;
    
    public PossibleSchedule() {
        schedule = new ArrayList<>();
        creditHours = 0;
    }
    
    public PossibleSchedule(PossibleSchedule source) {
       this.schedule = new ArrayList<>();
        for(int i = 0; i < source.schedule.size(); i++) {
            if(source.schedule.get(i) != null) {
                this.schedule.add(source.schedule.get(i).clone());
            } 
        }
        
        
        this.creditHours = source.creditHours;
    }
    
    public String toString() { 
        StringBuilder sb = new StringBuilder();
        
        for(Course curr : schedule) {
            sb.append(curr.toString());
            sb.append(": ");
        }
        
        return sb.toString();
    }
    
    public boolean add(Course toAdd) {
        boolean added = true;
        
        for(Course current : schedule) {
            if(current.conflicts(toAdd)) {
                added = false;
            }
        }
        
        if(added) {
            schedule.add(toAdd.clone());
        }
        
        return added;
    }
    
    public PossibleSchedule clone() {
        return new PossibleSchedule(this);
    }
    
    public boolean equals(PossibleSchedule check) {
        boolean same = false;
        
        if(this.schedule.size() == check.schedule.size()) {
            if(this.creditHours == check.creditHours) {
                same = true;
                for(int i = 0; i < this.schedule.size(); i++) {
                    if(!this.schedule.get(i).equals(check.schedule.get(i))) {
                        same = false;
                    }
                }
            }
        }
        return same;
    }
}
