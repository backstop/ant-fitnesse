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
