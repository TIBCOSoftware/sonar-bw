/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.sonar.plugins.bw6.check.process;

import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

/**
 *
 * @author avazquez
 */
public class ForEachMappingCheckTest {
    
    private static ProcessSource source;
    
    public ForEachMappingCheckTest() {
    }
     
    @BeforeClass
    public static void setUpClass() {
       
        source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<bpws:process exitOnStandardFault=\"no\"\n" +
"    name=\"com.tibco.bw.service.SISEG201.SISEG201\"\n" +
"    suppressJoinFailure=\"yes\"\n" +
"    targetNamespace=\"http://xmlns.example.com/20171020173048\"\n" +
"    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n" +
"    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n" +
"    xmlns:ns=\"http://xmlns.example.com/20171020173048PLT\"\n" +
"    xmlns:ns0=\"http://www.tibco.com/pe/EngineTypes\"\n" +
"    xmlns:ns1=\"http://xmlns.example.com/20171020173048\"\n" +
"    xmlns:ns2=\"http://xmlns.endesa.com/wsdl/XX_AMBITO_XX/XX_NOMBRE_REQUERIMIENTO_XX/TTXXXZZZ/\"\n" +
"    xmlns:ns3=\"http://xmlns.tibco.com/psg/CommonSchemas\"\n" +
"    xmlns:ns4=\"http://tns.tibco.com/bw/palette/internal/errorlinkhandler+9e08097e-3ba6-4dbb-b55d-e37b861e520c\"\n" +
"    xmlns:ns5=\"http://xmlns.tibco.com/psg/wsdl/InternalService\"\n" +
"    xmlns:sca=\"http://docs.oasis-open.org/ns/opencsa/sca/200912\"\n" +
"    xmlns:sca-bpel=\"http://docs.oasis-open.org/ns/opencsa/sca-bpel/200801\"\n" +
"    xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"\n" +
"    xmlns:tibprop=\"http://ns.tibco.com/bw/property\"\n" +
"    xmlns:tns=\"http://www.example.org/schema/1508569656776\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
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
"                    <!--xs:element minOccurs=\"0\" maxOccurs=\"unbounded\" name=\"TrackingInfo\" form=\"unqualified\" block=\"extension restriction substitution\" type=\"xs:string\"/-->\n" +
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
"        <wsdl:definitions\n" +
"            targetNamespace=\"http://xmlns.example.com/20171020173048PLT\"\n" +
"            xmlns:plnk=\"http://docs.oasis-open.org/wsbpel/2.0/plnktype\"\n" +
"            xmlns:ptyp=\"http://xmlns.example.com/20171020173048\"\n" +
"            xmlns:ptyp1=\"http://xmlns.endesa.com/wsdl/XX_AMBITO_XX/XX_NOMBRE_REQUERIMIENTO_XX/TTXXXZZZ/\"\n" +
"            xmlns:ptyp2=\"http://xmlns.tibco.com/psg/wsdl/InternalService\"\n" +
"            xmlns:tns=\"http://xmlns.example.com/20171020173048PLT\"\n" +
"            xmlns:vprop=\"http://docs.oasis-open.org/wsbpel/2.0/varprop\"\n" +
"            xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
"            <plnk:partnerLinkType name=\"partnerLinkType1\">\n" +
"                <plnk:role name=\"use\" portType=\"ptyp1:MainPortType\"/>\n" +
"            </plnk:partnerLinkType>\n" +
"            <plnk:partnerLinkType name=\"partnerLinkType\">\n" +
"                <plnk:role name=\"use\" portType=\"ptyp2:MainPortType\"/>\n" +
"            </plnk:partnerLinkType>\n" +
"            <wsdl:import namespace=\"http://xmlns.tibco.com/psg/wsdl/InternalService\"/>\n" +
"        </wsdl:definitions>\n" +
"    </tibex:Types>\n" +
"    <tibex:ProcessInfo callable=\"false\" createdBy=\"avazquez\"\n" +
"        createdOn=\"Fri Oct 20 17:30:48 CEST 2017\" description=\"\"\n" +
"        extraErrorVars=\"false\" modifiers=\"public\"\n" +
"        productVersion=\"6.4.1 V22 2017-08-17\" scalable=\"true\"\n" +
"        singleton=\"true\" stateless=\"true\" type=\"IT\"/>\n" +
"    <tibex:ProcessInterface context=\"\" input=\"\" output=\"\"/>\n" +
"    <tibex:ProcessTemplateConfigurations/>\n" +
"    <notation:Diagram measurementUnit=\"Pixel\" type=\"BWProcess\"\n" +
"        xmlns:bwnotation=\"http://tns.tibco.com/bw/runtime/BWNotation\"\n" +
"        xmlns:notation=\"http://www.eclipse.org/gmf/runtime/1.0.2/notation\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"        <children type=\"2001\">\n" +
"            <children type=\"5001\">\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
"            <children type=\"3001\">\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
"            <children type=\"3002\">\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
"            <children type=\"3003\">\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
"            <children type=\"3004\">\n" +
"                <children type=\"4018\">\n" +
"                    <children type=\"3018\">\n" +
"                        <children type=\"4020\">\n" +
"                            <children type=\"3020\">\n" +
"                                <children type=\"4005\">\n" +
"                                    <children type=\"3007\">\n" +
"                                    <children type=\"4002 bw.generalactivities.callprocess\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n" +
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
"                                    x=\"165\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"21\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n" +
"                                    <layoutConstraint x=\"35\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"21\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
"                                    <layoutConstraint x=\"295\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"21\"/>\n" +
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
"                                    <layoutConstraint height=\"233\"\n" +
"                                    width=\"754\" xsi:type=\"notation:Bounds\"/>\n" +
"                                </children>\n" +
"                                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                                <element href=\"//0/@process/@activity\"/>\n" +
"                            </children>\n" +
"                            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                            <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                            <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                            <element href=\"//0/@process/@activity\"/>\n" +
"                            <layoutConstraint height=\"308\" width=\"757\" xsi:type=\"notation:Bounds\"/>\n" +
"                        </children>\n" +
"                        <children type=\"4022\">\n" +
"                            <children type=\"3022\">\n" +
"                                <children type=\"4028\">\n" +
"                                    <children type=\"3029\">\n" +
"                                    <children type=\"4032\">\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@faultHandlers/@catch.0\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"34\" width=\"34\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"68\"/>\n" +
"                                    </children>\n" +
"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"3030\">\n" +
"                                    <children type=\"4005\">\n" +
"                                    <children type=\"3007\">\n" +
"                                    <children type=\"4002 com.tibco.bw.core.design.process.editor.Activity_4002_Reply\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@faultHandlers/@catch.0/@activity/@activity/@activities.0\"/>\n" +
"\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@faultHandlers/@catch.0/@activity/@activity/@activities.0\"/>\n" +
"\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@faultHandlers/@catch.0/@activity/@activity/@activities.0\"/>\n" +
"\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@faultHandlers/@catch.0/@activity/@activity/@activities.0\"/>\n" +
"\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@faultHandlers/@catch.0/@activity/@activity/@activities.0\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"40\"\n" +
"                                    width=\"40\"\n" +
"                                    x=\"130\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"39\"/>\n" +
"                                    </children>\n" +
"                                    <styles xsi:type=\"notation:DrawerStyle\"/>\n" +
"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@faultHandlers/@catch.0/@activity/@activity\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"171\" width=\"336\" xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <styles restoreHeight=\"180\"\n" +
"                                    restoreWidth=\"387\" xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@faultHandlers/@catch.0\"/>\n" +
"                                    <layoutConstraint height=\"180\"\n" +
"                                    width=\"387\" xsi:type=\"notation:Bounds\"/>\n" +
"                                </children>\n" +
"                                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                            </children>\n" +
"                            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                            <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                            <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                            <element href=\"//0/@process/@activity/@faultHandlers\"/>\n" +
"                            <layoutConstraint height=\"201\" width=\"397\"\n" +
"                                x=\"22\" xsi:type=\"notation:Bounds\" y=\"114\"/>\n" +
"                        </children>\n" +
"                        <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                        <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                        <element href=\"//0/@process/@activity\"/>\n" +
"                    </children>\n" +
"                    <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                    <element href=\"//0/@process/@activity\"/>\n" +
"                    <layoutConstraint height=\"316\" width=\"437\" x=\"1\"\n" +
"                        xsi:type=\"notation:Bounds\" y=\"1\"/>\n" +
"                </children>\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
"            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"            <element href=\"//0/@process\"/>\n" +
"            <layoutConstraint height=\"368\" width=\"779\" xsi:type=\"notation:Bounds\"/>\n" +
"        </children>\n" +
"        <styles xsi:type=\"notation:DiagramStyle\"/>\n" +
"        <element href=\"//0\"/>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.0\"/>\n" +
"            <bendpoints points=\"[10, -24, -34, 89]$[34, -88, -10, 25]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.1\"/>\n" +
"            <bendpoints points=\"[19, 25, -70, -88]$[70, 89, -19, -24]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"    </notation:Diagram>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://xmlns.tibco.com/psg/CommonSchemas\"/>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://xmlns.tibco.com/psg/CommonSchemas\"/>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://www.example.org/schema/1508569656776\"/>\n" +
"    <bpws:import importType=\"http://schemas.xmlsoap.org/wsdl/\" namespace=\"http://xmlns.tibco.com/psg/wsdl/InternalService\"/>\n" +
"    <bpws:import importType=\"http://schemas.xmlsoap.org/wsdl/\"\n" +
"        location=\"../../../../../../../com.tibco.bw.arch.fwk/Service%20Descriptors/Internal_Service.wsdl\" namespace=\"http://xmlns.tibco.com/psg/wsdl/InternalService\"/>\n" +
"    <bpws:partnerLinks>\n" +
"        <bpws:partnerLink myRole=\"use\" name=\"SISEG201\"\n" +
"            partnerLinkType=\"ns:partnerLinkType\" sca-bpel:ignore=\"false\"\n" +
"            sca-bpel:service=\"SISEG201\" tibex:register=\"true\"/>\n" +
"    </bpws:partnerLinks>\n" +
"    <bpws:variables>\n" +
"        <bpws:variable element=\"ns0:ProcessContext\"\n" +
"            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns3:SOAInput\" name=\"FWK-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns3:SOAOutput\" name=\"FWK\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable messageType=\"ns5:MainRequest\" name=\"OperationIn\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable messageType=\"ns5:MainResponse\"\n" +
"            name=\"OperationOut-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable messageType=\"ns5:MainResponse\"\n" +
"            name=\"Copy_1_Reply-input\" sca-bpel:internal=\"true\"/>\n" +
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
"        <bpws:faultHandlers>\n" +
"            <bpws:catch faultElement=\"ns3:Error\" faultName=\"ns3:Error\"\n" +
"                faultVariable=\"Error\" tibex:xpdlId=\"713b80bb-be56-40b0-8675-7d5cb31de367\">\n" +
"                <bpws:scope name=\"scope2\">\n" +
"                    <bpws:flow name=\"flow2\">\n" +
"                        <bpws:links/>\n" +
"                        <bpws:reply faultName=\"fault\"\n" +
"                            name=\"Copy_1_Reply\" operation=\"main\"\n" +
"                            partnerLink=\"SISEG201\"\n" +
"                            portType=\"ns5:MainPortType\"\n" +
"                            tibex:xpdlId=\"c875a7c6-ba98-4a19-afe8-e0a5c298d214\" variable=\"Copy_1_Reply-input\">\n" +
"                            <tibex:inputBinding expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\">&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;\n" +
"&lt;xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:tns=\"http://xmlns.tibco.com/psg/CommonSchemas\" xmlns:mensajes=\"http://xmlns.tibco.com/psg/wsdl/InternalService\" version=\"2.0\"&gt;&lt;xsl:param name=\"Error\"/&gt;&lt;xsl:template name=\"Copy_1_Reply-input\" match=\"/\"&gt;&lt;mensajes:MainResponse&gt;&lt;response&gt;&lt;tns:MainResponse&gt;&lt;tns:SOAHeader&gt;&lt;tns:Result&gt;&lt;tns:Status&gt;&lt;xsl:value-of select=\"&amp;quot;ERROR&amp;quot;\"/&gt;&lt;/tns:Status&gt;&lt;tns:ErrorCode&gt;&lt;xsl:value-of select=\"$Error/tns:ErrorCode\"/&gt;&lt;/tns:ErrorCode&gt;&lt;tns:ErrorMessage&gt;&lt;xsl:value-of select=\"$Error/tns:ErrorMessage\"/&gt;&lt;/tns:ErrorMessage&gt;&lt;tns:ErrorDetails&gt;&lt;xsl:copy-of select=\"$Error/ancestor-or-self::*/namespace::node()\"/&gt;&lt;xsl:copy-of select=\"$Error/@*\"/&gt;&lt;xsl:copy-of select=\"$Error/node()\"/&gt;&lt;/tns:ErrorDetails&gt;&lt;/tns:Result&gt;&lt;/tns:SOAHeader&gt;&lt;tns:SOAFunctionalInfo/&gt;&lt;/tns:MainResponse&gt;&lt;/response&gt;&lt;/mensajes:MainResponse&gt;&lt;/xsl:template&gt;&lt;/xsl:stylesheet&gt;</tibex:inputBinding>\n" +
"                            <tibex:inputBindings>\n" +
"                                <tibex:partBinding\n" +
"                                    expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://xmlns.tibco.com/psg/CommonSchemas&quot; xmlns:mensajes=&quot;http://xmlns.tibco.com/psg/wsdl/InternalService&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;Error&quot;/>&#xa;    &lt;xsl:template name=&quot;Copy_1_Reply-input&quot; match=&quot;/&quot;>&#xa;        &lt;tns:MainResponse>&#xa;            &lt;tns:SOAHeader>&#xa;                &lt;tns:Result>&#xa;                    &lt;tns:Status>&#xa;                        &lt;xsl:value-of select=&quot;&amp;quot;ERROR&amp;quot;&quot;/>&#xa;                    &lt;/tns:Status>&#xa;                    &lt;tns:ErrorCode>&#xa;                        &lt;xsl:value-of select=&quot;$Error/tns:ErrorCode&quot;/>&#xa;                    &lt;/tns:ErrorCode>&#xa;                    &lt;tns:ErrorMessage>&#xa;                        &lt;xsl:value-of select=&quot;$Error/tns:ErrorMessage&quot;/>&#xa;                    &lt;/tns:ErrorMessage>&#xa;                    &lt;tns:ErrorDetails>&#xa;                        &lt;xsl:copy-of select=&quot;$Error/ancestor-or-self::*/namespace::node()&quot;/>&#xa;                        &lt;xsl:copy-of select=&quot;$Error/@*&quot;/>&#xa;                        &lt;xsl:copy-of select=&quot;$Error/node()&quot;/>&#xa;                    &lt;/tns:ErrorDetails>&#xa;                &lt;/tns:Result>&#xa;            &lt;/tns:SOAHeader>&#xa;            &lt;tns:SOAFunctionalInfo/>&#xa;        &lt;/tns:MainResponse>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                            </tibex:inputBindings>\n" +
"                            <bpws:targets/>\n" +
"                            <bpws:correlations/>\n" +
"                        </bpws:reply>\n" +
"                    </bpws:flow>\n" +
"                </bpws:scope>\n" +
"            </bpws:catch>\n" +
"        </bpws:faultHandlers>\n" +
"        <bpws:flow name=\"flow\">\n" +
"            <bpws:links>\n" +
"                <bpws:link name=\"__Link\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"__Link1\" tibex:linkType=\"SUCCESSWITHCONDITION\"/>\n" +
"            </bpws:links>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:extActivity\n" +
"                    expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://xmlns.tibco.com/psg/CommonSchemas&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;OperationIn&quot;/>&lt;xsl:template name=&quot;FWK-input&quot; match=&quot;/&quot;>&lt;tns:SOAInput>&lt;tns:SOAHeader>&lt;xsl:if test=&quot;$OperationIn/request/tns:MainRequest/tns:SOAHeader/tns:SOAId&quot;>&lt;tns:SOAId>&lt;xsl:value-of select=&quot;$OperationIn/request/tns:MainRequest/tns:SOAHeader/tns:SOAId&quot;/>&lt;/tns:SOAId>&lt;/xsl:if>&lt;tns:ExternalId>&lt;xsl:value-of select=&quot;$OperationIn/request/tns:MainRequest/tns:SOAHeader/tns:ExternalId&quot;/>&lt;/tns:ExternalId>&lt;tns:SourceApplication>&lt;xsl:value-of select=&quot;$OperationIn/request/tns:MainRequest/tns:SOAHeader/tns:SourceApplication&quot;/>&lt;/tns:SourceApplication>&lt;tns:ServiceName>&lt;xsl:value-of select=&quot;$OperationIn/request/tns:MainRequest/tns:SOAHeader/tns:ServiceName&quot;/>&lt;/tns:ServiceName>&lt;tns:ServiceVersion>&lt;xsl:value-of select=&quot;$OperationIn/request/tns:MainRequest/tns:SOAHeader/tns:ServiceVersion&quot;/>&lt;/tns:ServiceVersion>&lt;tns:OperationName>&lt;xsl:value-of select=&quot;$OperationIn/request/tns:MainRequest/tns:SOAHeader/tns:OperationName&quot;/>&lt;/tns:OperationName>&lt;xsl:if test=&quot;$OperationIn/request/tns:MainRequest/tns:SOAHeader/tns:User&quot;>&lt;tns:User>&lt;xsl:value-of select=&quot;$OperationIn/request/tns:MainRequest/tns:SOAHeader/tns:User&quot;/>&lt;/tns:User>&lt;/xsl:if>&lt;tns:FrameworkVersion>&lt;xsl:value-of select=&quot;$OperationIn/request/tns:MainRequest/tns:SOAHeader/tns:FrameworkVersion&quot;/>&lt;/tns:FrameworkVersion>&lt;tns:FunctionalGroup>&lt;xsl:value-of select=&quot;$OperationIn/request/tns:MainRequest/tns:SOAHeader/tns:FunctionalGroup&quot;/>&lt;/tns:FunctionalGroup>&lt;/tns:SOAHeader>&lt;xsl:copy-of select=&quot;$OperationIn/request/tns:MainRequest/tns:SOAFunctionalInfo&quot;/>&lt;/tns:SOAInput>&lt;/xsl:template>&lt;/xsl:stylesheet>\"\n" +
"                    expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"\n" +
"                    inputVariable=\"FWK-input\" name=\"FWK\"\n" +
"                    outputVariable=\"FWK\"\n" +
"                    tibex:xpdlId=\"9e08097e-3ba6-4dbb-b55d-e37b861e520c\"\n" +
"                    type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"__Link\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"__Link1\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n" +
"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://xmlns.tibco.com/psg/CommonSchemas&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;OperationIn.request&quot;/>&#xa;    &lt;xsl:template name=&quot;FWK-input&quot; match=&quot;/&quot;>&#xa;        &lt;tns:SOAInput>&#xa;            &lt;tns:SOAHeader>&#xa;                &lt;xsl:if test=&quot;$OperationIn.request/tns:SOAHeader/tns:SOAId&quot;>&#xa;                    &lt;tns:SOAId>&#xa;                        &lt;xsl:value-of select=&quot;$OperationIn.request/tns:SOAHeader/tns:SOAId&quot;/>&#xa;                    &lt;/tns:SOAId>&#xa;                &lt;/xsl:if>&#xa;                &lt;tns:ExternalId>&#xa;                    &lt;xsl:value-of select=&quot;$OperationIn.request/tns:SOAHeader/tns:ExternalId&quot;/>&#xa;                &lt;/tns:ExternalId>&#xa;                &lt;tns:SourceApplication>&#xa;                    &lt;xsl:value-of select=&quot;$OperationIn.request/tns:SOAHeader/tns:SourceApplication&quot;/>&#xa;                &lt;/tns:SourceApplication>&#xa;                &lt;tns:ServiceName>&#xa;                    &lt;xsl:value-of select=&quot;$OperationIn.request/tns:SOAHeader/tns:ServiceName&quot;/>&#xa;                &lt;/tns:ServiceName>&#xa;                &lt;tns:ServiceVersion>&#xa;                    &lt;xsl:value-of select=&quot;$OperationIn.request/tns:SOAHeader/tns:ServiceVersion&quot;/>&#xa;                &lt;/tns:ServiceVersion>&#xa;                &lt;tns:OperationName>&#xa;                    &lt;xsl:value-of select=&quot;$OperationIn.request/tns:SOAHeader/tns:OperationName&quot;/>&#xa;                &lt;/tns:OperationName>&#xa;                &lt;xsl:if test=&quot;$OperationIn.request/tns:SOAHeader/tns:User&quot;>&#xa;                    &lt;tns:User>&#xa;                        &lt;xsl:value-of select=&quot;$OperationIn.request/tns:SOAHeader/tns:User&quot;/>&#xa;                    &lt;/tns:User>&#xa;                &lt;/xsl:if>&#xa;                &lt;tns:FrameworkVersion>&#xa;                    &lt;xsl:value-of select=&quot;$OperationIn.request/tns:SOAHeader/tns:FrameworkVersion&quot;/>&#xa;                &lt;/tns:FrameworkVersion>&#xa;                &lt;tns:FunctionalGroup>&#xa;                    &lt;xsl:value-of select=&quot;$OperationIn.request/tns:SOAHeader/tns:FunctionalGroup&quot;/>&#xa;                &lt;/tns:FunctionalGroup>&#xa;            &lt;/tns:SOAHeader>&#xa;            &lt;xsl:copy-of select=&quot;$OperationIn.request/tns:SOAFunctionalInfo&quot;/>&#xa;        &lt;/tns:SOAInput>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:CallProcess moduleRef=\"com.tibco.bw.arch.fwk\"\n" +
"                        subProcessName=\"com.tibco.bw.fwk.main.default\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
"                </tibex:extActivity>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:receive createInstance=\"yes\" name=\"OperationIn\"\n" +
"                operation=\"main\" partnerLink=\"SISEG201\"\n" +
"                portType=\"ns5:MainPortType\"\n" +
"                tibex:xpdlId=\"64532b6e-e422-4015-a08f-b9142a00f803\" variable=\"OperationIn\">\n" +
"                <bpws:sources>\n" +
"                    <bpws:source linkName=\"__Link\"/>\n" +
"                </bpws:sources>\n" +
"                <bpws:correlations/>\n" +
"            </bpws:receive>\n" +
"            <bpws:reply name=\"OperationOut\" operation=\"main\"\n" +
"                partnerLink=\"SISEG201\" portType=\"ns5:MainPortType\"\n" +
"                tibex:xpdlId=\"c1ccf4ab-dd10-440f-aa76-966ce60bc49b\" variable=\"OperationOut-input\">\n" +
"                <tibex:inputBinding expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\">&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;\n" +
"&lt;xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:tns=\"http://xmlns.tibco.com/psg/CommonSchemas\" xmlns:ns0=\"http://xmlns.tibco.com/psg/wsdl/InternalService\" version=\"2.0\"&gt;&lt;xsl:param name=\"FWK\"/&gt;&lt;xsl:template name=\"OperationOut-input\" match=\"/\"&gt;&lt;ns0:MainResponse&gt;&lt;response&gt;&lt;tns:MainResponse&gt;&lt;tns:SOAHeader&gt;&lt;xsl:copy-of select=\"$FWK/tns:SOAHeader/ancestor-or-self::*/namespace::node()\"/&gt;&lt;xsl:copy-of select=\"$FWK/tns:SOAHeader/@*\"/&gt;&lt;xsl:copy-of select=\"$FWK/tns:SOAHeader/node()\"/&gt;&lt;/tns:SOAHeader&gt;&lt;tns:SOAFunctionalInfo&gt;&lt;xsl:copy-of select=\"$FWK/tns:SOAFunctionalInfo/ancestor-or-self::*/namespace::node()\"/&gt;&lt;xsl:copy-of select=\"$FWK/tns:SOAFunctionalInfo/@*\"/&gt;&lt;xsl:copy-of select=\"$FWK/tns:SOAFunctionalInfo/node()\"/&gt;&lt;/tns:SOAFunctionalInfo&gt;&lt;/tns:MainResponse&gt;&lt;/response&gt;&lt;/ns0:MainResponse&gt;&lt;/xsl:template&gt;&lt;/xsl:stylesheet&gt;</tibex:inputBinding>\n" +
"                <tibex:inputBindings>\n" +
"                    <tibex:partBinding\n" +
"                        expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://xmlns.tibco.com/psg/CommonSchemas&quot; xmlns:ns0=&quot;http://xmlns.tibco.com/psg/wsdl/InternalService&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;FWK&quot;/>&#xa;    &lt;xsl:template name=&quot;OperationOut-input&quot; match=&quot;/&quot;>&#xa;        &lt;tns:MainResponse>&#xa;            &lt;tns:SOAHeader>&#xa;                &lt;xsl:copy-of select=&quot;$FWK/tns:SOAHeader/ancestor-or-self::*/namespace::node()&quot;/>&#xa;                &lt;xsl:copy-of select=&quot;$FWK/tns:SOAHeader/@*&quot;/>&#xa;                &lt;xsl:copy-of select=&quot;$FWK/tns:SOAHeader/node()&quot;/>&#xa;            &lt;/tns:SOAHeader>&#xa;            &lt;tns:SOAFunctionalInfo>&#xa;                &lt;xsl:copy-of select=&quot;$FWK/tns:SOAFunctionalInfo/ancestor-or-self::*/namespace::node()&quot;/>&#xa;                &lt;xsl:copy-of select=&quot;$FWK/tns:SOAFunctionalInfo/@*&quot;/>&#xa;                &lt;xsl:copy-of select=&quot;$FWK/tns:SOAFunctionalInfo/node()&quot;/>&#xa;            &lt;/tns:SOAFunctionalInfo>&#xa;        &lt;/tns:MainResponse>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                </tibex:inputBindings>\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"__Link1\"/>\n" +
"                </bpws:targets>\n" +
"                <bpws:correlations/>\n" +
"            </bpws:reply>\n" +
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
        TransitionLabelCheck instance = new TransitionLabelCheck();
        TransitionLabelCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(), anyInt());        
        spyInstance.validate(source);        
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(anyString(),anyInt());
        
    }
    
}
