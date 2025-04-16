package com.tibco.sonar.plugins.bw5.computers;

import static com.tibco.sonar.plugins.bw5.metric.BusinessWorksMetrics.ACTIVITIES;

public class ActivitiesMeasureComputer extends AbstractResourceTotals {

    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
        return this.defineMeasure(def, ACTIVITIES);
    }

    @Override
    public void compute(MeasureComputerContext context) {
        this.computeMeasure(context, ACTIVITIES);
    }
}
