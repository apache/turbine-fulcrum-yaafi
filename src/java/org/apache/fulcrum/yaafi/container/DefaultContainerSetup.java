package org.apache.fulcrum.yaafi.container;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.avalon.framework.component.ComponentException;
import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.avalon.framework.logger.Logger;

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

public class DefaultContainerSetup
{
    public static final String CONTAINER_YAAFI = "CONTAINER_YAAFI";

    /** Key used in the context for defining the application root */
    public static final String COMPONENT_APP_ROOT = Container.COMPONENT_APP_ROOT;

    /** Pick the default container to be YAAFI, running in **/
    private String containerType = CONTAINER_YAAFI;

    /** Use INFO for ConsoleLogger */
    public static final int defaultLogLevel = 1; //org.apache.avalon.framework.logger.ConsoleLogger.LEVEL_INFO;

    /** Container for the components */
    private Container container;

    /** default configurationFileName */
    private String configurationFileName = "/DefaultComponentConfig.xml"; 

    /** setup default roleFileName */
    private String roleFileName = "/DefaultRoleConfig.xml";

    /** Setup our default parameterFileName */
    private String parameterFileName = null;

    /** Set the log level (only works for YAAFI container) */
    private int logLevel = defaultLogLevel;

    /** Hash map to store attributes for the test **/
    public Map<String, Object> attributes = new HashMap<String, Object>();

    /** set the Max inactive interval **/
    public int maxInactiveInterval = 0;
    
    /**
     * Constructor 
     *  
     * @throws IOException 
     */
    public DefaultContainerSetup()  
    { 
    }


    /**
     * Gets the configuration file name for the container should use for this test.
     * By default it is src/test/TestComponentConfig.
     * 
     * @param configurationFileName the location of the config file
     */
    public void setConfigurationFileName(String configurationFileName) 
    {
        this.configurationFileName = configurationFileName;
    }

    /**
     * Override the role file name for the container should use for this test. By
     * default it is src/test/TestRoleConfig.
     * 
     * @param roleFileName location of the role file
     */
    public void setRoleFileName(String roleFileName) 
    {
        this.roleFileName = roleFileName;
    }

    /**
     * Set the console logger level
     * 
     * @see org.apache.avalon.framework.logger.ConsoleLogger for debugging levels
     * @param logLevel set valid logging level
     */
    public void setLogLevel(int logLevel) 
    {
        this.logLevel = logLevel;
    }
    
    public int getLogLevel()
    {
        return logLevel;
    }
    /**
     * Clean up container.
     */
    public void tearDown() 
    {
        if (container != null) 
        {
            container.dispose();
        }
        container = null;
    }

    /**
     * Gets the configuration file name for the container should use for this test.
     *
     * @return The filename of the configuration file
     */
    public String getConfigurationFileName() 
    {
        return configurationFileName;
    }

    /**
     * Gets the role file name for the container should use for this test.
     *
     * @return The filename of the role configuration file
     */
    public String getRoleFileName() 
    {
        return roleFileName;
    }

    /**
     * Gets the parameter file name for the container should use for this test.
     *
     * @return The filename of the role configuration file
     */
    public String getParameterFileName() 
    {
        return parameterFileName;
    }

    /**
     * Returns an instance of the named component. This method will also start the
     * container if it has not been started already
     *
     * @param roleName Name of the role the component fills.
     * @return instance of the component
     * @throws ComponentException generic exception
     */
    public Object lookup(String roleName) throws ComponentException 
    {
        if (container == null) 
        {
            Logger log = new ConsoleLogger(); //Log4j2Logger( null )
            container = new ContainerImpl(log);
            container.startup(getConfigurationFileName(), getRoleFileName(), getParameterFileName());
        }
        return container.lookup(roleName);
    }

    /**
     * Releases the component.
     *
     * @param component component to be released
     */
    public void release(Object component) 
    {
        if (container != null) 
        {
            container.release(component);
        }
    }

    /**
     * @return the container type
     */
    public String getContainerType() 
    {
        return containerType;
    }

    /**
     * @param containerType container type to set
     */
    public void setContainerType(String containerType) 
    {
        this.containerType = containerType;
    }
}
