/*
 * Copyright 2016 JSpare.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jspare.core.serializer;

import org.jspare.core.container.Component;

/**
 * The Interface Serializer.
 */
@Component
@FunctionalInterface
public interface Serializer {

	/**
	 * Convert.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param value
	 *            the value
	 * @return the t
	 */
	<T> T convert(Class<T> clazz, String value);
}
