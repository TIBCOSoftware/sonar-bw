/*
 * SonarQube XML Plugin
 * Copyright (C) 2010 SonarSource
 * dev@sonar.codehaus.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tibco.sonar.plugins.bw6.check;

import org.sonar.api.rule.RuleKey;
import org.sonar.api.rules.Rule;

import com.tibco.sonar.plugins.bw6.source.Source;
import java.util.List;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;

/**
 * Abtract superclass for checks.
 *
 * @author Kapil Shivarkar
 */
public abstract class AbstractCheck {

    protected Rule rule;
    protected RuleKey ruleKey;
    private SensorContext context;
    private InputFile inputFile;
        
    public abstract String getRuleKeyName();

    public RuleKey getRuleKey() {
        return ruleKey;
    }

    public void setRuleKey(RuleKey ruleKey) {
        this.ruleKey = ruleKey;
    }

    public final void setRule(Rule rule) {
        this.rule = rule;
    }

    public Rule getRule() {
        return rule;
    }

    public final  <S extends Source> void  scanFile(SensorContext context, RuleKey ruleKey, S file) {
        this.context = context;
        this.inputFile = file.getFile().getInputFile();
        this.ruleKey = ruleKey;
        validate(file);
  }
   
    public abstract <S extends Source> void validate(S source);

    public final void reportIssueOnFile(String message, List<Integer> secondaryLocationLines) {
        NewIssue issue = context.newIssue();

        NewIssueLocation location = issue.newLocation()
                .on(inputFile)
                .message(message);

        secondaryLocationLines.stream().map((line) -> issue.newLocation()
                .on(inputFile)
                .at(inputFile.selectLine(line))).forEachOrdered((secondary) -> {
                    issue.addLocation(secondary);
        });

        issue
                .at(location)
                .forRule(ruleKey)
                .save();
    }
    
     public final void reportIssueOnFile(String message, int line) {
        NewIssue issue = context.newIssue();

        NewIssueLocation location = issue.newLocation()
                .on(inputFile)
                .message(message);

        
            NewIssueLocation secondary = issue.newLocation()
                    .on(inputFile)
                    .at(inputFile.selectLine(line));
            issue.addLocation(secondary);
        

        issue
                .at(location)
                .forRule(ruleKey)
                .save();
    }
    
    
     public final void reportIssueOnFile(String message) {
        NewIssue issue = context.newIssue();

        NewIssueLocation location = issue.newLocation()
                .on(inputFile)
                .message(message);

        
        NewIssueLocation secondary = issue.newLocation()
                .on(inputFile)
                .at(inputFile.selectLine(1));
        issue.addLocation(secondary);
        

        issue
                .at(location)
                .forRule(ruleKey)
                .save();
    }


}
