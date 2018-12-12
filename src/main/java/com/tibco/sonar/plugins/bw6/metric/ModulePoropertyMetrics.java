package com.tibco.sonar.plugins.bw6.metric;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;
import java.util.List;
import static java.util.Arrays.asList;

public class ModulePoropertyMetrics implements Metrics{

	/*
	 * 
	 * Module Properties metrics
	 * 
	 */
	
	public static final String BWMP_GLOBALVARIABLES_KEY = "globalvariables";
	public static final Metric<Integer> BWMP_GLOBALVARIABLES = new Metric.Builder(BWMP_GLOBALVARIABLES_KEY,
			"Module Properties", Metric.ValueType.INT)
			.setDescription("Total number of module properties")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String BWMP_JOBSHAREDVARIABLES_KEY = "jobsharedvariables";
	public static final Metric<Integer> BWMP_JOBSHAREDVARIABLES = new Metric.Builder(BWMP_JOBSHAREDVARIABLES_KEY,
			"Job Shared Variables", Metric.ValueType.INT)
			.setDescription("Total number of job shared variables")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String BWMP_MODULESHAREDVARIABLES_KEY = "modulesharedvariables";
	public static final Metric<Integer> BWMP_MODULESHAREDVARIABLES = new Metric.Builder(BWMP_MODULESHAREDVARIABLES_KEY,
			"Module Shared Variables", Metric.ValueType.INT)
			.setDescription("Total number of module shared variables")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String BWMP_CATCHBLOCK_KEY = "catchblock";
	public static final Metric<Integer> BWMP_CATCHBLOCK = new Metric.Builder(BWMP_CATCHBLOCK_KEY,
			"Catch Blocks", Metric.ValueType.INT)
			.setDescription("Total number of catch blocks")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();
	
	public static final String BWMP_EVENTHANDLER_KEY = "eventhandler";
	public static final Metric<Integer> BWMP_EVENTHANDLER = new Metric.Builder(BWMP_EVENTHANDLER_KEY,
			"Event Handlers", Metric.ValueType.INT)
			.setDescription("Total number of event handlers")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(CoreMetrics.DOMAIN_SIZE).create();


    @Override
    public List<Metric> getMetrics() {
        return asList(
            BWMP_GLOBALVARIABLES,
            BWMP_JOBSHAREDVARIABLES,
            BWMP_MODULESHAREDVARIABLES,
            BWMP_CATCHBLOCK,
            BWMP_EVENTHANDLER
        );
    }
}
