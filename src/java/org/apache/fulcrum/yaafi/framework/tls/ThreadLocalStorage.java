package org.apache.fulcrum.yaafi.framework.tls;

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

/**
 * Provides a service which can temporarily store
 * thread-local data. This is useful in a multithreaded
 * environment, such as a servlet or Tapestry application.
 * ThreadLocalStorage acts like a map around thread local data.
 *
 * The code was pasted from the Hivemind container written by
 * Howard Lewis Ship.
 *
 * @author <a href="mailto:siegfried.goeschl@it20one.at">Siegfried Goeschl</a>
 */

public interface ThreadLocalStorage
{
    /**
	 * Returns the thread-local object for the given key, or null
	 * if no such object exists.
	 *
	 * @param key the key for the lookup
	 * @return the object
	 */
	public Object get(String key);

	/**
	 * Stores the value object at the given key, overwriting
	 * any prior value that may have been stored at that key.
	 * Care should be taken in selecting keys to avoid
	 * naming conflicts; in general, prefixing a key with
	 * a module id is a good idea.
	 *
	 * @param key the key of the object to store
	 * @param value the value of the object to store
	 */
	public void put(String key, Object value);

	/**
	 * Checks if the thread-local object for the given key exists
	 *
	 * @param key the key for the lookup
	 * @return true the object exists
	 */
	public boolean containsKey(String key);

	/**
	 * Clears all keys.
	 */
	public void clear();
}
