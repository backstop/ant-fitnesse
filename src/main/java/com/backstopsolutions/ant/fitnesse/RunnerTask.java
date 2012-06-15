/* ==========================================================
 * ant-fitnesse
 * https://github.com/backstop/ant-fitnesse
 * ==========================================================
 * Copyright 2012 Backstop Solutions Group, LLC.
 * Authors: George Shakhnazaryan (gshakhnazaryan@backstopsolutions.com), Nate Riffe (nriffe@backstopsolutions.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */

package com.backstopsolutions.ant.fitnesse;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Get;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Override
    public void execute() {
        setDest(getDest(getResultPath(), getSuite()));

        StringBuilder uri = new StringBuilder();
        uri.append("/");
        uri.append(getSuite());
        uri.append("?suite");
        uri.append("&format=xml");

        for (SuiteFilter filter : getSuiteFilters()) {
            uri.append("&suiteFilter=");
            uri.append(filter.getFilterName());
        }

        try {
            setSrc(new URL("http", getHost(), getPort(), uri.toString()));
        } catch (MalformedURLException e) {
            throw new BuildException(e);
        }

        SimpleDateFormat tstamp = new SimpleDateFormat ("HH:mm:ss");
        log(tstamp.format(new Date()) + " - Starting suite " + getSuite());
        super.execute();
        log(tstamp.format(new Date()) + " - Ending suite " + getSuite());
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
}
