/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.measurecomputer.resources;

import static com.tibco.sonar.plugins.bw6.metric.SharedResourceMetrics.BWRESOURCES_PROXY_CONFIGURATION;


public class ComputeProxyConfigResource extends AbstractResourceTotals {

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
    return this.defineMeasure(def, BWRESOURCES_PROXY_CONFIGURATION);
  }

  @Override
  public void compute(MeasureComputerContext context) {
      this.computeMeasure(context, BWRESOURCES_PROXY_CONFIGURATION);
  }
}