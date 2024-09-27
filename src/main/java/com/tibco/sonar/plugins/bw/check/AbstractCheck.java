/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw.check;

import org.sonar.api.rule.RuleKey;
import org.sonar.api.rules.Rule;

import com.tibco.sonar.plugins.bw.source.Source;
import java.util.List;
import org.sonar.api.batch.fs.InputComponent;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.utils.log.Logger;
import com.tibco.sonar.common.SonarMsgConstants;

/**
 * Abtract superclass for checks.
 *
 * @author Kapil Shivarkar
 */
public abstract class AbstractCheck {

    protected Rule rule;
    protected RuleKey ruleKey;
    private SensorContext context;
    private InputComponent inputComponent;

    public abstract String getRuleKeyName();

    public abstract Logger getLogger();

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

    public final <S extends Source> void scanFile(SensorContext context, RuleKey ruleKey, S file) {
        this.context = context;

        this.inputComponent = file.getComponent();

        this.ruleKey = ruleKey;
        validate(file);
    }

    public abstract <S extends Source> void validate(S source);

    public void reportIssueOnFile(String message, List<Integer> secondaryLocationLines) {
        getLogger().warn(SonarMsgConstants.ISSUE_REPORTED_MSG + message + SonarMsgConstants.ON_COMMENT + inputComponent.key() + SonarMsgConstants.CLOSE_MSG);
        NewIssue issue = context.newIssue();

        NewIssueLocation location = issue.newLocation()
                .on(inputComponent)
                .message(message);

        if (inputComponent.isFile()) {
            secondaryLocationLines.stream().map(line -> issue.newLocation()
                    .on(inputComponent)
                    .at(((InputFile) inputComponent).selectLine(line))).forEachOrdered(issue::addLocation);
        }

        issue.at(location)
              .forRule(ruleKey)
              .save();
    }

    public void reportIssueOnFile(String message, InputComponent file, int line) {
        getLogger().warn(SonarMsgConstants.ISSUE_REPORTED_MSG + message + SonarMsgConstants.ON_COMMENT + file.key() + SonarMsgConstants.CLOSE_MSG);
        NewIssue issue = context.newIssue();

        NewIssueLocation location = issue.newLocation()
                .on(file)
                .message(message);

        if (inputComponent.isFile()) {
            NewIssueLocation secondary = issue.newLocation()
                    .on(file)
                    .at(((InputFile) file).selectLine(line));
            issue.addLocation(secondary);
        }

        issue
                .at(location)
                .forRule(ruleKey)
                .save();
    }

    public void reportIssueOnFile(String message, int line) {
        int finalLine = 1;
        if (line > 0) {
            finalLine = line;
        }

        getLogger().warn(SonarMsgConstants.ISSUE_REPORTED_MSG + message + SonarMsgConstants.ON_COMMENT + inputComponent.key() +SonarMsgConstants.CLOSE_MSG);
        NewIssue issue = context.newIssue();

        if (inputComponent.isFile()) {
            NewIssueLocation secondary = issue.newLocation()
                    .on(inputComponent)
                    .at(((InputFile) inputComponent).selectLine(finalLine))
                    .message(message);
            issue.addLocation(secondary);
            issue
                    .at(secondary)
                    .forRule(ruleKey)
                    .save();
        } else {
            NewIssueLocation location = issue.newLocation()
                    .on(inputComponent)
                    .message(message);
            issue
                    .at(location)
                    .forRule(ruleKey)
                    .save();
        }

    }

    public void reportIssueOnFile(String message) {
        getLogger().warn( SonarMsgConstants.ISSUE_REPORTED_MSG + message + SonarMsgConstants.ON_COMMENT + inputComponent.key() + SonarMsgConstants.CLOSE_MSG);
        NewIssue issue = context.newIssue();

        NewIssueLocation location = issue.newLocation()
                .on(inputComponent)
                .message(message);

        if (inputComponent.isFile()) {
            NewIssueLocation secondary = issue.newLocation()
                    .on(inputComponent)
                    .at(((InputFile) inputComponent).selectLine(1));
            issue.addLocation(secondary);
        }

        issue
                .at(location)
                .forRule(ruleKey)
                .save();
    }

}
