/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.process;

import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import org.sonar.api.utils.log.Logger;

/**
 *
 * @author avazquez
 */
public class ListFileActivityToCheckFileExistenceCheckTest {
    
    private final ProcessSource source;
    
  
    
    public ListFileActivityToCheckFileExistenceCheckTest() {
   
           
        source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<bpws:process exitOnStandardFault=\"no\"\n" +
"    name=\"com.tibco.bw.service.END_OSOFE004_SOA.Process\"\n" +
"    suppressJoinFailure=\"yes\"\n" +
"    targetNamespace=\"http://xmlns.example.com/20190626080323\"\n" +
"    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n" +
"    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n" +
"    xmlns:ns=\"http://www.tibco.com/pe/EngineTypes\"\n" +
"    xmlns:ns0=\"http://tns.tibco.com/bw/activity/timer/xsd/output\"\n" +
"    xmlns:ns1=\"http://www.tibco.com/namespaces/tnt/plugins/file\"\n" +
"    xmlns:ns2=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.file.list\"\n" +
"    xmlns:sca=\"http://docs.oasis-open.org/ns/opencsa/sca/200912\"\n" +
"    xmlns:sca-bpel=\"http://docs.oasis-open.org/ns/opencsa/sca-bpel/200801\"\n" +
"    xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"\n" +
"    xmlns:tibprop=\"http://ns.tibco.com/bw/property\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
"    <tibex:Types>\n" +
"        <xs:schema attributeFormDefault=\"unqualified\"\n" +
"            elementFormDefault=\"qualified\"\n" +
"            targetNamespace=\"http://www.tibco.com/pe/EngineTypes\"\n" +
"            xmlns:tns=\"http://www.tibco.com/pe/EngineTypes\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
"            <xs:complexType block=\"extension restriction\"\n" +
"                final=\"extension restriction\" name=\"ErrorReport\">\n" +
"                <xs:sequence>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"StackTrace\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"Msg\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"FullClass\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"Class\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"ProcessStack\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"MsgCode\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"Data\" type=\"tns:anydata\"/>\n" +
"                </xs:sequence>\n" +
"            </xs:complexType>\n" +
"            <xs:complexType block=\"extension restriction\"\n" +
"                final=\"extension restriction\" name=\"OptionalErrorReport\">\n" +
"                <xs:sequence>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"StackTrace\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"Msg\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"FullClass\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"Class\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"ProcessStack\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"MsgCode\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"Data\" type=\"tns:anydata\"/>\n" +
"                </xs:sequence>\n" +
"            </xs:complexType>\n" +
"            <xs:complexType block=\"extension restriction\"\n" +
"                final=\"extension restriction\" name=\"FaultDetail\">\n" +
"                <xs:sequence>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"ActivityName\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"Data\" type=\"tns:anydata\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"Msg\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"MsgCode\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"ProcessStack\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"StackTrace\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"FullClass\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"Class\" type=\"xs:string\"/>\n" +
"                </xs:sequence>\n" +
"            </xs:complexType>\n" +
"            <xs:complexType block=\"extension restriction\"\n" +
"                final=\"extension restriction\" name=\"ProcessContext\">\n" +
"                <xs:sequence>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"JobId\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"ApplicationName\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"EngineName\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"ProcessInstanceId\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"CustomJobId\" type=\"xs:string\"/>\n" +
"                    <!--xs:element name=\"RestartedFromCheckpoint\" form=\"unqualified\" block=\"extension restriction substitution\" type=\"xs:boolean\"/-->\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" maxOccurs=\"unbounded\"\n" +
"                        minOccurs=\"0\" name=\"TrackingInfo\" type=\"xs:string\"/>\n" +
"                </xs:sequence>\n" +
"            </xs:complexType>\n" +
"            <xs:complexType block=\"extension restriction\"\n" +
"                final=\"extension restriction\" name=\"anydata\">\n" +
"                <xs:sequence>\n" +
"                    <xs:any namespace=\"##any\" processContents=\"lax\"/>\n" +
"                </xs:sequence>\n" +
"            </xs:complexType>\n" +
"            <xs:element block=\"extension restriction substitution\"\n" +
"                final=\"extension restriction\" name=\"OptionalErrorReport\" type=\"tns:OptionalErrorReport\"/>\n" +
"            <xs:element block=\"extension restriction substitution\"\n" +
"                final=\"extension restriction\" name=\"ErrorReport\" type=\"tns:ErrorReport\"/>\n" +
"            <xs:element block=\"extension restriction substitution\"\n" +
"                final=\"extension restriction\" name=\"FaultDetail\" type=\"tns:FaultDetail\"/>\n" +
"            <xs:element block=\"extension restriction substitution\"\n" +
"                final=\"extension restriction\" name=\"ProcessContext\" type=\"tns:ProcessContext\"/>\n" +
"            <xs:element block=\"extension restriction substitution\"\n" +
"                final=\"extension restriction\" name=\"CorrelationValue\" type=\"xs:string\"/>\n" +
"        </xs:schema>\n" +
"        <schema attributeFormDefault=\"unqualified\"\n" +
"            elementFormDefault=\"unqualified\"\n" +
"            targetNamespace=\"http://schemas.tibco.com/bw/pe/plugin/5.0/exceptions\"\n" +
"            version=\"\" xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://schemas.tibco.com/bw/pe/plugin/5.0/exceptions\">\n" +
"            <complexType name=\"ActivityExceptionType\">\n" +
"                <sequence>\n" +
"                    <element name=\"msg\" type=\"string\"/>\n" +
"                    <element minOccurs=\"0\" name=\"msgCode\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"ActivityException\" type=\"tns:ActivityExceptionType\"/>\n" +
"            <complexType name=\"ActivityTimedOutExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns:ActivityExceptionType\"/>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"ActivityTimedOutException\" type=\"tns:ActivityTimedOutExceptionType\"/>\n" +
"            <complexType name=\"DuplicateKeyExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns:ActivityExceptionType\">\n" +
"                        <sequence>\n" +
"                            <element name=\"duplicateKey\" type=\"string\"/>\n" +
"                            <element minOccurs=\"0\" name=\"previousJobID\" type=\"string\"/>\n" +
"                        </sequence>\n" +
"                    </extension>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"DuplicateKeyException\" type=\"tns:DuplicateKeyExceptionType\"/>\n" +
"        </schema>\n" +
"        <xs:schema attributeFormDefault=\"unqualified\"\n" +
"            elementFormDefault=\"qualified\"\n" +
"            targetNamespace=\"http://tns.tibco.com/bw/activity/timer/xsd/output\"\n" +
"            xmlns:tns=\"http://tns.tibco.com/bw/activity/timer/xsd/output\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
"            <xs:complexType name=\"TimerOutputSchemaType\">\n" +
"                <xs:sequence>\n" +
"                    <xs:element form=\"unqualified\" name=\"Now\" type=\"xs:long\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Hour\" type=\"xs:int\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Minute\" type=\"xs:int\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Second\" type=\"xs:int\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Week\" type=\"xs:int\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Month\" type=\"xs:int\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Year\" type=\"xs:int\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Date\" type=\"xs:date\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Time\" type=\"xs:string\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"DayOfMonth\" type=\"xs:int\"/>\n" +
"                </xs:sequence>\n" +
"            </xs:complexType>\n" +
"            <xs:element name=\"TimerOutputSchema\" type=\"tns:TimerOutputSchemaType\"/>\n" +
"        </xs:schema>\n" +
"        <schema\n" +
"            targetNamespace=\"http://www.tibco.com/namespaces/tnt/plugins/file\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://www.tibco.com/namespaces/tnt/plugins/file\">\n" +
"            <complexType name=\"EventSourceConfigClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileName\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"pollInterval\" type=\"int\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"includeSubDirectories\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"includeCurrent\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"excludeContent\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"mode\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"encoding\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"encodingUsed\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"sortorder\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"sortby\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"EventSourceConfigClass\" type=\"tns:EventSourceConfigClass\"/>\n" +
"            <complexType name=\"ReadActivityConfigClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"excludeContent\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"encoding\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"ReadActivityConfigClass\" type=\"tns:ReadActivityConfigClass\"/>\n" +
"            <complexType name=\"ReadActivityInputClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"fileName\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"ReadActivityInputClass\" type=\"tns:ReadActivityInputClass\"/>\n" +
"            <complexType name=\"fileInfoType\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fullName\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileName\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"location\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"configuredFileName\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"type\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"readProtected\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"writeProtected\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"size\" type=\"long\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"lastModified\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"fileContentTypeBinary\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"binaryContent\" type=\"base64Binary\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"fileContentTypeTextClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"textContent\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"encoding\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"ReadActivityOutputNoContentClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"ReadActivityOutputNoContentClass\" type=\"tns:ReadActivityOutputNoContentClass\"/>\n" +
"            <complexType name=\"ReadActivityOutputBinaryClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"fileContent\" type=\"tns:fileContentTypeBinary\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"ReadActivityOutputBinaryClass\" type=\"tns:ReadActivityOutputBinaryClass\"/>\n" +
"            <complexType name=\"ReadActivityOutputTextClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"fileContent\" type=\"tns:fileContentTypeTextClass\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"ReadActivityOutputTextClass\" type=\"tns:ReadActivityOutputTextClass\"/>\n" +
"            <complexType name=\"FileIOException\"/>\n" +
"            <element name=\"FileIOException\" type=\"tns:FileIOException\"/>\n" +
"            <complexType name=\"EventSourceOuputNoContentClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"action\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"timeOccurred\" type=\"long\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"EventSourceOuputNoContentClass\" type=\"tns:EventSourceOuputNoContentClass\"/>\n" +
"            <complexType name=\"EventSourceOuputBinaryClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"action\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"timeOccurred\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"fileContent\" type=\"tns:fileContentTypeBinary\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"EventSourceOuputBinaryClass\" type=\"tns:EventSourceOuputBinaryClass\"/>\n" +
"            <complexType name=\"EventSourceOuputTextClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"action\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"timeOccurred\" type=\"long\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"fileContent\" type=\"tns:fileContentTypeTextClass\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"EventSourceOuputTextClass\" type=\"tns:EventSourceOuputTextClass\"/>\n" +
"            <complexType name=\"WriteActivityConfigClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"createNewFile\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"append\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"encoding\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"compressFile\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"WriteActivityConfigClass\" type=\"tns:WriteActivityConfigClass\"/>\n" +
"            <complexType name=\"WriteActivityInputBinaryClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"fileName\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"binaryContent\" type=\"base64Binary\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"WriteActivityInputBinaryClass\" type=\"tns:WriteActivityInputBinaryClass\"/>\n" +
"            <complexType name=\"WriteActivityInputTextClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"fileName\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"textContent\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"addLineSeparator\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"encoding\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"WriteActivityInputTextClass\" type=\"tns:WriteActivityInputTextClass\"/>\n" +
"            <complexType name=\"WriteActivityOutputClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"WriteActivityOutputClass\" type=\"tns:WriteActivityOutputClass\"/>\n" +
"            <complexType name=\"CreateActivityConfigClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"override\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"createDirectory\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"createMissingDirectories\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"overwrite\" type=\"boolean\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"CreateActivityConfigClass\" type=\"tns:CreateActivityConfigClass\"/>\n" +
"            <complexType name=\"CreateActivityInputClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"fileName\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"CreateActivityInputClass\" type=\"tns:CreateActivityInputClass\"/>\n" +
"            <complexType name=\"CreateActivityOutputClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"CreateActivityOutputClass\" type=\"tns:CreateActivityOutputClass\"/>\n" +
"            <complexType name=\"RemoveActivityConfigClass\"/>\n" +
"            <element name=\"RemoveActivityConfigClass\" type=\"tns:RemoveActivityConfigClass\"/>\n" +
"            <complexType name=\"RemoveActivityInputClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"fileName\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"RemoveActivityInputClass\" type=\"tns:RemoveActivityInputClass\"/>\n" +
"            <complexType name=\"RemoveActivityOutputClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"RemoveActivityOutputClass\" type=\"tns:RemoveActivityOutputClass\"/>\n" +
"            <element name=\"RenameActivityConfig\" type=\"tns:RenameActivityInputClass\"/>\n" +
"            <complexType name=\"RenameActivityConfig\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"overwrite\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"createMissingDirectories\" type=\"boolean\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"RenameActivityInputClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"fromFileName\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"toFileName\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"RenameActivityInputClass\" type=\"tns:RenameActivityInputClass\"/>\n" +
"            <complexType name=\"RenameActivityOutput\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"ListFilesActivityConfig\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"mode\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"ListFilesActivityConfig\" type=\"tns:ListFilesActivityInputClass\"/>\n" +
"            <complexType name=\"ListFilesActivityInputClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"fileName\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"ListFilesActivityInputClass\" type=\"tns:ListFilesActivityInputClass\"/>\n" +
"            <complexType name=\"files\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"unbounded\" minOccurs=\"0\"\n" +
"                        name=\"fileInfo\" type=\"tns:fileInfoType\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"ListFilesActivityOutput\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"files\" type=\"tns:files\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"ListFilesActivityOutput\" type=\"tns:ListFilesActivityOutput\"/>\n" +
"            <complexType name=\"CopyActivityConfig\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"fromFileName\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"toFileName\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"overwrite\" type=\"boolean\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"createMissingDirectories\" type=\"boolean\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"CopyActivityConfig\" type=\"tns:CopyActivityInputClass\"/>\n" +
"            <complexType name=\"CopyActivityInputClass\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"fromFileName\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"toFileName\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"CopyActivityInputClass\" type=\"tns:CopyActivityInputClass\"/>\n" +
"            <element name=\"RenameActivityOutput\" type=\"tns:RenameActivityOutput\"/>\n" +
"            <element name=\"input\" type=\"tns:WaitForFileChangeActivityInput\"/>\n" +
"            <complexType name=\"WaitForFileChangeActivityInput\">\n" +
"                <sequence>\n" +
"                    <element minOccurs=\"0\" name=\"key\" type=\"string\"/>\n" +
"                    <element minOccurs=\"0\" name=\"processTimeout\" type=\"int\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"        </schema>\n" +
"        <schema elementFormDefault=\"unqualified\"\n" +
"            targetNamespace=\"http://schemas.tibco.com/bw/plugins/file/5.0/fileExceptions\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://schemas.tibco.com/bw/plugins/file/5.0/fileExceptions\">\n" +
"            <element name=\"FileException\" type=\"tns:FileExceptionType\"/>\n" +
"            <complexType name=\"FileExceptionType\">\n" +
"                <sequence>\n" +
"                    <element name=\"msg\" type=\"string\"/>\n" +
"                    <element minOccurs=\"0\" name=\"msgCode\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"ActivityTimedOutException\" type=\"tns:complexTypeFault\"/>\n" +
"            <complexType name=\"complexTypeFault\">\n" +
"                <sequence>\n" +
"                    <element name=\"msg\" type=\"string\"/>\n" +
"                    <element minOccurs=\"0\" name=\"msgCode\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"FileNotFoundExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns:FileExceptionType\">\n" +
"                        <sequence>\n" +
"                            <element name=\"fileName\" type=\"string\"/>\n" +
"                        </sequence>\n" +
"                    </extension>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"FileNotFoundException\" type=\"tns:FileNotFoundExceptionType\"/>\n" +
"            <complexType name=\"UnsupportedEncodingExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns:FileExceptionType\">\n" +
"                        <sequence>\n" +
"                            <element name=\"encoding\" type=\"string\"/>\n" +
"                        </sequence>\n" +
"                    </extension>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"UnsupportedEncodingException\" type=\"tns:UnsupportedEncodingExceptionType\"/>\n" +
"            <complexType name=\"FileIOExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns:FileExceptionType\"/>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"FileIOException\" type=\"tns:FileIOExceptionType\"/>\n" +
"            <complexType name=\"IllegalRenameExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns:FileExceptionType\">\n" +
"                        <sequence>\n" +
"                            <element name=\"fromFileName\" type=\"string\"/>\n" +
"                            <element name=\"toFileName\" type=\"string\"/>\n" +
"                        </sequence>\n" +
"                    </extension>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"IllegalRenameException\" type=\"tns:IllegalRenameExceptionType\"/>\n" +
"            <complexType name=\"FileAlreadyExistsExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns:FileExceptionType\">\n" +
"                        <sequence>\n" +
"                            <element name=\"fileName\" type=\"string\"/>\n" +
"                        </sequence>\n" +
"                    </extension>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"FileAlreadyExistsException\" type=\"tns:FileAlreadyExistsExceptionType\"/>\n" +
"            <complexType name=\"IllegalCopyExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns:FileExceptionType\">\n" +
"                        <sequence>\n" +
"                            <element name=\"fromFileName\" type=\"string\"/>\n" +
"                            <element name=\"toFileName\" type=\"string\"/>\n" +
"                        </sequence>\n" +
"                    </extension>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"IllegalCopyException\" type=\"tns:IllegalCopyExceptionType\"/>\n" +
"            <!-- fault signature for ReadFile activity -->\n" +
"            <complexType name=\"ReadFileFaultDataType\">\n" +
"                <choice>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" ref=\"tns:FileNotFoundException\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" ref=\"tns:UnsupportedEncodingException\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" ref=\"tns:FileIOException\"/>\n" +
"                </choice>\n" +
"            </complexType>\n" +
"            <element name=\"ReadFileFaultData\" type=\"tns:ReadFileFaultDataType\"/>\n" +
"        </schema>\n" +
"        <schema elementFormDefault=\"unqualified\"\n" +
"            targetNamespace=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.file.list\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\"\n" +
"            xmlns:Q1=\"http://schemas.tibco.com/bw/plugins/file/5.0/fileExceptions\" xmlns:tns=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.file.list\">\n" +
"            <import namespace=\"http://schemas.tibco.com/bw/plugins/file/5.0/fileExceptions\"/>\n" +
"            <element name=\"ActivityErrorData\" type=\"tns:ActivityErrorDataType\"/>\n" +
"            <complexType name=\"ActivityErrorDataType\">\n" +
"                <choice>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:FileNotFoundException\"/>\n" +
"                </choice>\n" +
"            </complexType>\n" +
"        </schema>\n" +
"    </tibex:Types>\n" +
"    <tibex:ProcessInfo callable=\"false\" createdBy=\"avazquez\"\n" +
"        createdOn=\"Wed Jun 26 08:03:23 CEST 2019\" description=\"\"\n" +
"        extraErrorVars=\"true\" modifiers=\"public\"\n" +
"        productVersion=\"6.5.1 V9 2019-02-06\" scalable=\"true\"\n" +
"        singleton=\"true\" stateless=\"false\" type=\"IT\"/>\n" +
"    <tibex:ProcessInterface context=\"\" input=\"\" output=\"\"/>\n" +
"    <tibex:ProcessTemplateConfigurations/>\n" +
"    <notation:Diagram measurementUnit=\"Pixel\" type=\"BWProcess\"\n" +
"        xmlns:bwnotation=\"http://tns.tibco.com/bw/runtime/BWNotation\"\n" +
"        xmlns:notation=\"http://www.eclipse.org/gmf/runtime/1.0.2/notation\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"        <children type=\"2001\">\n" +
"            <children type=\"5001\"/>\n" +
"            <children type=\"3001\">\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
"            <children type=\"3002\">\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"            </children>\n" +
"            <children type=\"3003\">\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"            </children>\n" +
"            <children type=\"3004\">\n" +
"                <children type=\"4018\">\n" +
"                    <children type=\"3018\">\n" +
"                        <children type=\"4020\">\n" +
"                            <children type=\"3020\">\n" +
"                                <children type=\"4005\">\n" +
"                                    <children type=\"3007\">\n" +
"                                    <children type=\"4002 bw.generalactivities.timer\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"40\" width=\"40\"\n" +
"                                    x=\"-2\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"96\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n" +
"                                    <layoutConstraint x=\"153\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"96\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
"                                    <layoutConstraint x=\"350\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"96\"/>\n" +
"                                    </children>\n" +
"                                    <styles xsi:type=\"notation:DrawerStyle\"/>\n" +
"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity\"/>\n" +
"                                    <layoutConstraint height=\"384\"\n" +
"                                    width=\"515\" xsi:type=\"notation:Bounds\"/>\n" +
"                                </children>\n" +
"                                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                            </children>\n" +
"                            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                            <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                            <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                            <element href=\"//0/@process/@activity\"/>\n" +
"                            <layoutConstraint height=\"365\" width=\"444\"\n" +
"                                x=\"30\" xsi:type=\"notation:Bounds\" y=\"20\"/>\n" +
"                        </children>\n" +
"                        <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                        <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                        <element href=\"//0/@process/@activity\"/>\n" +
"                    </children>\n" +
"                    <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                    <element href=\"//0/@process/@activity\"/>\n" +
"                    <layoutConstraint height=\"409\" width=\"519\" xsi:type=\"notation:Bounds\"/>\n" +
"                </children>\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
"            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"            <element href=\"//0/@process\"/>\n" +
"            <layoutConstraint height=\"460\" width=\"860\" xsi:type=\"notation:Bounds\"/>\n" +
"        </children>\n" +
"        <styles xsi:type=\"notation:DiagramStyle\"/>\n" +
"        <element href=\"//0\"/>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.0\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.1\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"    </notation:Diagram>\n" +
"    <tibex:NamespaceRegistry enabled=\"true\">\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://www.tibco.com/namespaces/tnt/plugins/file\" prefix=\"tns\"/>\n" +
"    </tibex:NamespaceRegistry>\n" +
"    <bpws:variables>\n" +
"        <bpws:variable element=\"ns:ProcessContext\"\n" +
"            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns0:TimerOutputSchema\" name=\"Timer\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns1:ListFilesActivityConfig\"\n" +
"            name=\"ListFiles-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns1:ListFilesActivityOutput\"\n" +
"            name=\"ListFiles\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns2:ActivityErrorData\"\n" +
"            name=\"_error_ListFiles\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns:ErrorReport\" name=\"_error\" sca-bpel:internal=\"true\"/>\n" +
"    </bpws:variables>\n" +
"    <bpws:extensions>\n" +
"        <bpws:extension mustUnderstand=\"no\" namespace=\"http://www.eclipse.org/gmf/runtime/1.0.2/notation\"/>\n" +
"        <bpws:extension mustUnderstand=\"no\" namespace=\"http://www.tibco.com/bw/process/info\"/>\n" +
"        <bpws:extension mustUnderstand=\"no\" namespace=\"http://docs.oasis-open.org/ns/opencsa/sca-bpel/200801\"/>\n" +
"        <bpws:extension mustUnderstand=\"no\" namespace=\"http://docs.oasis-open.org/ns/opencsa/sca/200912\"/>\n" +
"        <bpws:extension mustUnderstand=\"no\" namespace=\"http://ns.tibco.com/bw/property\"/>\n" +
"        <bpws:extension mustUnderstand=\"no\" namespace=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
"    </bpws:extensions>\n" +
"    <bpws:scope name=\"scope\">\n" +
"        <bpws:flow name=\"flow\">\n" +
"            <bpws:links>\n" +
"                <bpws:link name=\"TimerToListFiles\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"ListFilesToExit\" tibex:linkType=\"SUCCESS\"/>\n" +
"            </bpws:links>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:receiveEvent createInstance=\"yes\"\n" +
"                    eventTimeout=\"60\" name=\"Timer\"\n" +
"                    tibex:xpdlId=\"23f53721-c64a-4259-97ce-daa12a709eee\"\n" +
"                    variable=\"Timer\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"TimerToListFiles\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:eventSource>\n" +
"                        <bwext:BWActivity\n" +
"                            activityTypeID=\"bw.generalactivities.timer\"\n" +
"                            version=\"6.0.0.001\"\n" +
"                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
"                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
"                            xmlns:generalactivities=\"http://ns.tibco.com/bw/palette/generalactivities\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                            <activityConfig>\n" +
"                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
"                                    <type href=\"http://ns.tibco.com/bw/palette/generalactivities#//Timer\"/>\n" +
"                                    <value\n" +
"                                    endTime=\"2019-06-26T08:03:29.341+0200\"\n" +
"                                    intervalUnit=\"Second\"\n" +
"                                    runOnce=\"true\"\n" +
"                                    startTime=\"2019-06-26T08:03:29.341+0200\"\n" +
"                                    timeInterval=\"1\" xsi:type=\"generalactivities:Timer\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:eventSource>\n" +
"                </tibex:receiveEvent>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:activityExtension inputVariable=\"ListFiles-input\"\n" +
"                    name=\"ListFiles\" outputVariable=\"ListFiles\"\n" +
"                    tibex:xpdlId=\"5ab571b1-1977-4c49-a8c9-bbc2c91eda42\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"TimerToListFiles\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"ListFilesToExit\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n" +
"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.tibco.com/namespaces/tnt/plugins/file&quot; version=&quot;2.0&quot;>&lt;xsl:template name=&quot;ListFiles-input&quot; match=&quot;/&quot;>&lt;tns:ListFilesActivityConfig>&lt;fileName>&lt;xsl:value-of select=&quot;&amp;quot;test.txt&amp;quot;&quot;/>&lt;/fileName>&lt;/tns:ListFilesActivityConfig>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:config>\n" +
"                        <bwext:BWActivity activityTypeID=\"bw.file.list\"\n" +
"                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
"                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
"                            xmlns:file=\"http://ns.tibco.com/bw/palette/file\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                            <activityConfig>\n" +
"                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
"                                    <type href=\"http://ns.tibco.com/bw/palette/file#//ListFiles\"/>\n" +
"                                    <value fileName=\"test.txt\"\n" +
"                                    mode=\"files-and-directories\" xsi:type=\"file:ListFiles\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:config>\n" +
"                </tibex:activityExtension>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:exit name=\"Exit\" tibex:xpdlId=\"aa638769-4e8a-4ffb-8c50-29b669cb1d45\">\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"ListFilesToExit\"/>\n" +
"                </bpws:targets>\n" +
"            </bpws:exit>\n" +
"        </bpws:flow>\n" +
"    </bpws:scope>\n" +
"</bpws:process>");

        
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }



    @Test
    public void testValidate() {
        System.out.println("testValidate");
        ListFileActivityToCheckFileExistenceCheck instance = new ListFileActivityToCheckFileExistenceCheck();
        ListFileActivityToCheckFileExistenceCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());        
        spyInstance.validate(source);        
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(anyString(),anyInt());
        
    }
    
}
