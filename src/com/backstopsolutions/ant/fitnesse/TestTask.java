package com.backstopsolutions.ant.fitnesse;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Parallel;
import org.apache.tools.ant.taskdefs.Sequential;
import org.apache.tools.ant.taskdefs.Sleep;
import org.apache.tools.ant.types.Reference;

import java.io.File;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nriffe
 * Date: 7/28/11
 * Time: 11:56 AM
 */
public class TestTask extends Task {

    private static final int DEFAULT_CONCURRENT_SUITES = 1;
    private static final long DEFAULT_MAX_TIME_FOR_SUITE = 60 * 15;
    private static final int SECONDS_TO_WAIT_FOR_WEBSERVER_TO_START = 10;

    private int port;
    private Reference classpathRef;
    private Suites suites = new Suites();
    private List<SuiteFilter> filters = new ArrayList<SuiteFilter>();
    private File resultPath;
    private int concurrentSuites;
    private String integrationTestsPath;
    private String slimTableFactory;
    private long maxTimeForSuite;

    public TestTask() {
        concurrentSuites = DEFAULT_CONCURRENT_SUITES;
        integrationTestsPath = "fitnesse";
        maxTimeForSuite = DEFAULT_MAX_TIME_FOR_SUITE;
    }

    @Override
    public void execute() {
        Parallel container = new Parallel();
        container.setProject(getProject());
        container.setTaskName("parallel");
        container.addDaemons(initDaemons(getProject(), getPort(), getClasspathRef(), getIntegrationTestsPath(), getSlimTableFactory()));
        container.addTask(initSequence(getProject(), getPort(), getResultPath(), getSuiteNames(suites), filters, getConcurrentSuites(), getMaxTimeForSuite()));
        container.execute();
    }

    private static Set<String> getSuiteNames(Suites suites) {
        Set<String> names = new HashSet<String>();
        Iterator suitie = suites.iterator();
        while (suitie.hasNext()) {
            Suite suite = (Suite) suitie.next();
            names.add(suite.getName());
        }
        return names;
    }

    private static Sequential initSequence(Project project, int port, File resultPath, Set<String> suiteNames, List<SuiteFilter> suiteFilters, int concurrentSuites, long maxTimeForSuite) {
        Sequential runnerSequence = new Sequential();
        runnerSequence.setProject(project);
        runnerSequence.setTaskName("sequential");
        runnerSequence.addTask(initSleep(project, SECONDS_TO_WAIT_FOR_WEBSERVER_TO_START));
        runnerSequence.addTask(initRunners(project, suiteNames, port, resultPath, suiteFilters, concurrentSuites, maxTimeForSuite));
        return runnerSequence;
    }

    private static Parallel.TaskList initDaemons(Project project, int port, Reference classpathRef, String integrationTestsPath, String slimTableFactory) {
        Parallel.TaskList daemonList = new Parallel.TaskList();
        InteractiveTask fitnesseTask = new InteractiveTask();
        fitnesseTask.setTaskName("fitnesse");
        fitnesseTask.setProject(project);
        fitnesseTask.setPort(port);
        fitnesseTask.setClasspathRef(classpathRef);
        fitnesseTask.setIntegrationTestsPath(integrationTestsPath);
        fitnesseTask.setSlimTableFactory(slimTableFactory);
        daemonList.addTask(fitnesseTask);
        return daemonList;
    }

    private static Task initRunners(Project project, Set<String> suiteNames, int port, File resultPath, List<SuiteFilter> suiteFilters, int concurrentSuites, long maxTimeForSuite) {
        Parallel runners = new Parallel();
        runners.setProject(project);
        runners.setTaskName("parallel");
        runners.setFailOnAny(true);
        runners.setThreadCount(concurrentSuites);
        for (String suiteName : suiteNames) {
            RunnerTask runner = new RunnerTask();
            runner.setProject(project);
            runner.setTaskName("runner");
            runner.setHost("localhost");
            runner.setPort(port);
            runner.setSuite(suiteName);
            runner.setResultPath(resultPath);
            runner.setSuiteFilters(suiteFilters);
            runner.setMaxTime(maxTimeForSuite);
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

    public String getIntegrationTestsPath() {
        return integrationTestsPath;
    }

    public void setIntegrationTestsPath(String integrationTestsPath) {
        this.integrationTestsPath = integrationTestsPath;
    }

    public String getSlimTableFactory() {
        return slimTableFactory;
    }

    public void setSlimTableFactory(String slimTableFactory) {
        this.slimTableFactory = slimTableFactory;
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

    public SuiteFilter createFilter() {
        SuiteFilter suiteFilter = new SuiteFilter();
        filters.add(suiteFilter);
        return suiteFilter;
    }

    public int getConcurrentSuites() {
        return concurrentSuites;
    }

    public void setConcurrentSuites(int concurrentSuites) {
        if (concurrentSuites < 1) {
            throw new IllegalArgumentException("concurrentSuites must be at least 1");
        }
        this.concurrentSuites = concurrentSuites;
    }

    public long getMaxTimeForSuite() {
        return maxTimeForSuite;
    }

    public void setMaxTimeForSuite(long maxTimeForSuite) {
        this.maxTimeForSuite = maxTimeForSuite;
    }
}
