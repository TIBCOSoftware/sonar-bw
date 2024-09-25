/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.utils.wrapper.jaxb;

import static org.junit.Assert.*;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.junit.Test;

import com.tibco.xmlns.extarchetype.metadata.ExtArchetypeDescriptor;
import com.tibco.xmlns.extarchetype.metadata.ExtendedProperties;
import com.tibco.xmlns.extarchetype.metadata.ExtendedReferenceProperty;
import com.tibco.xmlns.test.shiporder.Item;
import com.tibco.xmlns.test.shiporder.ShipOrder;
import com.tibco.xmlns.test.shiporder.ShipTo;

public class TestJaxbHelper {

	public final QName shipOrder_QNAME = new QName(
			"http://www.tibco.com/utils/wrapper/jaxb/tests", "shipOrder");

	public final String shipOrder1_XML_STRING = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns2:shipOrder orderId=\"123456\" xmlns:ns2=\"http://www.tibco.com/utils/wrapper/jaxb/tests\"><orderPerson>foo</orderPerson><shipTo><name>bar</name><address>addresse</address><city>city</city><country>country</country></shipTo><item><title>title</title><note>note</note><quantity>1</quantity><price>0</price></item></ns2:shipOrder>";
	public final String shipOrder1_ORDER_ID = "123456";
	public final String shipOrder1_ORDER_PERSON = "foo";
	public final String shipOrder1_SHIP_TO_NAME = "bar";
	public final String shipOrder1_SHIP_TO_ADDRESS = "addresse";
	public final String shipOrder1_SHIP_TO_CITY = "city";
	public final String shipOrder1_SHIP_TO_COUNTRY = "country";
	public final String shipOrder1_ITEM_TITLE = "title";
	public final String shipOrder1_ITEM_NOTE = "note";
	public final String shipOrder1_ITEM_QUANTITY = "1";
	public final Double shipOrder1_ITEM_PRICE = 0.0;

	private final static QName _ExtarchetypeDescriptor_QNAME = new QName(
			"http://www.tibco.com/xmlns/extarchetype/metadata",
			"extarchetype-descriptor");

	@Test
	public void testSimpleLoad() {
		URL url = this.getClass().getResource(
				"/xml/ext-archetype-metadata.sample0.xml");
		ExtArchetypeDescriptor result = null;
		try {
			String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
			File file = new File(filePath);
			result = JaxbHelper.xmlFileToObject(ExtArchetypeDescriptor.class,
					file);
		} catch (Exception e) {
			fail(e.getMessage());
		}
        assertNotNull(result);
        assertNotNull(result.getRequiredProperties());
        assertFalse(result.getRequiredProperties().getRequiredProperty().isEmpty());
	}

	@Test
	public void testOriginalLoad() {
		URL url = this.getClass().getResource(
				"/xml/ext-archetype-metadata.original0.xml");
		ExtArchetypeDescriptor result = null;
		try {
			String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
			File file = new File(filePath);
			result = JaxbHelper.xmlFileToObject(ExtArchetypeDescriptor.class,
					file);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        assertNotNull(result);
        assertNotNull(result.getRequiredProperties());
        assertFalse(result.getRequiredProperties().getRequiredProperty()
                .isEmpty());
	}

	@Test
	public void testSimpleSerialize() {

		ExtArchetypeDescriptor descriptor = new ExtArchetypeDescriptor();
		descriptor.setRequiredProperties(new ExtendedProperties());
		ExtendedReferenceProperty prop1 = new ExtendedReferenceProperty();
		ExtendedReferenceProperty prop2 = new ExtendedReferenceProperty();
		ExtendedReferenceProperty prop3 = new ExtendedReferenceProperty();
		prop1.setKey("prop1");
		prop2.setKey("prop2");
		prop3.setKey("prop3");
		prop1.setValidationRule("validationRule1");
		prop2.setValidationRule("validationRule2");
		prop3.setValidationRule("validationRule3");
		prop1.setSource("deduction");
		prop2.setSource("global");
		prop3.setSource("input");
		descriptor.getRequiredProperties().getRequiredProperty().add(prop1);
		descriptor.getRequiredProperties().getRequiredProperty().add(prop2);
		descriptor.getRequiredProperties().getRequiredProperty().add(prop3);

		try {
			URL url = this.getClass().getResource(
					"/xml/ext-archetype-metadata.sample0.xml");
			String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
			JaxbHelper.objectToXmlFile(descriptor,
					_ExtarchetypeDescriptor_QNAME, filePath, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testObjectToXmlString() {
		ShipOrder shipOrder = new ShipOrder();
		shipOrder.setOrderId(shipOrder1_ORDER_ID);
		shipOrder.setOrderPerson(shipOrder1_ORDER_PERSON);
		shipOrder.setShipTo(new ShipTo());
		shipOrder.getShipTo().setName(shipOrder1_SHIP_TO_NAME);
		shipOrder.getShipTo().setAddress(shipOrder1_SHIP_TO_ADDRESS);
		shipOrder.getShipTo().setCity(shipOrder1_SHIP_TO_CITY);
		shipOrder.getShipTo().setCountry(shipOrder1_SHIP_TO_COUNTRY);
		Item item = new Item();
		item.setTitle(shipOrder1_ITEM_TITLE);
		item.setNote(shipOrder1_ITEM_NOTE);
		item.setQuantity(new BigInteger(shipOrder1_ITEM_QUANTITY));
		item.setPrice(new BigDecimal(shipOrder1_ITEM_PRICE));
		shipOrder.getItem().add(item);

		String xmlStringResult = null;
		try {
			xmlStringResult = JaxbHelper.objectToXmlString(shipOrder,
					shipOrder_QNAME);
		} catch (JAXBException e) {
			e.printStackTrace();
			fail("JAXB exception raised");
		}
		String xmlStringExpected = shipOrder1_XML_STRING;

		assertEquals(xmlStringExpected, xmlStringResult);
		// System.out.println(xmlString);
	}

	@Test
	public void testXmlStringToObject() {
		Object result = null;
		try {
			result = JaxbHelper.xmlStringToObject(ShipOrder.class,
					shipOrder1_XML_STRING);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception raised");
		}

		assertTrue(result instanceof ShipOrder);
		ShipOrder shipOrderResult = (ShipOrder) result;
		assertEquals(shipOrder1_ORDER_ID, shipOrderResult.getOrderId());
		assertEquals(shipOrder1_ORDER_PERSON, shipOrderResult.getOrderPerson());
		assertEquals(shipOrder1_SHIP_TO_NAME, shipOrderResult.getShipTo()
				.getName());
		assertEquals(shipOrder1_SHIP_TO_ADDRESS, shipOrderResult.getShipTo()
				.getAddress());
		assertEquals(shipOrder1_SHIP_TO_CITY, shipOrderResult.getShipTo()
				.getCity());
		assertEquals(shipOrder1_SHIP_TO_COUNTRY, shipOrderResult.getShipTo()
				.getCountry());
		assertEquals(1, shipOrderResult.getItem().size());
		assertEquals(shipOrder1_ITEM_TITLE, shipOrderResult.getItem().get(0)
				.getTitle());
		assertEquals(shipOrder1_ITEM_NOTE, shipOrderResult.getItem().get(0)
				.getNote());
        assertEquals((int) Integer.valueOf(shipOrder1_ITEM_QUANTITY), shipOrderResult.getItem().get(0).getQuantity().intValue());
        assertEquals(shipOrder1_ITEM_PRICE, shipOrderResult.getItem()
                .get(0).getPrice().doubleValue(), 0.0);

	}

}
