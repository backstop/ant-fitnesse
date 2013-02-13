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
        createArg().setLine("-e");
        createArg().setLine("0");

        super.execute();
    }
}
