package com.backstopsolutions.ant.fitnesse;

import org.apache.tools.ant.types.ResourceCollection;

import javax.swing.text.StyleContext;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nriffe
 * Date: 7/28/11
 * Time: 12:15 PM
 */
public class Suites {

    private TestTask testTask;
    private List<Suite> suites = new ArrayList<Suite>();

    public Suites(TestTask testTask) {
        this.testTask = testTask;
    }

    public void addSuite(Suite suite) {
        suites.add(suite);
    }

    public Set<String> getSuiteNames() {
        Set<String> names = new HashSet<String>();
        for (Suite suite : suites) {
            names.add(suite.getName());
        }
        return names;
    }

}
