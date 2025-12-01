package com.enoda.tests.dmo;

import jakarta.persistence.EntityManager;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Sets up a testing DB and co-ordinates tests
 * @author hugo
 */
@Suite
@SelectClasses({
    ModelTests.class
})
public class TestSuite {
    public static EntityManager em;
}
