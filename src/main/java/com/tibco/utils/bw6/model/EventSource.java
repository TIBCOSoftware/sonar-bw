/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.utils.bw6.model;

public class EventSource extends Activity {

    public EventSource(Process process) {
        super(process);
        this.type = "start";
        this.name = "Start";
    }
}
