/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.openstack.heat.v1.internal;

import java.util.Properties;
import java.util.Set;

import org.jclouds.apis.BaseApiLiveTest;
import org.jclouds.location.reference.LocationConstants;
import org.jclouds.openstack.heat.v1.HeatApi;
import org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;

/**
 * Tests behavior of {@link HeatApi}.
 *
 */
@Test(groups = "live", testName = "BaseHeatApiLiveTest")
public class BaseHeatApiLiveTest extends BaseApiLiveTest<HeatApi> {

   protected Set<String> regions;

   public BaseHeatApiLiveTest() {
      provider = "openstack-heat";
   }

   @Override
   protected Properties setupProperties() {
      Properties props = super.setupProperties();
      setIfTestSystemPropertyPresent(props, KeystoneProperties.CREDENTIAL_TYPE);
      setIfTestSystemPropertyPresent(props, LocationConstants.PROPERTY_REGION);
      return props;
   }

   @Override
   @BeforeClass(groups = "live")
   public void setup() {
      super.setup();
      String providedRegion = System.getProperty("test." + LocationConstants.PROPERTY_REGION);
      if (providedRegion != null) {
        regions = ImmutableSet.of(providedRegion);
      } else {
        regions = api.getConfiguredRegions();
      }

      // create a stack
   }

}
