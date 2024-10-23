/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.standalone;


import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.Loggers;

public class DocumentationUtil {

	private static final Logger LOG = Loggers.get(DocumentationUtil.class);

	public static void main(String[] args) throws Exception {
		LOG.debug("***** GENERATING DOCUMENTATION *****");
		com.tibco.utils.bw5.documentation.DocumentationUtil.generate();
		com.tibco.utils.bw6.documentation.DocumentationUtil.generate();
		LOG.debug("***** DOCUMENTATION GENERATED *****");

	}




	

}