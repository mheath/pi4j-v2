package com.pi4j.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DigitalOutputConfig.java
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

import com.pi4j.config.exception.ConfigException;

import java.util.Properties;

public interface DigitalOutputConfig extends DigitalConfig<DigitalOutputConfig> {
    String SHUTDOWN_STATE_KEY = "shutdown";
    String INITIAL_STATE_KEY = "initial";

    DigitalState shutdownState();
    DigitalOutputConfig shutdownState(DigitalState state);
    default DigitalState getShutdownState(){
        return shutdownState();
    }
    default void setShutdownState(DigitalState state){
        this.shutdownState(state);
    }

    DigitalState initialState();
    default DigitalState getInitialState(){
        return initialState();
    }
}
