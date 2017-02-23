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

    public static void createPossibleSchedule(ArrayList<PossibleSchedule> list,
            ArrayList<Course> courses, ArrayList<Integer> used, ArrayList<Integer> conflict,
            PossibleSchedule curr) {
        if (used.size() + conflict.size() == courses.size() - 1) {
            int left = 0;
            while (used.contains(left) || conflict.contains(left)) {
                left++;
            }

            curr.add(courses.get(left));

            used.add(left);

            list.add(curr);
        } else {
            for (int i = 0; i < courses.size(); i++) {
                if (!used.contains(i) && !conflict.contains(i)) {
                    boolean res = curr.add(courses.get(i));
                    if (res) {
                        used.add(i);
                    } else {
                        used.add(i);
                    }

                    if (used.size() < courses.size()) {
                        ArrayList<Integer> usedCopy = new ArrayList<>();
                        usedCopy = (ArrayList<Integer>) used.clone();

                        createPossibleSchedule(list, courses, usedCopy, conflict, curr.clone());
                    }
                    
                    
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

        System.out.print("Enter file location of schedule: ");
        String loc = "";
        try {
            loc = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(PlannerMaximizer.class.getName()).log(Level.SEVERE, null, ex);
        }

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

        createPossibleSchedule(results, availables, used, conflict, curr);
        
        removeDuplicates(results);

        System.out.println("Total schedules found: " + results.size());

        try {
            File file = new File("C:\\Users\\samal\\Documents\\results.txt");

            /* This logic will make sure that the file 
	  * gets created if it is not present at the
	  * specified location*/
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            printResults(bw, results);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(PlannerMaximizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printResults(BufferedWriter out, ArrayList<PossibleSchedule> poss) throws IOException {
        for (PossibleSchedule curr : poss) {
            out.write(curr.toString());
            out.write("\n");
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
        
        while(!done) {
            done = true;
            for(int i = 0; i < res.size() -1; i++) {
                if(res.get(i).equals(res.get(i+1))) {
                    done = false;
                    toRemove = i;
                }
                
            }
            
            if(!done) {
                res.remove(toRemove);
            }
            
        }
        
    }

}
