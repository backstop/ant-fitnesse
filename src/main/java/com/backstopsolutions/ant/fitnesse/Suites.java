package com.backstopsolutions.ant.fitnesse;

import org.apache.tools.ant.types.DataType;
import org.apache.tools.ant.types.ResourceCollection;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nriffe
 * Date: 7/28/11
 * Time: 12:15 PM
 */
public class Suites extends DataType implements ResourceCollection {

    private List<Suite> suites = new ArrayList<Suite>();

    public void addSuite(Suite suite) {
        suites.add(suite);
    }

    public Iterator iterator() {
        return isReference() ? ((Suites) getCheckedRef()).iterator() : suites.iterator();
    }

    public int size() {
        return isReference() ? ((Suites) getCheckedRef()).size() : suites.size();
    }

    public boolean isFilesystemOnly() {
        return false;
    }
}
