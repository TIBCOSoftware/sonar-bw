package com.tibco.sonar.plugins.bw6.measurecomputer.resources;

import static com.tibco.sonar.plugins.bw6.metric.SharedResourceMetrics.BWRESOURCES_TRUST_PROVIDER;

public class ComputeTotalTrustProviderResource extends AbstractResourceTotals {

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
    return this.defineMeasure(def, BWRESOURCES_TRUST_PROVIDER);
  }

  @Override
  public void compute(MeasureComputerContext context) {
     this.computeMeasure(context, BWRESOURCES_TRUST_PROVIDER);
  }
}