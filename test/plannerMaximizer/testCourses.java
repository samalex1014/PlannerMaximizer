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
import plannermaximizer.Course;


/**
 *
 * @author samal
 */
public class testCourses {
    
    public testCourses() {
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
    public void testConflicts_FalseLab() {
        Course ethics = new Course("CSE,2501,20,1,12:45-14:05,12:45-14:05,12:45-14:05,12:45-14:05,,1,4");
        Course bio = new Course("EASCI,1121,10,4,10:20-12:25,,10:20-12:25,,10:20-12:25,7,12");
        
        bio.addLabTimes(",,14,0,12:40-14:45,,12:40-14:45,,,,");
        
        assertTrue(!ethics.conflicts(bio));
        assertEquals(ethics.overlappingWeeks(bio), bio.overlappingWeeks(ethics));
        assertEquals(ethics.conflicts(bio), bio.conflicts(ethics));
    }
    
    
}
