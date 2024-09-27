/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.sensor;

import com.tibco.sonar.plugins.bw5.settings.BW5LanguageFileSuffixProperty;
import com.tibco.sonar.plugins.bw5.metric.BusinessWorksMetrics;
import com.tibco.utils.common.SaxParser;
import com.tibco.utils.bw5.helper.GvHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.measures.Metric;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.w3c.dom.Document;

public class GlobalVariableMetricSensor implements Sensor {

    private static final Logger LOG = Loggers.get(GlobalVariableMetricSensor.class);
    protected FileSystem fileSystem;

    private final FilePredicate mainFilesPredicate;

    public GlobalVariableMetricSensor(FileSystem fileSystem) {
        LOG.debug("ProcessRuleSensor - START");

        this.fileSystem = fileSystem;
        this.mainFilesPredicate = fileSystem.predicates().and(
                fileSystem.predicates().hasExtension(BW5LanguageFileSuffixProperty.BW5_PROCESS_FILE_SUFFIX));

        LOG.debug("ProcessRuleSensor - END");

    }

    private SensorContext sensorContext;
    private InputFile substvarFile;

    protected void analyseFile(InputFile file) {
        substvarFile = file;
        Document xmlDoc;
        try {
            xmlDoc = new SaxParser().parseDocument(file.inputStream(), true);
            int globalVariables = GvHelper.countGV(xmlDoc);
            saveMeasure(BusinessWorksMetrics.GLOBALVARIABLES, globalVariables);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(GlobalVariableMetricSensor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void saveMeasure(Metric metric, double value) {
        sensorContext.<Double>newMeasure()
                .forMetric(metric)
                .on(substvarFile)
                .withValue(value)
                .save();
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        
    }

    @Override
    public void execute(SensorContext context) {

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
    }

}
