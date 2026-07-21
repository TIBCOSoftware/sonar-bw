package com.tibco.sonar.plugins.bw5.computers;



import static com.tibco.sonar.plugins.bw5.metric.BusinessWorksMetrics.GLOBALVARIABLES;

public class GlobalVariablesMeasureComputer  extends AbstractResourceTotals {

    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
        return this.defineMeasure(def, GLOBALVARIABLES);
    }

    @Override
    public void compute(MeasureComputerContext context) {
        this.computeMeasure(context, GLOBALVARIABLES);
    }
}
