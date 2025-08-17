package com.nt.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AppTest {

  
    @Test
    public void bignum() {
        double num1=7000000.0;
        double num2=8000000.0;
        App ob=new App();
        double expected=ob.add(num1, num2);
        double actual=15000000.0;
        assertEquals(expected,actual);
    }
    
    @Test
    public void smallnum() {
        double num1=700000.0;
        double num2=800000.0;
        App ob=new App();
        double expected=ob.add(num1, num2);
        double actual=1500000.0;
        assertEquals(expected,actual);
    }
    
    @Test
    public void zeros() {
        double num1=0.0;
        double num2=-0.0;
        App ob=new App();
        double expected=ob.add(num1, num2);
        double actual=0.0;
        assertEquals(expected,actual);
    }
    
    
    @Test
    public void negatives() {
        double num1=-7000000.0;
        double num2=8000000.0;
        App ob=new App();
        double expected=ob.add(num1, num2);
        double actual=1000000.0;
        assertEquals(expected,actual);
    }
    
    @Test
    public void smallnegatives() {
        double num1=-700000.0;
        double num2=-800000.0;
        App ob=new App();
        double expected=ob.add(num1, num2);
        double actual=-1500000.0;
        assertEquals(expected,actual);
    }
}
