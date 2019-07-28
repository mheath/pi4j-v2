package com.pi4j.example;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  GettingStartedExample.java
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

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.platform.Platform;
import com.pi4j.platform.Platforms;
import com.pi4j.provider.Providers;
import com.pi4j.registry.Registry;
import com.pi4j.util.Console;

public class GettingStartedExample {

    public static void main(String[] args) throws Exception {

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "Getting Started Example");

        // ************************************************************
        //
        // WELCOME TO Pi4J:
        //
        // Here we will use this getting started example to
        // demonstrate the basic fundamentals of the Pi4J library.
        //
        // This example is to introduce you to the boilerplate
        // logic and concepts required for all applications using
        // the Pi4J library.  This example won't actually perform
        // any I/O, there are plenty of additional examples in this
        // project that will dig deeper into each specific I/O.
        //
        // ************************************************************


        // ------------------------------------------------------------
        // Initialize the Pi4J library
        // ------------------------------------------------------------
        // Before you can use Pi4J you must initialize it first.
        // Initialization will automatically load all available Pi4J
        // extensions found in the application's classpath which
        // may include 'Platforms' and 'I/O Providers'
        //
        // There are optional arguments to the `initialize()` method
        // to disable this automatic detection and loading if you
        // need/prefer to manually configure which 'Platforms' and
        // 'I/O Providers' should be used with Pi4J.
        var context = Pi4J.initialize();

        // After we initialize Pi4J, we can access the following
        // core parts of the system:
        //
        //  - Context
        //  - Platforms
        //  - Platform (Default Runtime Platform)
        //  - Providers (I/O Providers)
        //  - Registry (I/O Registry)


        // ------------------------------------------------------------
        // Pi4J Context
        // ------------------------------------------------------------
        // When you initialize Pi4J, a 'Context' is returned.  You can
        // also directly obtain the context from the Pi4J static helper
        // class.
        //
        // The 'Context' is a shared container for all Pi4J runtime
        // information, state, extensions and lifecycle.  From the
        // `Context` object you can access just about any part of
        // the Pi4J infrastructure and system.
        //
        // The 'Context' is a static singleton in the lifecycle of
        // your application.
        Context ctx = Pi4J.context();  // <-- Also available via the Pi4J static helper


        // ------------------------------------------------------------
        // Pi4J Platforms
        // ------------------------------------------------------------
        // Platforms are intended to represent the hardware platform
        // where Pi4J is running.  In most cases this will be the
        // 'RaspberryPi' platform, but Pi4J supports and extensible
        // set of platforms thus additional platforms such as
        // 'BananaPi', 'Odroid', etc can be added.
        //
        // Platforms represent the physical layout of a system's
        // hardware I/O capabilities and what I/O providers the
        // target platform supports.  For example, a 'RaspberryPi'
        // platform supports `Digital` inputs and outputs, PWM, I2C,
        // SPI, and Serial but does not support a default provider
        // for 'Analog' inputs and outputs.
        //
        // Platforms also provide validation for the I/O pins and
        // their capabilities for the target hardware.
        Platforms platforms = context.platforms();
        //Platforms platforms = Pi4J.platforms(); // <-- Also available via the Pi4J static helper

        // let's print out to the console the detected and loaded
        // platforms that Pi4J detected when it was initialized.
        console.box("Pi4J PLATFORMS");
        platforms.describe().print(System.out);
        console.println();


        // ------------------------------------------------------------
        // Pi4J Platform (Default Platform)
        // ------------------------------------------------------------
        // A single 'default' platform is auto-assigned during Pi4J
        // initialization based on a weighting value provided by each
        // platform implementation at runtime.  Additionally, you can
        // override this behavior and assign your own 'default' platform
        // anytime after initialization.
        //
        // The default platform is a single platform instance from the
        // managed platforms collection that will serve to define the
        // default I/O providers that Pi4J will use for each given I/O
        // interface when creating and registering I/O instances.
        Platform platform = context.platform();
        //Platform platform = Pi4J.platform(); // <-- Also available via the Pi4J static helper

        // let's print out to the console the detected and loaded
        // platforms that Pi4J detected when it was initialized.
        console.box("Pi4J DEFAULT PLATFORM");
        platform.describe().print(System.out);
        console.println();


        // ------------------------------------------------------------
        // Pi4J Providers
        // ------------------------------------------------------------
        // Providers are intended to represent I/O implementations
        // and provide access to the I/O interfaces available on
        // the system.  Providers 'provide' concrete runtime
        // implementations of I/O interfaces such as:
        //
        // - DigitalInput
        // - DigitalOutput
        // - AnalogInput
        // - AnalogOutput
        // - PWM
        // - I2C
        // - SPI
        // - SERIAL
        //
        // Each platform will have a default set of providers assigned
        // to it to serve as the default providers that will be used
        // on a given platform's hardware I/O.  However, you are not
        // limited to the providers that a platform provides, you can
        // instantiate I/O interfaces using any provider that has
        // been registered on the Pi4J system.  A good example of
        // this is the 'AnalogInput' and 'AnalogOutput' I/O interfaces.
        // The 'RaspberryPi' does not inherently support analog I/O
        // hardware, but with an attached ADC (Analog to Digital Converter)
        // or DAC (Digital to Analog converter) chip attached to a
        // data bus (I2C/SPI) you may wish to use Pi4J to read/write
        // to these analog hardware interfaces.
        //
        // Providers allow for a completely flexible and extensible
        // infrastructure enabling third-parties to build and extend
        // the capabilities of Pi4J by writing your/their own
        // Provider implementation libraries.
        Providers providers = context.providers();
        //Providers providers = Pi4J.providers(); // <-- Also available via the Pi4J static helper

        // let's print out to the console the detected and loaded
        // providers that Pi4J detected when it was initialized.
        console.box("Pi4J PROVIDERS");
        providers.describe().print(System.out);
        console.println();


        // ------------------------------------------------------------
        // Pi4J Registry
        // ------------------------------------------------------------
        // Providers are intended to represent I/O implementations
        // and provide access to the I/O interfaces available on
        // the system.  Providers 'provide' concrete runtime
        // implementations of I/O interfaces such as:
        //
        // - DigitalInput
        // - DigitalOutput
        // - AnalogInput
        // - AnalogOutput
        // - PWM
        // - I2C
        // - SPI
        // - SERIAL
        //
        // Each platform will have a default set of providers assigned
        // to it to serve as the default providers that will be used
        // on a given platform's hardware I/O.  However, you are not
        // limited to the providers that a platform provides, you can
        // instantiate I/O interfaces using any provider that has
        // been registered on the Pi4J system.  A good example of
        // this is the 'AnalogInput' and 'AnalogOutput' I/O interfaces.
        // The 'RaspberryPi' does not inherently support analog I/O
        // hardware, but with an attached ADC (Analog to Digital Converter)
        // or DAC (Digital to Analog converter) chip attached to a
        // data bus (I2C/SPI) you may wish to use Pi4J to read/write
        // to these analog hardware interfaces.
        //
        // Providers allow for a completely flexible and extensible
        // infrastructure enabling third-parties to build and extend
        // the capabilities of Pi4J by writing your/their own
        // Provider implementation libraries.
        Registry registry = context.registry();
        //Registry registry = Pi4J.registry(); // <-- Also available via the Pi4J static helper

        // Here we will create an I/O interface for a (GPIO) digital output pin.
        // More comprehensive examples and descriptions are provided in other examples;
        // we just want to include a single I/O instance here as a simple example for
        // this demonstration.  Since no specific 'provider' is defined, Pi4J will
        // use the default `DigitalOutputProvider` for the current default platform.
        DigitalOutput output = DigitalOutput.create(1,"my-digital-output-1");

        // let's print out to the console the detected and loaded
        // I/O interfaces registered with Pi4J and included in the 'Registry'.
        console.box("Pi4J REGISTRY");
        registry.describe().print(System.out);
        console.println();

        // ------------------------------------------------------------
        // Terminate the Pi4J library
        // ------------------------------------------------------------
        // We we are all done and want to exit our application, we must
        // call the 'terminate()' function on the Pi4J static helper class.
        // This will ensure that all I/O instances are properly terminated,
        // released by the the system and shutdown in the appropriate
        // manner.  Terminate will also ensure that any background
        // threads/processes are cleanly shutdown and any used memory
        // is returned to the system.

        // shutdown Pi4J
        Pi4J.terminate();
    }
}
