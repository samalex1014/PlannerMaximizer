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
    public void testConflicts_False() {
        Course fnd = new Course("CSE,2331,1,3,13:50-14:45,13:50-14:45,,13:50-14:45,13:50-14:45");
        Course ethics = new Course("CSE,2501,10,1,,9:35-10:55,9:35-10:56,9:35-10:57,9:35-10:58");
        
        assertTrue(!fnd.conflicts(ethics));
    }
    
    
    @Test
    public void testConflicts_True() {
        Course fnd = new Course("CSE,2331,1,3,13:50-14:45,13:50-14:45,,13:50-14:45,13:50-14:45");
        Course ops = new Course("CSE,2431,0010,3,14:20-15:40,14:20-15:40,,14:20-15:40,14:20-15:40");
        
        assertTrue(fnd.conflicts(ops));
    }
    
    @Test
    public void testEquals_True() {
        Course fnd = new Course("CSE,2331,1,3,13:50-14:45,13:50-14:45,,13:50-14:45,13:50-14:45");
        Course fnd2 = new Course("CSE,2331,2,3,13:40-14:35,13:40-14:35,,13:50-14:45,13:50-14:45");
        
        assertTrue(fnd.equals(fnd2));
    }
    
    @Test
    public void testEquals_False() {
        Course fnd = new Course("CSE,2331,1,3,13:50-14:45,13:50-14:45,,13:50-14:45,13:50-14:45");
        Course ops = new Course("CSE,2431,0010,3,14:20-15:40,14:20-15:40,,14:20-15:40,14:20-15:40");
        
        assertTrue(!fnd.equals(ops));
    }
}
