/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.metric;

import java.util.LinkedList;
import java.util.List;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;




public final class BusinessWorksMetrics implements Metrics {

    private static final String MEASURES_DOMAIN = "BW Resources";

    public static final String BWLANGUAGEFLAG_KEY = "isbwproject";
    public static final Metric<Integer> BWLANGUAGEFLAG = new Metric.Builder(BWLANGUAGEFLAG_KEY,
            "TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
            .setDescription("Equals true if the resource is a TIBCO BusinessWorks project or module")
            .setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

    /*
	 * 
	 * Global variables metrics
	 * 
     */
    public static final String GLOBALVARIABLES_KEY = "globalvariables";
    public static final Metric<Integer> GLOBALVARIABLES = new Metric.Builder(GLOBALVARIABLES_KEY,
            "BW5 .x Global Variables", Metric.ValueType.INT)
            .setDescription("Total number of global variables")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(MEASURES_DOMAIN).create();

    /*
	 * 
	 * BW resources metrics
	 * 
     */
    public static final String BWRESOURCES_HTTP_CONNECTION_KEY = "httpconnection";
    public static final Metric<Integer> BWRESOURCES_HTTP_CONNECTION = new Metric.Builder(BWRESOURCES_HTTP_CONNECTION_KEY,
            "BW 5.x HTTP Connections", Metric.ValueType.INT)
            .setDescription("Total of shared HTTP connection resources")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(MEASURES_DOMAIN).create();

    public static final String BWRESOURCES_JDBC_CONNECTION_KEY = "jdbcconnection";
    public static final Metric<Integer> BWRESOURCES_JDBC_CONNECTION = new Metric.Builder(BWRESOURCES_JDBC_CONNECTION_KEY,
            "BW 5.x JDBC Connections", Metric.ValueType.INT)
            .setDescription("Total of shared JDBC connection resources")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(MEASURES_DOMAIN).create();

    public static final String BWRESOURCES_JMS_CONNECTION_KEY = "jmsconnection";
    public static final Metric<Integer> BWRESOURCES_JMS_CONNECTION = new Metric.Builder(BWRESOURCES_JMS_CONNECTION_KEY,
            "BW 5.x JMS Connections", Metric.ValueType.INT)
            .setDescription("Total of shared JMS connection resources")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(MEASURES_DOMAIN).create();

    public static final String BWRESOURCES_KEY = "bwresources";
    protected static final Metric[] BWRESOURCES_METRICS_LIST = {
        BWRESOURCES_JDBC_CONNECTION,
        BWRESOURCES_JMS_CONNECTION,
        BWRESOURCES_HTTP_CONNECTION
    };
    public static final Metric<Integer> BWRESOURCES = new Metric.Builder(BWRESOURCES_KEY,
            "BW 5.x Resources", Metric.ValueType.INT)
            .setDescription("Number of BusinessWorks resources")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(MEASURES_DOMAIN).create();

    /*
	 * 
	 * 
	 * Processes metrics
	 * 
     */
    public static final String PROCESSES_KEY = "processes";
    public static final Metric<Integer> PROCESSES = new Metric.Builder(PROCESSES_KEY,
            "BW 5.x Processes", Metric.ValueType.INT)
            .setDescription("Number of processes")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(MEASURES_DOMAIN).setHidden(false).create();

    public static final String GROUPS_KEY = "groups";
    public static final Metric<Integer> GROUPS = new Metric.Builder(GROUPS_KEY,
            "BW 5.x Groups", Metric.ValueType.INT).setDescription("Number of groups")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(MEASURES_DOMAIN).create();

    public static final String ACTIVITIES_KEY = "activities";
    public static final Metric<Integer> ACTIVITIES = new Metric.Builder(ACTIVITIES_KEY,
            "BW 5.x Activities", Metric.ValueType.INT)
            .setDescription("Number of activities")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(MEASURES_DOMAIN).create();

    public static final String TRANSITIONS_KEY = "transitions";
    public static final Metric<Integer> TRANSITIONS = new Metric.Builder(
            TRANSITIONS_KEY, "BW 5.x Transitions", Metric.ValueType.INT)
            .setDescription("Number of transitions")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(MEASURES_DOMAIN).create();

    private static final List<Metric> METRICS;

    static {
        METRICS = new LinkedList<>();
        METRICS.add(BWLANGUAGEFLAG);

        // Global Variables
        METRICS.add(GLOBALVARIABLES);

        // BW Resources
        METRICS.add(BWRESOURCES_HTTP_CONNECTION);
        METRICS.add(BWRESOURCES_JDBC_CONNECTION);
        METRICS.add(BWRESOURCES_JMS_CONNECTION);
        METRICS.add(BWRESOURCES);

        // Processes
        METRICS.add(PROCESSES);
        METRICS.add(GROUPS);
        METRICS.add(ACTIVITIES);
        METRICS.add(TRANSITIONS);
    }


    public List<Metric> getMetrics() {
        return METRICS;
    }

    public static Metric getMetric(final String key) {
        for (Metric m : METRICS) {
            if(m != null && m.getKey().equals(key)){
                return m;
            }
        }
        return null;
    }

}
