package org.apache.fulcrum.yaafi.container;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.apache.fulcrum.yaafi.interceptor.performance.PerformanceInterceptorService;
import org.apache.fulcrum.yaafi.service.advice.AdviceService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

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

public class ContainerTest  {

    DefaultContainerSetup container;
    
    @BeforeEach
    public void setup() {
    }
    
    @Test
    public void testContainer() throws Exception {
        container = new DefaultContainerSetup();
        container.setConfigurationFileName( "/TestComponentConfig.xml" );
        container.setRoleFileName( "/TestRoleConfig.xml" );
        
        Object service = container.lookup( PerformanceInterceptorService.class.getName() );
        System.out.println( "service:" + service );
        assertNotNull( service );
        assertTrue( service instanceof PerformanceInterceptorService );
        PerformanceInterceptorService pService = (PerformanceInterceptorService) service;
        
        Object aService = container.lookup( AdviceService.class.getName() );
        System.out.println( "service:" + aService );
        assertNotNull( aService );
        assertTrue( aService instanceof AdviceService );
        
        AdviceService adviceService = (AdviceService) aService;
        // just any
        Random testObject = new Random();
        Object advicedObject = adviceService.advice( testObject );
        assertTrue(adviceService.isAdviced( advicedObject ));
        
    }
    
    @AfterEach
    public void dispose() {
       container.tearDown(); 
    }
}
