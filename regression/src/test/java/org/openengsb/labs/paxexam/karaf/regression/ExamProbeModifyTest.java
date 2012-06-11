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
import org.ops4j.pax.exam.Customizer;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import java.io.File;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.ops4j.pax.swissbox.tinybundles.core.TinyBundles.modifyBundle;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class ExamProbeModifyTest {

    @Test
    public void testGettingResource() {
        InputStream is = this.getClass().getResourceAsStream("/test.cfg");
        assertNotNull("We should get the resource here", is);
    }

    @Configuration
    public Option[] config() {
        return new Option[]{ karafDistributionConfiguration("mvn:org.apache.karaf/apache-karaf/2.2.5/tar.gz", "karaf",
                "2.2.5").useDeployFolder(false).unpackDirectory(new File("target/paxexam/unpack/")),
                keepRuntimeFolder(),
                // create bundle to install
                new Customizer() {
                    @Override
                    public InputStream customizeTestProbe(InputStream testProbe) throws Exception {
                        return modifyBundle(testProbe).add("test.cfg", ExamProbeModifyTest.class.getResourceAsStream("test-resource.cfg")).build();
                    }
                }

        };
    }
}
