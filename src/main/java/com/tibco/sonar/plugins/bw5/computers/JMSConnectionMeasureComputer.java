package com.tibco.sonar.plugins.bw5.computers;



import static com.tibco.sonar.plugins.bw5.metric.BusinessWorksMetrics.BWRESOURCES_JMS_CONNECTION;

public class JMSConnectionMeasureComputer extends AbstractResourceTotals {

    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
        return this.defineMeasure(def, BWRESOURCES_JMS_CONNECTION);
    }

    @Override
    public void compute(MeasureComputerContext context) {
        this.computeMeasure(context, BWRESOURCES_JMS_CONNECTION);
    }
}
