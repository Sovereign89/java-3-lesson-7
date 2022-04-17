package com.geekbrains;

import com.geekbrains.test.TestAnnotations;

public class Main {
    public static void main(String[] args) {
        TestAnnotations testAnnotations = new TestAnnotations();
        testAnnotations.RunTests(ExampleTests.class);
    }
}
