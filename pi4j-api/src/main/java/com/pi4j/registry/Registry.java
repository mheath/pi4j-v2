package com.pi4j.registry;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Registry.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.pi4j.common.Describable;
import com.pi4j.common.Descriptor;
import com.pi4j.io.IO;
import com.pi4j.io.IOType;
import com.pi4j.io.exception.IOInvalidIDException;
import com.pi4j.io.exception.IONotFoundException;
import com.pi4j.provider.Provider;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public interface Registry extends Describable {

    boolean exists(String id, Class<? extends IO> type);
    boolean exists(String id);

    Map<String, ? extends IO> all();

    <T extends IO> T get(String id) throws IOInvalidIDException, IONotFoundException;
    <T extends IO> T get(String id, Class<T> type) throws IOInvalidIDException, IONotFoundException;

    default <T extends IO> Map<String, T> allByType(Class<T> ioClass){
        // create a map <io-id, io-instance> of I/O instances that extend of the given IO class
        var result = new ConcurrentHashMap<String, T>();
        this.all().values().stream().filter(ioClass::isInstance).forEach(p -> {
            result.put(p.id(), ioClass.cast(p));
        });
        return Collections.unmodifiableMap(result);
    }

    default <P extends Provider> Map<String, ? extends IO> allByIoType(IOType ioType){
        return allByType(ioType.getIOClass());
    }

    default <P extends Provider> Map<String, ? extends IO> allByProvider(Class<P> providerClass){
        return allByIoType(IOType.getByProviderClass(providerClass));
    }

    default <P extends Provider> Map<String, ? extends IO> allByProvider(String providerId){

        // create a map <io-id, io-instance> of providers that extend of the given io class
        var result = this.all().values().stream()
                .filter(instance -> providerId.equalsIgnoreCase(((IO) instance).provider().id()))
                .collect(Collectors.toMap(IO::id, c->c));

        return Collections.unmodifiableMap(result);
    }

    default <P extends Provider, T extends IO> Map<String, T> allByProvider(String providerId, Class<T> ioClass){
        // create a map <io-id, io-instance> of providers that extend of the given io class
        var result = new ConcurrentHashMap<String, T>();
        this.all().values().stream()
                .filter(instance -> providerId.equalsIgnoreCase(((IO) instance).provider().id()))
                .filter(ioClass::isInstance).forEach(p -> {
            result.put(p.id(), ioClass.cast(p));
        });
        return Collections.unmodifiableMap(result);
    }

    default Descriptor describe() {

        Map<String, ? extends IO> instances = all();
        Descriptor descriptor = Descriptor.create()
                .category("REGISTRY")
                .name("I/O Registered Instances")
                .quantity((instances == null) ? 0 : instances.size())
                .type(this.getClass());

        if(instances != null && !instances.isEmpty()) {
            instances.forEach((id, instance) -> {
                descriptor.add(instance.describe());
            });
        }

        return descriptor;
    }
}