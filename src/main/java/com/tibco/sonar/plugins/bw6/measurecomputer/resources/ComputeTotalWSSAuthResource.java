/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.measurecomputer.resources;

import static com.tibco.sonar.plugins.bw6.metric.SharedResourceMetrics.BWRESOURCES_WSS_AUTHENTICATION;


public class ComputeTotalWSSAuthResource extends AbstractResourceTotals {

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
    return this.defineMeasure(def, BWRESOURCES_WSS_AUTHENTICATION);
  }

  @Override
  public void compute(MeasureComputerContext context) {
     this.computeMeasure(context, BWRESOURCES_WSS_AUTHENTICATION);
  }
}