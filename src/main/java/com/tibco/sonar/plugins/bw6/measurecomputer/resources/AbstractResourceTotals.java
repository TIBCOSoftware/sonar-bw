/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
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
  