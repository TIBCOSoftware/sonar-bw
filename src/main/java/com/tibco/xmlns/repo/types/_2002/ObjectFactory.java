package com.tibco.xmlns.repo.types._2002;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
	private static final QName _Repository_QNAME = new QName("http://www.tibco.com/xmlns/repo/types/2002",
			"repository");

	public GlobalVariableType createGlobalVariableType() {
		return new GlobalVariableType();
	}

	public GlobalVariablesType createGlobalVariablesType() {
		return new GlobalVariablesType();
	}

	public RepositoryType createRepositoryType() {
		return new RepositoryType();
	}

	@XmlElementDecl(namespace = "http://www.tibco.com/xmlns/repo/types/2002", name = "repository")
	public JAXBElement<RepositoryType> createRepository(RepositoryType value) {
		return new JAXBElement(_Repository_QNAME, RepositoryType.class, null, value);
	}
}
