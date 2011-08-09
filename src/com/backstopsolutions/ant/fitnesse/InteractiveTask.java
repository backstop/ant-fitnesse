package com.backstopsolutions.ant.fitnesse;

import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Environment;

/**
 * Created by IntelliJ IDEA.
 * User: nriffe
 * Date: 7/28/11
 * Time: 2:52 PM
 */
public class InteractiveTask extends Java {

    private static final int DEFAULT_PORT = 9123;
    
    private int port;
    private String slimTableFactory;
    private String integrationTestPath;

    public InteractiveTask() {
        setClassname("fitnesseMain.FitNesseMain");
        setFailonerror(true);
        setFork(true);
        integrationTestPath = "fitnesse";
        port = DEFAULT_PORT;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSlimTableFactory() {
        return slimTableFactory;
    }

    public void setSlimTableFactory(String slimTableFactory) {
        this.slimTableFactory = slimTableFactory;
    }

    public String getIntegrationTestPath() {
        return integrationTestPath;
    }

    public void setIntegrationTestsPath(String integrationTestPath) {
        this.integrationTestPath = integrationTestPath;
    }

    @Override
    public void execute() {
        if (getSlimTableFactory() != null) {
            Environment.Variable var = new Environment.Variable();
            var.setKey("fitnesse.slimTables.SlimTableFactory");
            var.setValue(getSlimTableFactory());
            addSysproperty(var);
        }

        createArg().setLine("-p");
        createArg().setLine(Integer.toString(getPort()));
        createArg().setLine("-r");
        createArg().setLine(getIntegrationTestPath());
//        createArg().setLine("-o");
        createArg().setLine("-e");
        createArg().setLine("0");

        super.execute();
    }
}
