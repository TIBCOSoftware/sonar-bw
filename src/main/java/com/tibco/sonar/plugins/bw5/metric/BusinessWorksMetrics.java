package com.tibco.sonar.plugins.bw5.metric;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;


import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;
import org.sonar.api.utils.SonarException;



public final class BusinessWorksMetrics implements Metrics {

    public static final String BWLANGUAGEFLAG_KEY = "isbwproject";
    public static final Metric BWLANGUAGEFLAG = new Metric.Builder(BWLANGUAGEFLAG_KEY,
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
    public static final Metric GLOBALVARIABLES = new Metric.Builder(GLOBALVARIABLES_KEY,
            "Global Variables", Metric.ValueType.INT)
            .setDescription("Total number of global variables")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_SIZE).create();

    /*
	 * 
	 * BW resources metrics
	 * 
     */
    public static final String BWRESOURCES_HTTP_CONNECTION_KEY = "httpconnection";
    public static final Metric BWRESOURCES_HTTP_CONNECTION = new Metric.Builder(BWRESOURCES_HTTP_CONNECTION_KEY,
            "HTTP Connections", Metric.ValueType.INT)
            .setDescription("Total of shared HTTP connection resources")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_SIZE).create();

    public static final String BWRESOURCES_JDBC_CONNECTION_KEY = "jdbcconnection";
    public static final Metric BWRESOURCES_JDBC_CONNECTION = new Metric.Builder(BWRESOURCES_JDBC_CONNECTION_KEY,
            "JDBC Connections", Metric.ValueType.INT)
            .setDescription("Total of shared JDBC connection resources")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_SIZE).create();

    public static final String BWRESOURCES_JMS_CONNECTION_KEY = "jmsconnection";
    public static final Metric BWRESOURCES_JMS_CONNECTION = new Metric.Builder(BWRESOURCES_JMS_CONNECTION_KEY,
            "JMS Connections", Metric.ValueType.INT)
            .setDescription("Total of shared JMS connection resources")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_SIZE).create();

    public static final String BWRESOURCES_KEY = "bwresources";
    public static final Metric[] BWRESOURCES_METRICS_LIST = {
        BWRESOURCES_JDBC_CONNECTION,
        BWRESOURCES_JMS_CONNECTION,
        BWRESOURCES_HTTP_CONNECTION
    };
    public static final Metric BWRESOURCES = new Metric.Builder(BWRESOURCES_KEY,
            "Resources", Metric.ValueType.INT)
            .setDescription("Number of BusinessWorks resources")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_SIZE).create();

    /*
	 * 
	 * 
	 * Processes metrics
	 * 
     */
    public static final String PROCESSES_KEY = "processes";
    public static final Metric PROCESSES = new Metric.Builder(PROCESSES_KEY,
            "Processes", Metric.ValueType.INT)
            .setDescription("Number of processes")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_SIZE).create();

    public static final String GROUPS_KEY = "groups";
    public static final Metric GROUPS = new Metric.Builder(GROUPS_KEY,
            "Groups", Metric.ValueType.INT).setDescription("Number of groups")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_SIZE).create();

    public static final String ACTIVITIES_KEY = "activities";
    public static final Metric ACTIVITIES = new Metric.Builder(ACTIVITIES_KEY,
            "Activities", Metric.ValueType.INT)
            .setDescription("Number of activities")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_SIZE).create();

    public static final String TRANSITIONS_KEY = "transitions";
    public static final Metric TRANSITIONS = new Metric.Builder(
            TRANSITIONS_KEY, "Transitions", Metric.ValueType.INT)
            .setDescription("Number of transitions")
            .setDirection(Metric.DIRECTION_WORST).setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_SIZE).create();

    private static final List<Metric> METRICS;

    static {
        METRICS = new LinkedList();
        for (Field field : BusinessWorksMetrics.class.getFields()) {
            if (Metric.class.isAssignableFrom(field.getType())) {
                try {
                    Metric metric = (Metric) field.get(null);
                    METRICS.add(metric);
                } catch (IllegalAccessException e) {
                    throw new SonarException("can not introspect "
                            + CoreMetrics.class + " to get metrics", e);
                }
            }
        }
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
