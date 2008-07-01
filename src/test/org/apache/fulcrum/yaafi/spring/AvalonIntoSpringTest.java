package org.apache.fulcrum.yaafi.spring;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import junit.framework.TestCase;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.apache.avalon.framework.service.ServiceManager;

/**
 * Test suite for the project
 *
 * @author <a href="mailto:siegfried.goeschl@it20one.at">Siegfried Goeschl</a>
 */

public class AvalonIntoSpringTest extends TestCase
{

    private static final String GREETING = "Hello Avalon!!!";
    private AbstractApplicationContext ctx;

    /** @see junit.framework.TestCase#setUp() */
    protected void setUp() throws Exception {
        super.setUp();
        this.ctx = new FileSystemXmlApplicationContext("./src/test/org/apache/fulcrum/yaafi/spring/avalonIntoSpringApplicationContext.xml");
    }

    /** @see junit.framework.TestCase#tearDown() () */
    protected void tearDown() throws Exception {
        this.ctx.close();        
        super.tearDown();
    }

    /**
     * Constructor
     *
     * @param name the name of the test case
     */
    public AvalonIntoSpringTest( String name )
    {
        super(name);
    }

    /**
     * Test the Avalon into Spring integration.
     *
     * @throws Exception the test failed
     */
    public void testAvalonIntoSpringIntegration() throws Exception
    {
        // ensure that the Avalon SystemPropertyService updated the system properties (so it was properly started)
        assertTrue(System.getProperty("FOO").equals("BAR"));

        CustomAvalonService customAvalonService;
        CustomSpringService customSpringService;
        ServiceManager serviceManager = (ServiceManager) ctx.getBean("avalonContainerBean");

        // lookup and use the Spring bean using Spring's context
        customSpringService = (CustomSpringService) ctx.getBean("customSpringService");
        customSpringService.sayGretting();
        assertEquals(customSpringService.getGreeting(), GREETING);

        // lookup and use the Spring bean using Avalon's Service Manager
        customSpringService = (CustomSpringService) serviceManager.lookup("customSpringService");
        customSpringService.sayGretting();
        assertEquals(customSpringService.getGreeting(), GREETING);

        // lookup and use the Avalon service using Spring's context
        customAvalonService = (CustomAvalonService) ctx.getBean("customAvalonService");
        customAvalonService.sayGretting();
        assertEquals(customAvalonService.getGreeting(), GREETING);

        // lookup and use the Avalon service using Avalon's Service Manager
        customAvalonService = (CustomAvalonService) serviceManager.lookup("customAvalonService");
        customAvalonService.sayGretting();
        assertEquals(customAvalonService.getGreeting(), GREETING);
    }
}