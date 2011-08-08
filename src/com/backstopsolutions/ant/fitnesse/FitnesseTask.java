package com.backstopsolutions.ant.fitnesse;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Environment;

import java.util.ArrayList;
import java.util.List;

public class FitnesseTask extends Java {

    private List<SuiteFilter> suiteFilters = new ArrayList<SuiteFilter>();
    private String suiteName;
    private Integer port;
    private String slimTableFactory;

    public FitnesseTask() {
        setClassname("fitnesseMain.FitNesseMain");
        setFailonerror(true);
        setFork(true);
    }

    public void addSuiteFilter(SuiteFilter filter) {
        suiteFilters.add(filter);
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setSlimTableFactory(String slimTableFactory) {
        this.slimTableFactory = slimTableFactory;
    }

    @Override
    public void execute() throws BuildException {
        if (slimTableFactory != null) {
            Environment.Variable var = new Environment.Variable();
            var.setKey("fitnesse.slimTables.SlimTableFactory");
            var.setValue(slimTableFactory);
            addSysproperty(var);
        }

        StringBuilder filterLine = new StringBuilder();
        filterLine.append(suiteName);
        filterLine.append("?suite&nohistory&format=text");
        for (SuiteFilter filter : suiteFilters) {
            filterLine.append("&suiteFilter=").append(filter.getFilterName());
        }

        createArg().setLine("-c");
        createArg().setLine(filterLine.toString());
        createArg().setLine("-p");
        createArg().setLine(port.toString());
        createArg().setLine("-r");
        createArg().setLine("fitnesse");

        super.execute();
    }

}
