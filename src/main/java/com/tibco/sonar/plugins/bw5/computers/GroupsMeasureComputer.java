package com.tibco.sonar.plugins.bw5.computers;



import static com.tibco.sonar.plugins.bw5.metric.BusinessWorksMetrics.GROUPS;

public class GroupsMeasureComputer extends AbstractResourceTotals {

    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
        return this.defineMeasure(def, GROUPS);
    }

    @Override
    public void compute(MeasureComputerContext context) {
        this.computeMeasure(context, GROUPS);
    }
}
