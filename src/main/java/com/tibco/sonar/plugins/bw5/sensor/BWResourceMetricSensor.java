/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.sensor;

import com.tibco.sonar.plugins.bw5.language.BusinessWorks5Language;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import com.tibco.sonar.plugins.bw5.language.SharedHttp;
import com.tibco.sonar.plugins.bw5.language.SharedJdbc;
import com.tibco.sonar.plugins.bw5.language.SharedJms;
import com.tibco.sonar.plugins.bw5.metric.BusinessWorksMetrics;
import org.sonar.api.batch.sensor.Sensor;

import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.measures.Metric;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class BWResourceMetricSensor implements Sensor {

    private final static Logger LOG = Loggers.get(BWResourceMetricSensor.class);

    protected FileSystem fileSystem;
  
    
    private final List<SimpleEntry<String, Metric>> resourceLanguageKeys = new ArrayList<>();

    
    private org.sonar.api.batch.sensor.SensorContext sensorContext;    
    private final FilePredicate mainFilesPredicate;

    public BWResourceMetricSensor(FileSystem fileSystem,
            CheckFactory checkFactory) {
        LOG.debug("ProcessRuleSensor - START");
        resourceLanguageKeys.add(new SimpleEntry<>(SharedHttp.KEY, BusinessWorksMetrics.BWRESOURCES_HTTP_CONNECTION));
        resourceLanguageKeys.add(new SimpleEntry<>(SharedJms.KEY, BusinessWorksMetrics.BWRESOURCES_JMS_CONNECTION));
        resourceLanguageKeys.add(new SimpleEntry<>(SharedJdbc.KEY, BusinessWorksMetrics.BWRESOURCES_JDBC_CONNECTION));

        this.fileSystem = fileSystem;        
        this.mainFilesPredicate = fileSystem.predicates().and(
                fileSystem.predicates().hasLanguage(BusinessWorks5Language.KEY));
        LOG.debug("ProcessRuleSensor - END");
    }


    @Override
    public void describe(SensorDescriptor descriptor) {
       
    }

    @Override
    public void execute(org.sonar.api.batch.sensor.SensorContext context) {
        this.sensorContext = context;
        try{
        resourceLanguageKeys.forEach((entry) -> {
            for (InputFile file : fileSystem.inputFiles(mainFilesPredicate)) {
                sensorContext.<Integer>newMeasure()
                        .forMetric(entry.getValue())
                        .on(file)
                        .withValue(1)
                        .save();

            }
        });
        }catch(Exception ex){
            LOG.error(ex.getMessage());
        }

    }
}
