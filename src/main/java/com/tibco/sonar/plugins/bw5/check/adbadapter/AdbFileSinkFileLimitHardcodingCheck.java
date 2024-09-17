package com.tibco.sonar.plugins.bw5.check.adbadapter;

import com.tibco.utils.common.helper.XmlHelper;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.sonar.plugins.bw5.check.AbstractXmlCheck;
import com.tibco.sonar.plugins.bw5.check.CheckConstants;
import com.tibco.sonar.plugins.bw5.check.activity.catcherror.CatchAllCheck;
import com.tibco.sonar.plugins.bw5.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw5.source.XmlBw5Source;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

@Rule(key = AdbFileSinkFileLimitHardcodingCheck.RULE_KEY, name = CheckConstants.RULE_ADBADAPTER_ADBFILESINKFILELIMITHARDCODINGCHECK_NAME, description = CheckConstants.RULE_ADBADAPTER_ADBFILESINKFILELIMITHARDCODINGCHECK_DESCRIPTION, priority = Priority.CRITICAL, tags = { "bug" })
@BelongsToProfile(title = BWProcessQualityProfile.PROFILE_NAME, priority = Priority.CRITICAL)
public class AdbFileSinkFileLimitHardcodingCheck extends AbstractXmlCheck {

    private static final Logger LOG = Loggers.get(CatchAllCheck.class);
	public static final String RULE_KEY = "AdbFileSinkFileLimitHardcodingCheck";
	public static final String FILESINK_ELEMENT_NAME = "fileSink";
	public static final String FILESINK_ELEMENT_NAMESPACE = "http://www.tibco.com/xmlns/aemeta/adapter/2002";
	public static final String FILE_LIMIT_ELEMENT_NAME = "fileLimit";
	public static final String FILE_LIMIT_ELEMENT_DESCRIPTION = "Hardcoded File Limit in FileSink of Adb Adapter";

	@Override
	protected void validate(XmlBw5Source xmlSource) {
		Document document = xmlSource.getDocument(true);
		try {
			Element fileSinkElement = XmlHelper.firstChildElement(document.getDocumentElement(),
					FILESINK_ELEMENT_NAMESPACE, FILESINK_ELEMENT_NAME);
			xmlSource.findAndValidateHardCodedChild(getRuleKey(), fileSinkElement, FILE_LIMIT_ELEMENT_NAME,
					FILE_LIMIT_ELEMENT_DESCRIPTION);
		} catch (Exception e) {			
                        reportIssueOnFile(FILE_LIMIT_ELEMENT_DESCRIPTION);
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