/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.measurecomputer.resources;

import static com.tibco.sonar.plugins.bw6.metric.SharedResourceMetrics.BWRESOURCES_FTL_REALM_SERVER_CONNECTION;

public class ComputeFTLRealmConnectionResource extends AbstractResourceTotals {

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
    return this.defineMeasure(def, BWRESOURCES_FTL_REALM_SERVER_CONNECTION);
  }

  @Override
  public void compute(MeasureComputerContext context) {
      this.computeMeasure(context, BWRESOURCES_FTL_REALM_SERVER_CONNECTION);
  }
}