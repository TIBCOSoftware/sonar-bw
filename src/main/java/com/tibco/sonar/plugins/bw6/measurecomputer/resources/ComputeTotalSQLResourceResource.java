package com.tibco.sonar.plugins.bw6.measurecomputer.resources;

import static com.tibco.sonar.plugins.bw6.metric.SharedResourceMetrics.BWRESOURCES_SQL_FILE;


public class ComputeTotalSQLResourceResource extends AbstractResourceTotals {

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
    return this.defineMeasure(def, BWRESOURCES_SQL_FILE);
  }

  @Override
  public void compute(MeasureComputerContext context) {
     this.computeMeasure(context, BWRESOURCES_SQL_FILE);
  }
}
