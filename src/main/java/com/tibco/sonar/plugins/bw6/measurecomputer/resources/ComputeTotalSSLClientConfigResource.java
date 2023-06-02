/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.measurecomputer.resources;

import static com.tibco.sonar.plugins.bw6.metric.SharedResourceMetrics.BWRESOURCES_SSL_CLIENT_CONFIGURATION;


public class ComputeTotalSSLClientConfigResource extends AbstractResourceTotals {

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
    return this.defineMeasure(def, BWRESOURCES_SSL_CLIENT_CONFIGURATION);
  }

  @Override
  public void compute(MeasureComputerContext context) {
     this.computeMeasure(context, BWRESOURCES_SSL_CLIENT_CONFIGURATION);
  }
}
