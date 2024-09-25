/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.metric;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;
import static java.util.Arrays.asList;
import java.util.List;

public class BusinessWorksMetrics implements Metrics {

    public static final String BWLANGUAGEFLAG_KEY = "isbwproject";

    public static final Metric<Boolean> BWLANGUAGEFLAG = new Metric.Builder(BWLANGUAGEFLAG_KEY,
            "TIBCO BusinessWorks Nature", Metric.ValueType.BOOL)
            .setDescription("Equals true if the resource is a TIBCO BusinessWorks project or module")
            .setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL).create();

    public List<Metric> getMetrics() {
        return List.of(BWLANGUAGEFLAG);
    }
}
