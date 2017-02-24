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
public class PossibleSchedule extends Object {

    private ArrayList<Course> schedule;
    private ArrayList<Course> conflicted;

    private int creditHours;

    public PossibleSchedule() {
        schedule = new ArrayList<>();
        conflicted = new ArrayList<>();
        creditHours = 0;
    }

    public PossibleSchedule(PossibleSchedule source) {
        this.schedule = new ArrayList<>();
        for (int i = 0; i < source.schedule.size(); i++) {
            if (source.schedule.get(i) != null) {
                this.schedule.add(source.schedule.get(i).clone());
            }
        }

        this.creditHours = source.creditHours;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Course curr : schedule) {
            sb.append(curr.toString());
            sb.append(": ");
        }

        return sb.toString();
    }

    public ArrayList<Course> getSched() {
        ArrayList<Course> newSched = new ArrayList<>();

        for (Course curr : this.schedule) {
            newSched.add(curr.clone());
        }

        return newSched;
    }

    public boolean contains(String dept, String code) {
        boolean present = false;

        for (Course curr : this.schedule) {
            if (curr.sameCourse(dept, code)) {
                present = true;
            }
        }

        return present;
    }

    public int size() {
        return this.schedule.size() + this.conflicted.size();
    }

    public int creditHours() {
        int totHours = 0;
        for (Course curr : schedule) {
            totHours += curr.getHours();
        }

        return totHours;
    }

    public boolean add(Course toAdd) {
        boolean added = true;

        for (Course current : schedule) {
            if (current.conflicts(toAdd)) {
                added = false;
            }
            if (toAdd.conflicts(current)) {
                added = false;
            }
            if (current.sameCourse(toAdd)) {
                added = false;
            }
        }

        if (added) {
            schedule.add(toAdd.clone());
        } else {
            conflicted.add(toAdd.clone());
        }

        return added;
    }

    public void remove(Course toRemove) {
        int removeIndex = -1;

        for (int i = 0; i < this.schedule.size(); i++) {
            if (toRemove.equals(this.schedule.get(i))) {
                removeIndex = i;
            }
        }

        if (removeIndex < 0) {
            for (int i = 0; i < this.conflicted.size(); i++) {
                if (toRemove.equals(this.conflicted.get(i))) {
                    removeIndex = i;
                }
            }

            if (removeIndex >= 0) {
                this.conflicted.remove(removeIndex);
            }
        } else {
            if (removeIndex >= 0) {
                this.schedule.remove(removeIndex);
            }
        }

    }

    public PossibleSchedule clone() {
        return new PossibleSchedule(this);
    }

    public boolean contains(Course check) {
        boolean isPresent = false;
        for (Course curr : this.schedule) {
            if (curr.equals(check)) {
                isPresent = true;
            }
        }
        if (conflicted != null) {
            for (Course curr : this.conflicted) {
                if (curr.equals(check)) {
                    isPresent = true;
                }
            }
        }
        return isPresent;
    }

    public boolean containsDiffSection(Course check) {
        boolean isPresent = false;
        for (Course curr : this.schedule) {
            if (curr.sameCourse(check)) {
                isPresent = true;
            }
        }
        if (conflicted != null) {
            for (Course curr : this.conflicted) {
                if (curr.sameCourse(check)) {
                    isPresent = true;
                }
            }
        }
        return isPresent;
    }

    public boolean equals(PossibleSchedule check) {
        boolean same = false;

        if (this.schedule.size() == check.schedule.size()) {
            if (this.creditHours == check.creditHours) {
                same = true;
                for (int i = 0; i < check.schedule.size(); i++) {
                    if (!this.containsDiffSection(check.schedule.get(i))) {
                        same = false;
                    }
                }

                for (int i = 0; i < this.schedule.size(); i++) {
                    if (!check.containsDiffSection(this.schedule.get(i))) {
                        same = false;
                    }
                }
            }
        }
        return same;
    }

    public int DEBUGGING_Sched_Size() {
        return schedule.size();
    }
}
