/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plannerMaximizer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author samal
 */
public class testSchedule {
    
    public testSchedule() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testAddPoss() {
        plannermaximizer.Course ethics = new plannermaximizer.Course("CSE,2501,20,1,12:45-14:05,12:45-14:05,12:45-14:05,12:45-14:05,");
        plannermaximizer.Course bio = new plannermaximizer.Course("BIO,1113,10,4,9:50-11:25,9:50-11:25,9:50-11:25,9:50-11:25,");
        
        bio.addLabTimes(",,20,0,,12:00-14:45,,12:00-14:45,");
        
        plannermaximizer.PossibleSchedule test = new plannermaximizer.PossibleSchedule();
        
        test.add(ethics);
        test.add(bio);
        
        assertEquals(1, test.DEBUGGING_Sched_Size());
    }
}
