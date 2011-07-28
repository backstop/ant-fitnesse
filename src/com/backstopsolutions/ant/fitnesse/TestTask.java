package com.backstopsolutions.ant.fitnesse;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Echo;
import org.apache.tools.ant.taskdefs.Parallel;
import org.apache.tools.ant.taskdefs.Sequential;
import org.apache.tools.ant.taskdefs.Sleep;
import org.apache.tools.ant.types.Reference;

import java.io.File;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nriffe
 * Date: 7/28/11
 * Time: 11:56 AM
 */
public class TestTask extends Parallel {

    private int port;
    private Reference classpathRef;
    private Suites suites = new Suites(this);
    private File resultPath;

    public TestTask() {
    }

    @Override
    public void execute() throws BuildException {
        addDaemons(initDaemons(getProject(), getPort(), getClasspathRef()));
        Sequential runnerSequence = new Sequential();
        runnerSequence.setProject(getProject());
        runnerSequence.setTaskName("sequential");
        runnerSequence.addTask(initSleep(getProject(), 10));
        runnerSequence.addTask(initRunners(getProject(), suites.getSuiteNames(), getPort(), getClasspathRef(), getResultPath()));
        addTask(runnerSequence);

        super.execute();
    }

    private static Task debug(Project project, String message) {
        Echo echo = new Echo();
        echo.setProject(project);
        echo.setTaskName("echo");
        echo.setMessage("DEBUG: " + message);
        return echo;
    }

    private static TaskList initDaemons(Project project, int port, Reference classpathRef) {
        TaskList daemonList = new TaskList();
        InteractiveTask fitnesseTask = new InteractiveTask();
        fitnesseTask.setTaskName("fitnesse");
        fitnesseTask.setProject(project);
        fitnesseTask.setPort(port);
        fitnesseTask.setClasspathRef(classpathRef);
        daemonList.addTask(fitnesseTask);
        return daemonList;
    }

    private static Task initRunners(Project project, Set<String> suiteNames, int port, Reference classpathRef, File resultPath) {
        Parallel runners = new Parallel();
        runners.setProject(project);
        runners.setTaskName("parallel");
        runners.setFailOnAny(true);
        runners.addTask(initSleep(project, 30));
        for (String suiteName : suiteNames) {
            RunnerTask runner = new RunnerTask();
            runner.setProject(project);
            runner.setTaskName("runner");
            runner.setHost("localhost");
            runner.setPort(port);
            runner.setSuite(suiteName);
            runner.setResultPath(resultPath);
            runners.addTask(runner);
        }
        return runners;
    }

    private static Sleep initSleep(Project project, int seconds) {
        Sleep sleep = new Sleep();
        sleep.setProject(project);
        sleep.setTaskName("sleep");
        sleep.setSeconds(seconds);
        return sleep;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Reference getClasspathRef() {
        return classpathRef;
    }

    public void setClasspathRef(Reference classpathRef) {
        this.classpathRef = classpathRef;
    }

    public File getResultPath() {
        return resultPath;
    }

    public void setResultPath(File resultPath) {
        this.resultPath = resultPath;
    }

    public Suites createSuites() {
        return suites;
    }
}
