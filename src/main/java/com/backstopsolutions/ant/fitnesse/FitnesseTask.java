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
    public void execute() {
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
