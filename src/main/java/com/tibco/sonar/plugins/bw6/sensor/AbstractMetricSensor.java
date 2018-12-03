package com.tibco.sonar.plugins.bw6.sensor;

import org.sonar.api.utils.log.*;
import org.sonar.api.batch.sensor.SensorContext;

import org.sonar.api.batch.fs.InputModule;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.rule.CheckFactory;

/**
 * XmlSensor provides analysis of xml files.
 * 
 * @author Kapil Shivarkar
 */
public abstract class AbstractMetricSensor extends AbstractSensor {
	
	private static final Logger LOGGER = Loggers.get(AbstractMetricSensor.class);
	
	protected AbstractMetricSensor(FileSystem fileSystem, String languageKey, CheckFactory checkFactory) {
		super(fileSystem, languageKey, checkFactory);
	}

	/**
	 * Analyze the XML files.
	 */
	public void analyse(InputModule project, SensorContext sensorContext) {
		for (java.io.File file : fileSystem.files(fileSystem.predicates().hasLanguage(languageKey))) {
			try {
				analyseFile(file);
			} catch (Exception e) {
				LOGGER.error(
						"Could not analyze the file " + file.getAbsolutePath(),e);
			}
		}
	}

}
