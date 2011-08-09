package com.backstopsolutions.ant.fitnesse;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Get;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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
    private List<SuiteFilter> suiteFilters;
    private boolean saveHistory;

    @Override
    public void execute() {
        setDest(getDest(getResultPath(), getSuite()));

        StringBuilder uri = new StringBuilder();
        uri.append("/");
        uri.append(getSuite());
        uri.append("?suite");
        uri.append("&format=xml");

        if (!saveHistory) {
            uri.append("&nohistory");
        }

        for (SuiteFilter filter : getSuiteFilters()) {
            uri.append("&suiteFilter=");
            uri.append(filter.getFilterName());
        }

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

    public List<SuiteFilter> getSuiteFilters() {
        return suiteFilters;
    }

    public void setSuiteFilters(List<SuiteFilter> suiteFilters) {
        this.suiteFilters = suiteFilters;
    }

    public boolean isSaveHistory() {
        return saveHistory;
    }

    public void setSaveHistory(boolean saveHistory) {
        this.saveHistory = saveHistory;
    }
}
