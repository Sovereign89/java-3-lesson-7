package com.geekbrains;

import com.geekbrains.annotations.*;

public class ExampleTests {

    @AfterSuite
    public void finish(){
        System.out.println("@AfterSuite");
    }

    @BeforeSuite
    public void begin(){
        System.out.println("@BeforeSuite");
    }

    @Priority(3)
    public void Test2(){
        System.out.println("@Test2  - Priority 3");
    }

    @Priority(2)
    public void Test3(){
        System.out.println("@Test3  - Priority 2");
    }

    @Priority(1)
    public void Test1(){
        System.out.println("@Test1 - Priority 1");
    }

    @Priority(2)
    public void Test4(){
        System.out.println("@Test4 - Priority 2");
    }

}
