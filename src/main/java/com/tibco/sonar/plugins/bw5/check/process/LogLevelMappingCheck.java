package com.tibco.sonar.plugins.bw5.check.process;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.tibco.sonar.plugins.bw5.check.AbstractXmlCheck;
import com.tibco.sonar.plugins.bw5.check.CheckConstants;
import com.tibco.sonar.plugins.bw5.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw5.source.XmlBw5Source;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = LogLevelMappingCheck.RULE_KEY, name = CheckConstants.RULE_PROCESS_LOGLEVELMAPPINGCHECK_NAME, description = CheckConstants.RULE_PROCESS_LOGLEVELMAPPINGCHECK_DESCRIPTION, priority = Priority.CRITICAL, tags = { "bug" })
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.CRITICAL)
public class LogLevelMappingCheck extends AbstractXmlCheck {

    private static final Logger LOG = Loggers.get(LogLevelMappingCheck.class);
	public static final String RULE_KEY = "LogLevelMappingCheck";
	public static final String VALUE_OF_ELEMENT_NAME = "value-of";
	public static final String VALUE_OF_ELEMENT_NAMESPACE = "http://www.w3.org/1999/XSL/Transform";
	public static final String LOG_LEVEL_ELEMENT_DESCRIPTION = "Log Level element should be mapped with ServiceLogLevel only";

	@Override
	protected void validate(XmlBw5Source xmlSource) {
		Document document = xmlSource.getDocument(true);
		try {
			NodeList nodeList = document.getDocumentElement().getElementsByTagNameNS(VALUE_OF_ELEMENT_NAMESPACE,
					VALUE_OF_ELEMENT_NAME);
			for (int index = 0; index < nodeList.getLength(); index++) {
				if (nodeList.item(index).getParentNode().getNodeName().contains("LogLevel")) {
					Element logLevelElement = (Element) nodeList.item(index);
					if (!logLevelElement.getAttribute("select").contains("ServiceLogLevel/")) {						
                                                reportIssueOnFile(LOG_LEVEL_ELEMENT_DESCRIPTION,xmlSource.getLineForNode(logLevelElement));
					}
				}
			}
		} catch (Exception e) {			
                        reportIssueOnFile(LOG_LEVEL_ELEMENT_DESCRIPTION);
		}

	}
        
        
    @Override
    public String getRuleKeyName() {
        return RULE_KEY;
    }

    @Override
    public Logger getLogger() {
        return LOG;
    }

}
