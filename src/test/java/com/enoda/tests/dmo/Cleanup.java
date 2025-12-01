package com.enoda.tests.dmo;

import org.junit.jupiter.api.Test;

/**
 * Close DB and cleanup
 * @author hugo
 */
public class Cleanup {
    @Test
    public void Test000() {
        Setup.shutdown();
    }
}
