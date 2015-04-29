package com.cs51.project.roadtrip.enums;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for AlgType enumeration
 */
public class AlgTypeTest {

    /**
     * Logger Instance
     */
    private static final Logger logger = Logger.getLogger(AlgTypeTest.class);

    @Test
    public void testThereAreNoNullFields() {

        if (logger.isDebugEnabled()) {
            logger.debug("testThereAreNoNullFields | start...");
        }

        for (AlgType algType : AlgType.values()) {
            if (algType.getName() == null) {
                Assert.fail(algType + " name is null");
            }
            if (algType.getDesc() == null) {
                Assert.fail(algType + " description is null");
            }
            if (algType.getOptionChar() == null) {
                Assert.fail(algType + " option char is null");
            }
            if (algType.getAlg() == null) {
                Assert.fail(algType + " algorithm implementation is null");
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

        for (AlgType algType : AlgType.values()) {
            for (AlgType algType1 : AlgType.values()) {
                if (algType == algType1) {
                    continue;
                }
                if (algType.getName().equals(algType1.getName())) {
                    Assert.fail(algType + " has the same name as " + algType1);
                }
                if (algType.getOptionChar().toUpperCase().equals(algType1.getOptionChar().toUpperCase())) {
                    Assert.fail(algType + " has the same option character as " + algType1);
                }
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("testThereAreNoDuplicateNamesOrOptionChars | end...");
        }
    }
}
