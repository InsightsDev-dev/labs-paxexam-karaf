/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openengsb.labs.paxexam.karaf.regression;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.ops4j.pax.swissbox.tinybundles.dp.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.assertEquals;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.swissbox.tinybundles.core.TinyBundles.newBundle;
import static org.ops4j.pax.swissbox.tinybundles.core.TinyBundles.withBnd;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class KarafWithBundleTest {
    @Test
    public void testService() throws Exception {

        URL url = new URL("http://localhost:9080/test/services");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write("This is a test");
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        line = rd.readLine();
        assertEquals("Get a Wrong response", "This is a test", line);
        wr.close();
        rd.close();


    }

    @Configuration
    public Option[] config() {
        return new Option[]{ karafDistributionConfiguration("mvn:org.apache.karaf/apache-karaf/2.2.5/tar.gz", "karaf",
                "2.2.5").unpackDirectory(new File("target/paxexam/unpack/")),
                keepRuntimeFolder(),
                scanFeatures(maven().groupId("org.apache.karaf.assemblies.features").artifactId("standard").type("xml")
                        .classifier("features").version("2.2.5"), "war").start(),
                // set the system property for pax web
                KarafDistributionOption.editConfigurationFilePut("etc/system.properties","org.osgi.service.http.port", "9080"),
                // create bundle to install
                streamBundle(newBundle().add(org.openengsb.labs.paxexam.karaf.regression.supports.MyServlet.class)
                        .add(org.openengsb.labs.paxexam.karaf.regression.supports.ServletActivator.class)
                        .set(Constants.BUNDLE_SYMBOLICNAME, "MyBundleTest")
                        .set(Constants.BUNDLE_ACTIVATOR, "org.openengsb.labs.paxexam.karaf.regression.supports.ServletActivator")
                        .build(withBnd()))

        };
    }
}
