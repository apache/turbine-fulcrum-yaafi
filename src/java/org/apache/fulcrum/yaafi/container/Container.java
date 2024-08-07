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

import org.apache.avalon.framework.activity.Disposable;
import org.apache.avalon.framework.activity.Initializable;
import org.apache.avalon.framework.component.ComponentException;

/**
 * This is a simple interface around the ECM and Yaafi containers
 *
 * @author <a href="mailto:epugh@opensourceconnections.com">Eric Pugh</a>
 */
public interface Container extends Initializable, Disposable
{
	/** Key used in the context for defining the application root */
    String COMPONENT_APP_ROOT = "componentAppRoot";

    /** Alternate Merlin Friendly Key used in the context for defining the application root */
    String URN_AVALON_HOME = "urn:avalon:home";

    /** Alternate Merlin Friendly Key used in the context for defining the application root */
    String URN_AVALON_TEMP = "urn:avalon:temp";

    void startup(String configFileName, String roleFileName, String parametersFileName);

    Object lookup(String roleName) throws ComponentException;

    void release(Object component);

    //void decommission(String name);
}
