package org.apache.fulcrum.yaafi.testcontainer;

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

import java.io.File;

import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.avalon.framework.logger.Logger;
import org.apache.fulcrum.yaafi.container.ContainerImpl;


/**
 * This is a simple YAAFI based container that can be used in unit test
 * of the fulcrum components.
 *
 * @author <a href="mailto:siegfried.goeschl@it20one.at">Siegfried Goeschl</a>
 */
public class TestContainer extends ContainerImpl
{

    /**
     * Constructor
     */
    public TestContainer(Logger logger)
    {
        super( logger );
    }

    /**
     * Starts up the container and initializes it.
     *
     * @param configFileName Name of the component configuration file
     * @param roleFileName Name of the role configuration file
     * @param parametersFileName Name of the parameters file
     */
    public void startup(
        String configFileName,
        String roleFileName,
        String parametersFileName )
    {
        getLogger().debug("Starting container...");

        this.config.setComponentConfigurationLocation( configFileName );
        this.config.setComponentRolesLocation( roleFileName );
        this.config.setParametersLocation( parametersFileName );
        this.config.setLogger( new ConsoleLogger( logLevel ) );

        File configFile = new File(configFileName);

        if (!configFile.exists())
        {
            throw new RuntimeException(
                "Could not initialize the container because the config file could not be found:" + configFile);
        }

        try
        {
            initialize();
            getLogger().info("YaffiContainer ready.");
        }
        catch (Exception e)
        {
            getLogger().error("Could not initialize the container", e);
            throw new RuntimeException("Could not initialize the container");
        }
    }

}
