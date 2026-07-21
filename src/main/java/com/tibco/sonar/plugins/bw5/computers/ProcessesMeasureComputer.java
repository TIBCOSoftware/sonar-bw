package com.tibco.sonar.plugins.bw5.computers;


import static com.tibco.sonar.plugins.bw5.metric.BusinessWorksMetrics.PROCESSES;

public class ProcessesMeasureComputer extends AbstractResourceTotals {

    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
        return this.defineMeasure(def, PROCESSES);
    }

    @Override
    public void compute(MeasureComputerContext context) {
        this.computeMeasure(context, PROCESSES);
    }
}
