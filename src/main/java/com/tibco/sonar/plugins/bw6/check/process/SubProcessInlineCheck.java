package com.tibco.sonar.plugins.bw6.check.process;

import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.profile.BWProcessQualityProfile;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.bw6.model.Service;

import java.util.Map;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

@Rule(key="SubProcessInlineCheck", name="Data Availability to Inline SubProcess Check", priority=Priority.INFO, description="This rule checks if there is large set of data being passed everytime to Inline SubProcess. Use of Job Shared Variable is recommended in this scenario to increase performance.")
@BelongsToProfile(title=BWProcessQualityProfile.PROFILE_NAME, priority=Priority.INFO )
public class SubProcessInlineCheck
  extends AbstractProcessCheck
{
    private static final Logger LOG = Loggers.get(SubProcessInlineCheck.class);
  public static final String RULE_KEY = "SubProcessInlineCheck";
  
  @Override
  protected void validate(ProcessSource processSource)
  {
    LOG.debug("Start validation for rule: " + RULE_KEY);
    Process process = processSource.getProcessModel();
    Map<String, Service> referenceServices = process.getProcessReferenceServices();
    referenceServices.keySet().stream().filter((referenceService) -> (referenceServices.get(referenceService).getInline().equals("true"))).map((referenceService) -> ((Service)referenceServices.get(referenceService)).getImplementationProcess()).map((proc) -> proc.substring(proc.lastIndexOf(".") + 1).concat(".bwp")).forEachOrdered((proc) -> {
        String parentprocess = process.getBasename();
        reportIssueOnFile("For performance reasons it is highly recommended to use Job Shared Variable instead of passing a large set of data when invoking Inline SubProcess " + proc + " from parent process " + parentprocess);
        });
    LOG.debug("Validation ended for rule: " + RULE_KEY);
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
