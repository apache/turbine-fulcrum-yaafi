package org.apache.fulcrum.yaafi.container;

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
import java.io.IOException;
import java.io.InputStream;

import org.apache.avalon.framework.activity.Disposable;
import org.apache.avalon.framework.activity.Initializable;
import org.apache.avalon.framework.component.Component;
import org.apache.avalon.framework.component.ComponentException;
import org.apache.avalon.framework.logger.AbstractLogEnabled;
import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.avalon.framework.logger.Logger;
import org.apache.avalon.framework.service.ServiceException;
import org.apache.fulcrum.yaafi.framework.container.ServiceContainer;
import org.apache.fulcrum.yaafi.framework.factory.ServiceContainerConfiguration;
import org.apache.fulcrum.yaafi.framework.factory.ServiceContainerFactory;


/**
 * This is a simple YAAFI based container that can be used to include
 * fulcrum components.
 *
 * @author <a href="mailto:siegfried.goeschl@it20one.at">Siegfried Goeschl</a>
 */
public class ContainerImpl extends AbstractLogEnabled implements Container, Initializable, Disposable
{
    /** The YAAFI configuration */
    protected ServiceContainerConfiguration config;

    /** Component manager */
    protected ServiceContainer manager;
    

    /** The log level of the container */
    protected int logLevel = ConsoleLogger.LEVEL_INFO;

    /**
     * Constructor
     */
    public ContainerImpl(Logger logger)
    {
        this.enableLogging( logger );
        this.config = new ServiceContainerConfiguration( logLevel );
    }
    
    public ContainerImpl()
    {
        this.enableLogging( new ConsoleLogger( logLevel ) );
        this.config = new ServiceContainerConfiguration( logLevel );
    }
    
//    /**
//     * Constructor.
//     *
//     * @param logLevel the log level to be used: {@link ConsoleLogger} LEVEL_*.
//     */
//    public ContainerImpl(int logLevel)
//    {
//        logger = LogManager.getLogger( "avalon" );
//        if (logLevel == ConsoleLogger.LEVEL_DEBUG) {
//            this.logLevel = Level.DEBUG;
//        }  else if (logLevel == ConsoleLogger.LEVEL_DEBUG) {
//                this.logLevel = Level.DEBUG;
//        }  else if (logLevel == ConsoleLogger.LEVEL_INFO) {
//            this.logLevel = Level.INFO;
//        }  else if (logLevel == ConsoleLogger.LEVEL_WARN) {
//            this.logLevel = Level.WARN;
//        }  else if (logLevel == ConsoleLogger.LEVEL_ERROR) {
//            this.logLevel = Level.ERROR;
//        }  else if (logLevel == ConsoleLogger.LEVEL_FATAL) {
//            this.logLevel = Level.FATAL;
//        }  else if (logLevel == ConsoleLogger.LEVEL_DISABLED) {
//            this.logLevel = Level.OFF;
//        } else {
//            this.logLevel = Level.INFO;
//        }
//        Configurator.setLevel( "avalon", this.logLevel );
//        this.enableLogging( new Log4j2Logger( logger ) );
//        this.config = new ServiceContainerConfiguration();
//    }

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
        this.config.setLogger( getLogger( ) );

        File configFile = new File(configFileName);

        if (!configFile.exists())
        {
            InputStream configIO = getClass().getResourceAsStream(configFileName);
            if (configIO == null)
            {
               throw new RuntimeException(
                   "Could not initialize the container because the config file could not be found:" + configFileName);
            }
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

    // -------------------------------------------------------------
    // Avalon lifecycle interfaces
    // -------------------------------------------------------------

    /* (non-Javadoc)
     * Initializes the container
     * @see org.apache.avalon.framework.activity.Initializable#initialize()
     */
    public void initialize() throws Exception
    {
        this.manager = ServiceContainerFactory.create(
            this.config
            );
    }

    /**
     * Disposes of the container and releases resources
     */
    public void dispose()
    {
        getLogger().debug("Disposing of container...");
        this.manager.dispose();
        getLogger().info("YaffiContainer has been disposed.");
    }

    /**
     * Returns an instance of the named component
     *
     * @param roleName Name of the role the component fills.
     * @return the instance of the named component
     * @throws ComponentException generic exception
     */
    public Object lookup(String roleName) throws ComponentException
    {
        try
        {
            return this.manager.lookup(roleName);
        }
        catch( Exception e )
        {
            String msg = "Failed to lookup role " + roleName;
            throw new ComponentException(roleName,msg,e);
        }
    }

    /**
     * Releases the component implementing the Component interface. This
     * interface is deprecated but still around in Fulcrum
     *
     * @param component the named component to release
     */
    public void release(Component component)
    {
        this.manager.release(component);
    }

    /**
     * Releases the component
     *
     * @param component the named component to release
     */
    public void release(Object component)
    {
        //ServiceManagerService.getServiceManager().release(component);
        this.manager.release(component);
    }

    /**
     * Decommission the service
     * @param name the name of the service
     * @throws ServiceException if the service is not found
     */
    public void decommission( String name )
        throws ServiceException
    {
        if( this.manager != null )
        {
            this.manager.decommission( name );
        }
    }

    public int getLogLevel()
    {
        return logLevel;
    }

    public void setLogLevel(int logLevel)
    {
        this.logLevel = logLevel;
    }
}
