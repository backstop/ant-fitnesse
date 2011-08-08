package com.backstopsolutions.ant.fitnesse;

import org.apache.tools.ant.types.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: nriffe
 * Date: 7/28/11
 * Time: 1:05 PM
 */
public class Suite extends Resource {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
