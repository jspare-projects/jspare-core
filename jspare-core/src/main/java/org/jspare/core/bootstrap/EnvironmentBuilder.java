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
package org.jspare.core.bootstrap;

import org.jspare.core.container.Environment;
import org.jspare.core.container.InjectorStrategy;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.Errors;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import static org.jspare.core.container.Environment.*;

/**
 * Builder your Environment with this implementation of Builder.
 */
public class EnvironmentBuilder implements Builder {

    /**
     * The bundles.
     */
    private List<Class<? extends Bundle>> bundles;
    /**
     * The clazz components.
     */
    private List<Class<?>> clazzComponents;
    /**
     * The declared components.
     */
    private List<String> declaredComponents;

    /**
     * Instantiates a new application builder.
     */
    public EnvironmentBuilder() {

        bundles = new ArrayList<>();
        clazzComponents = new ArrayList<>();
        declaredComponents = new ArrayList<>();
    }

    /**
     * Creates the.
     *
     * @return the application builder
     */
    public static EnvironmentBuilder create() {

        return new EnvironmentBuilder();
    }

    /**
     * Adds the bundle.
     *
     * @param bundleClazz the bundle clazz
     * @return the application builder
     */
    public EnvironmentBuilder addBundle(Class<? extends Bundle> bundleClazz) {

        bundles.add(bundleClazz);
        return this;
    }

    @Deprecated
    public EnvironmentBuilder addInjector(Class<? extends Annotation> annClazz, InjectorStrategy injector) {

        Environment.registryInjector(annClazz, injector);
        return this;
    }

    public EnvironmentBuilder addInjector(InjectorStrategy injector) {

        Environment.registryInjector(injector);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jspare.core.container.Builder#build()
     */
    @Override
    public void build() {

        scanAndRegistryComponents(declaredComponents);

        clazzComponents.forEach(clazz -> Environment.registryComponent(clazz));

        bundles.forEach(clazz -> {

            try {
                Bundle instance = clazz.newInstance();
                instance.registryComponents();
            } catch (InstantiationException | IllegalAccessException e) {

                throw new EnvironmentException(Errors.FAILED_INSTANTIATION.throwable(e));
            }
        });

    }

    /**
     * Registry component.
     *
     * @param clazz the clazz
     * @return the application builder
     */
    public EnvironmentBuilder registryComponent(Class<?> clazz) {

        clazzComponents.add(clazz);
        return this;
    }

    /**
     * Registry component.
     *
     * @param clazz the clazz
     * @return the application builder
     */
    public EnvironmentBuilder registryComponent(String clazz) {

        declaredComponents.add(clazz);
        return this;
    }

    /**
     * Scan one class or package for add to environment of container.
     *
     * @param package2scan the package2scan
     * @return the application builder
     */
    public EnvironmentBuilder scan(String package2scan) {

        String packageFormated = package2scan.endsWith(".*") ? package2scan : String.format("%s.*", package2scan);
        declaredComponents.add(packageFormated);
        return this;
    }
}