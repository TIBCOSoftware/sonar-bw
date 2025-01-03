/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;

public class EventSource extends Activity {

    public EventSource(Process process) {
        super(process);
        this.type = "start";
        this.name = "Start";
    }
}
