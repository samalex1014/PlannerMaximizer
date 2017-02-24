/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannermaximizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel Smith
 */


public class PlannerMaximizer {
    
    private static int checked = 0;
    private static int available = 0;

    public static ArrayList<Course> loadFiles(String location) throws FileNotFoundException, IOException {
        ArrayList<Course> possibles = new ArrayList<Course>();
        BufferedReader br = new BufferedReader(new FileReader(location));

        //first junk line
        String line = br.readLine();

        //first real line
        line = br.readLine();

        while (line != null) {
            if (line.length() > 0) {
                Course toAdd = new Course(line);

                line = br.readLine();

                if (line != null && line.length() > 0) {
                    if (line.charAt(0) == ',') {
                        toAdd.addLabTimes(line);
                        line = br.readLine();
                    }
                    possibles.add(toAdd);
                } else {
                    possibles.add(toAdd);
                }
            }
        }

        br.close();

        return possibles;

    }

    public static void addWithoutDuplicates(ArrayList<PossibleSchedule> list, PossibleSchedule toAdd) {
        boolean unique = true;
        checked++;
        for (PossibleSchedule curr : list) {
            if (curr.equals(toAdd)) {
                unique = false;
            }

        }

        if (unique) {
            list.add(toAdd);

            System.out.println("Added sched: " + toAdd.toString());
        } else {
            System.out.println("Sched: " + toAdd.toString() + " is duplicate, failed to add");
        }

        System.out.println("Total schedules: " + list.size());
        System.out.println("Total checked: " + checked);
    }

    public static void createPossibleSchedule(ArrayList<PossibleSchedule> list,
            ArrayList<Course> courses, PossibleSchedule curr) {
        if (curr.size() == courses.size()) {

            addWithoutDuplicates(list, new PossibleSchedule(curr));
            System.out.println("Attempting to add sched: " + curr.toString());
        } else {
            for (int i = 0; i < courses.size(); i++) {
                if (!curr.contains(courses.get(i))) {
                    curr.add(courses.get(i));
                    createPossibleSchedule(list, courses, curr);
                    curr.remove(courses.get(i));
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Course> availables = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        /*
        System.out.print("Enter file location of schedule: ");
        String loc = "";
        try {
            loc = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(PlannerMaximizer.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        //FILE LOCATION FOR SURFACE
        //String loc = "C:\\Users\\samal\\Documents\\scheduleData.csv";
        String loc = "C:\\Box Sync\\OSU\\OSU SP17\\scheduleData.csv";

        try {
            availables = loadFiles(loc);
        } catch (IOException ex) {
            Logger.getLogger(PlannerMaximizer.class.getName()).log(Level.SEVERE, null, ex);
        }

        removeEmpties(availables);

        ArrayList<PossibleSchedule> results = new ArrayList<>();
        ArrayList<Integer> used = new ArrayList<>();
        ArrayList<Integer> conflict = new ArrayList<>();
        PossibleSchedule curr = new PossibleSchedule();

        createPossibleSchedule(results, availables, curr);
        System.out.println("Total available: " + availables.size());
        ArrayList<PossibleSchedule> fndScheds = new ArrayList<>();
        for (PossibleSchedule moving : results) {
            if (moving.contains("CSE", "2331")) {
                fndScheds.add(moving.clone());
            }
        }

        removeDuplicates(results);

        sort(results);

        available = availables.size();

        System.out.println("Total schedules found: " + results.size());

        try {
            File file = new File("C:\\Users\\samal\\Documents\\schedules.txt");
            File altFile = new File("C:\\Box Sync\\OSU\\OSU SP17\\results.txt");
            File fndFile = new File("C:\\Users\\samal\\Documents\\foundationSchedules.txt");
            File altFndFile = new File("C:\\Box Sync\\OSU\\OSU SP17\\foundationScheds.txt");

            /* This logic will make sure that the file 
	  * gets created if it is not present at the
	  * specified location*/
            if (!file.exists()) {
                file.createNewFile();
            }

            if (!fndFile.exists()) {
                fndFile.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            BufferedWriter altBW = new BufferedWriter(new FileWriter(altFile));
            BufferedWriter fndBw = new BufferedWriter(new FileWriter(fndFile));
            BufferedWriter altFndBw = new BufferedWriter(new FileWriter(altFndFile));

            printResults(bw, results);
            printResults(altBW, results);
            printResults(fndBw, fndScheds);
            printResults(altFndBw, fndScheds);

            altBW.close();
            altFndBw.close();
            bw.close();
            fndBw.close();
        } catch (IOException ex) {
            Logger.getLogger(PlannerMaximizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printResults(BufferedWriter out, ArrayList<PossibleSchedule> poss) throws IOException {
        out.write("---TOTAL CHECKED: " + checked + "---\n");
        out.write("---TOTAL AVAILABLE COURSES: " + available + "---\n");
        for (int i = 0; i < poss.size(); i++) {
            ArrayList<Course> sched = poss.get(i).getSched();

            out.write("Schedule " + i);
            out.write("(CH: " + poss.get(i).creditHours() + ")\n");

            for (Course curr : sched) {
                out.write("\t" + curr.getDept() + " " + curr.getCode() + "\n");
            }

            out.write("\n");
        }
    }

    public static void sort(ArrayList<PossibleSchedule> list) {
        ArrayList<PossibleSchedule> sorted = new ArrayList<>();
        while(list.size() > 0) {
            int maxIndex = 0;
            
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).creditHours() > list.get(maxIndex).creditHours()) {
                    maxIndex = i;
                }
            }
            
            sorted.add(list.remove(maxIndex));
        }
        
        while(sorted.size() > 0) {
            list.add(sorted.remove(0));
        }
        
    }

    public static void removeEmpties(ArrayList<Course> courses) {
        boolean done = false;

        while (!done) {
            done = true;
            int remover = -1;

            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getDept() == null) {
                    remover = i;
                    done = false;
                }
            }
            if (remover >= 0) {
                courses.remove(remover);
            }
        }
    }

    public static void removeDuplicates(ArrayList<PossibleSchedule> res) {
        int toRemove = -1;
        boolean done = false;

        while (!done) {
            done = true;
            for (int i = 0; i < res.size() - 1; i++) {
                if (res.get(i).equals(res.get(i + 1))) {
                    done = false;
                    toRemove = i;
                }

            }

            if (!done) {
                res.remove(toRemove);
            }

        }

    }

}
