package com.mobiquityinc.packer;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.mobiquityinc.exception.APIException;

public class PackerTest {

	@Test
	public void testPackerOk() {
		try {
			System.out.println(Packer.pack("test/resources/TC_01.txt"));
		} catch (APIException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testPackerItemWieghtMoreThanMaxSupported() {
		try {
			System.out.println(Packer.pack("test/resources/TC_02.txt"));
		} catch (APIException e) {
			assertTrue("Item weight cannot exceed: 100.0".equals(e.getMessage()));
		}
	}
	
	@Test
	public void testPackerMoreThanMaxItemsPerLine() {
		try {
			System.out.println(Packer.pack("test/resources/TC_03.txt"));
		} catch (APIException e) {
			assertTrue("Cannot add more than: 15 items".equals(e.getMessage()));
		}
	}
	
	@Test
	public void testPackerItemCostMoreThanMaxSupported() {
		try {
			System.out.println(Packer.pack("test/resources/TC_04.txt"));
		} catch (APIException e) {
			assertTrue("Item cost cannot exceed: 100".equals(e.getMessage()));
		}
	}
	
	@Test
	public void testPackerInvalidData() {
		try {
			System.out.println(Packer.pack("test/resources/TC_05.txt"));
		} catch (APIException e) {
			assertTrue("Invalid data at line number: 1".equals(e.getMessage()));
		}
	}
	
	@Test
	public void testPackerSameCostLowerWeight() {
		try {
			System.out.println(Packer.pack("test/resources/TC_06.txt"));
		} catch (APIException e) {
			System.out.print(e.getMessage());
		}
	}
}
