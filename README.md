Purpose
=======

The standard way to run [Fitnesse](http://fitnesse.org/) in an ant build script is:

    <target name="my_fitnesse_tests">
      <java jar="dist/fitnesse.jar" failonerror="true" fork="true">
        <arg value="-c"/>
        <arg value="FitNesse.MySuitePage?suite&amp;format=text"/>
        <arg value="-p"/>
        <arg value="9234"/>
      </java>
    </target>

This works for simple projects, but there can be a lot of copy and paste if you want to run the same suite with different parameters (filters, etc.)
This project lets you run Fitnesse interactively or just run tests as part of a build, potentially in parallel.

Usage
=====

Define the task:

    <path id="ant.fitnesse.classpath">
        <fileset dir="FillThisIn" includes="ant-fitnesse-*.jar"/>
    </path>
    <taskdef resource="com/backstopsolutions/ant/fitnesse/tasks.xml"
             classpathref="ant.fitnesse.classpath" uri="antlib:com.backstopsolutions.ant.fitnesse"/>

Run interactively:

    <target name="example-interactive">
        <fit:interactive port="9234" classpathref="integration.classpath"/>
    </target>

Run tests in the background as part of a build:

    <fit:suites id="fullSuite">
        <fit:suite name="BlortSuite"/>
        <fit:suite name="DingusSuite.BigThingsSuite"/>
        <fit:suite name="DingusSuite.LittleThingsSuite"/>
    </fit:suites>

    <target name="example-test">
        <mkdir dir="${build}/integration/results"/>
        <fit:test port="24242" classPathRef="integration.classpath" concurrentsuites="5"
                  resultPath="${build}/integration/results" integrationTestsPath="integration-tests">
            <fit:filter filterName="quicktest"/>
            <fit:suites refid="fullSuite"/>
        </fit:test>
    </target>
