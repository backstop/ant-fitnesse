package com.backstopsolutions.ant.fitnesse;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Get;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: nriffe
 * Date: 7/28/11
 * Time: 1:23 PM
 */
public class RunnerTask extends Get {
    private String suite;
    private int port;
    private String host;
    private File resultPath;

    @Override
    public void execute() throws BuildException {
        setDest(getDest(getResultPath(), getSuite()));

        StringBuilder uri = new StringBuilder();
        uri.append("/");
        uri.append(getSuite());
        uri.append("?suite");
        uri.append("&format=xml");

        try {
            setSrc(new URL("http", getHost(), getPort(), uri.toString()));
        } catch (MalformedURLException e) {
            throw new BuildException(e);
        }

        super.execute();
    }

    private File getDest(File resultPath, String suite) {
        return new File(resultPath, "FitTest_" + suite + ".xml");
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public File getResultPath() {
        return resultPath;
    }

    public void setResultPath(File resultPath) {
        this.resultPath = resultPath;
    }
}
