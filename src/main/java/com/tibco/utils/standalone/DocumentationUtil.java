/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.standalone;


public class DocumentationUtil {


	public static void main(String[] args) throws Exception {
		System.out.println("***** GENERATING DOCUMENTATION *****");
		com.tibco.utils.bw5.documentation.DocumentationUtil.generate();
		com.tibco.utils.bw6.documentation.DocumentationUtil.generate();
		System.out.println("***** DOCUMENTATION GENERATED *****");

	}




	

}