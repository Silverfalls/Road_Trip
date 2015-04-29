package com.cs51.project.roadtrip.enums;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the CompType Enumeration
 */
public class CompTypeTest {

    /**
     * Logger Instance
     */
    private static final Logger logger = Logger.getLogger(CompTypeTest.class);

    @Test
    public void testThereAreNoNullFields() {

        if (logger.isDebugEnabled()) {
            logger.debug("testThereAreNoNullFields | start...");
        }

        for (CompType compType : CompType.values()) {
            if (compType.getName() == null) {
                Assert.fail(compType + " name is null");
            }
            if (compType.getDesc() == null) {
                Assert.fail(compType + " description is null");
            }
            if (compType.getOptionChar() == null) {
                Assert.fail(compType + " option char is null");
            }
            if (compType.getService() == null) {
                Assert.fail(compType + " service implementation is null");
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("testThereAreNoNullFields | end...");
        }
    }

    @Test
    public void testThereAreNoDuplicateNamesOrOptionChars() {

        if (logger.isDebugEnabled()) {
            logger.debug("testThereAreNoDuplicateNamesOrOptionChars | end...");
        }

        for (CompType compType : CompType.values()) {
            for (CompType compType1 : CompType.values()) {
                if (compType == compType1) {
                    continue;
                }
                if (compType.getName().equals(compType1.getName())) {
                    Assert.fail(compType + " has the same name as " + compType1);
                }
                if (compType.getOptionChar().toUpperCase().equals(compType1.getOptionChar().toUpperCase())) {
                    Assert.fail(compType + " has the same option character as " + compType1);
                }
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("testThereAreNoDuplicateNamesOrOptionChars | end...");
        }
    }
}
