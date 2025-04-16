package com.tibco.sonar.plugins.bw5.computers;


import static com.tibco.sonar.plugins.bw5.metric.BusinessWorksMetrics.TRANSITIONS;

public class TransitionsMeasureComputer extends AbstractResourceTotals {

    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
        return this.defineMeasure(def, TRANSITIONS);
    }

    @Override
    public void compute(MeasureComputerContext context) {
        this.computeMeasure(context, TRANSITIONS);
    }
}
