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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel Smith
 */
public class PlannerMaximizer {

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
                }
            }
        }

        br.close();

        return possibles;

    }

    public static void addWithoutDuplicates(ArrayList<PossibleSchedule> list, PossibleSchedule toAdd, int checked) {
        boolean unique = true;
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
            ArrayList<Course> courses, PossibleSchedule curr, int checked) {
        if (curr.size() == courses.size()) {
            checked++;
            addWithoutDuplicates(list, new PossibleSchedule(curr), checked);
            System.out.println("Attempting to add sched: " + curr.toString());
        } else {
            for (int i = 0; i < courses.size(); i++) {
                if (!curr.contains(courses.get(i))) {
                    curr.add(courses.get(i));
                    createPossibleSchedule(list, courses, curr, checked);
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

        createPossibleSchedule(results, availables, curr, 0);
        ArrayList<PossibleSchedule> fndScheds = new ArrayList<>();
        for (PossibleSchedule moving : results) {
            if (moving.contains("CSE", "2331")) {
                fndScheds.add(moving.clone());
            }
        }

        removeDuplicates(results);

        System.out.println("Total schedules found: " + results.size());

        try {
            File file = new File("C:\\Users\\samal\\Documents\\schedules.txt");
            File fndFile = new File("C:\\Users\\samal\\Documents\\foundationSchedules.txt");

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
            BufferedWriter fndBw = new BufferedWriter(new FileWriter(fndFile));
            printResults(bw, results);
            printResults(fndBw, fndScheds);
            bw.close();
            fndBw.close();
        } catch (IOException ex) {
            Logger.getLogger(PlannerMaximizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printResults(BufferedWriter out, ArrayList<PossibleSchedule> poss) throws IOException {
        for (int i = 0; i < poss.size(); i++) {
            ArrayList<Course> sched = poss.get(i).getSched();

            out.write("Schedule " + i + "\n");
            out.write(poss.get(i).creditHours() + "\n");

            for (Course curr : sched) {
                out.write("\t" + curr.getDept() + " " + curr.getCode());
            }
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
