package com.sailpoint.identityiq.ui.xml.component;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * Test entity class for testing
 */
@Data
public class TestGridDataProviderEntity {

    /**
     * All entities properties names
     */
    public static final List<String> PROPERTY_NAMES = Arrays.asList("property1", "property2", "property3");

    /**
     * Test property1
     */
    String property1;

    /**
     * Test property2
     */
    String property2;

    /**
     * Test property3
     */
    String property3;

}