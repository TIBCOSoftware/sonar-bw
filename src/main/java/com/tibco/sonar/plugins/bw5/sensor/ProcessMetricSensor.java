/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.sensor;

import com.tibco.sonar.plugins.bw5.language.BusinessWorks5Language;

import com.tibco.sonar.plugins.bw5.metric.BusinessWorksMetrics;
import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import java.util.ArrayList;
import java.util.List;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.measures.Metric;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class ProcessMetricSensor implements Sensor {

    private static final Logger LOG = Loggers.get(ProcessMetricSensor.class);

    protected FileSystem fileSystem;
    private final FilePredicate mainFilesPredicate;
    private SensorContext sensorContext;
    private InputFile processFileResource;

    public ProcessMetricSensor(FileSystem fileSystem) {
        LOG.debug("ProcessRuleSensor - START");

        this.fileSystem = fileSystem;
        this.mainFilesPredicate = fileSystem.predicates().and(
                fileSystem.predicates().hasLanguage(BusinessWorks5Language.KEY));
        LOG.debug("ProcessRuleSensor - END");
    }

    protected void analyseFile(InputFile file) {

        ProcessSource source = new ProcessSource(file);
        com.tibco.utils.bw5.model.Process process = source.getProcessModel();
        int groupsProcess = process.countAllGroups();
        int activitiesProcess = process.countAllActivities();
        int transitionsProcess = process.countAllTransitions();
        int processesProcess = 1;
        saveMeasure(BusinessWorksMetrics.PROCESSES, processesProcess);
        saveMeasure(BusinessWorksMetrics.GROUPS, groupsProcess);
        saveMeasure(BusinessWorksMetrics.ACTIVITIES, activitiesProcess);
        saveMeasure(BusinessWorksMetrics.TRANSITIONS, transitionsProcess);

    }

    private void saveMeasure(Metric metric, double value) {
        sensorContext.<Integer>newMeasure()
                .forMetric(metric)
                .on(processFileResource)
                .withValue(value)
                .save();
    }

    @Override
    public void describe(SensorDescriptor descriptor) {

    }

    @Override
    public void execute(org.sonar.api.batch.sensor.SensorContext context) {

        try {
            LOG.debug("execute - START");
            this.sensorContext = context;
            LOG.info("Starting ProcessRuleSensor");
            List<InputFile> inputFiles = new ArrayList<>();
            fileSystem.inputFiles(mainFilesPredicate).forEach(inputFiles::add);

            if (inputFiles.isEmpty()) {
                return;
            }

            LOG.info("Searching for BW5 PrcoessFiles");
            inputFiles.forEach(this::analyseFile);
            LOG.info("Completed Search of BW5 Resources");
            LOG.debug("execute - END");
        } catch (Exception ex) {

        }
    }

}
