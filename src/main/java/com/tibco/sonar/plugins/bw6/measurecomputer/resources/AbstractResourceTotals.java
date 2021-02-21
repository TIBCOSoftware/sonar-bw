/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.measurecomputer.resources;

import org.sonar.api.ce.measure.Measure;
import org.sonar.api.batch.measure.Metric;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.api.ce.measure.Component;


public abstract class AbstractResourceTotals implements MeasureComputer {


  
    public MeasureComputerDefinition defineMeasure(MeasureComputerDefinitionContext def,Metric<Integer> resourceMetric) {
      return def.newDefinitionBuilder()
        .setOutputMetrics(resourceMetric.key())
        .build();
    }
  
    public void computeMeasure (MeasureComputerContext context, Metric<Integer> resourceMetric ) {
    
      int sum=0;
      if (context.getComponent().getType() != Component.Type.FILE) {
        for (Measure child : context.getChildrenMeasures(resourceMetric.key())) {
          sum += child.getIntValue();
        }
        context.addMeasure(resourceMetric.key(), sum);
      }
    }
  }
  