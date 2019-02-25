package com.tibco.sonar.plugins.bw6.source;

import java.io.File;
import java.nio.charset.Charset;
import com.tibco.sonar.plugins.bw6.file.XmlFile;
import com.tibco.sonar.plugins.bw6.util.SaxParser;
import com.tibco.utils.bw.model.Process;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Checks and analyzes report measurements, issues and other findings in
 * WebSourceCode.
 * 
 * @author Kapil Shivarkar
 */
public class ProcessSource extends XmlSource {

	private Process process;

	public ProcessSource(File file){
		super(file);
            try {
                this.process = new Process();	
                process.setProcessXmlDocument(new SaxParser().parseDocument(new FileInputStream(file), true));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProcessSource.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

	public ProcessSource(InputStream file){
		super(file);
		this.process = new Process();
		process.setProcessXmlDocument(new SaxParser().parseDocument(file, true));	
	}


	public ProcessSource(XmlFile xmlFile) {
		super(xmlFile);
		this.process = new Process();
	}

/** 	public ProcessSource(String code) {
		super(code);
		setCode(code);
		this.process = new Process();
		InputStream is = createInputStream();
		process.setProcessXmlDocument(new SaxParser().parseDocument(is, true));		
	}
*/
	@Override
	public boolean parseSource(Charset charset) {
		boolean result = super.parseSource(charset);
		// TODO This can probalby be removed.
		//if(result){
		//	Process proc = process.setProcessXmlDocument(getDocument(true));
		//	proc.parse();
		//	proc.myparse();
		//}
		return result;
	}

	public void setProcessModel(Process process){
		this.process = process;
	}

	public Process getProcessModel(){
		return process;
	}
}
