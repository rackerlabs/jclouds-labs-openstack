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
package org.jclouds.openstack.heat.v1.features;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.List;

import org.jclouds.openstack.heat.v1.HeatApi;
import org.jclouds.openstack.heat.v1.domain.AutoStack;
import org.jclouds.openstack.heat.v1.domain.Stack;
import org.jclouds.openstack.heat.v1.internal.BaseHeatApiMockTest;
import org.testng.annotations.Test;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

/**
 * Tests annotation parsing of {@code StackApi}
 */
@Test(groups = "unit", testName = "StackApiMockTest")
public class StackApiMockTest extends BaseHeatApiMockTest {

   public void testGetAutoStack() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(
            new MockResponse().setResponseCode(200).setBody(stringFromResource("/stack_get_response.json"))));

      try {
         HeatApi heatApi = api(server.getUrl("/").toString(), "openstack-heat", overrides);
         StackApi api = heatApi.getStackApi("RegionOne");

         AutoStack stack = api.get("simple_stack", "3095aefc-09fb-4bc7-b1f0-f21a304e864c");
         System.out.println(stack);

         Method[] methods = stack.getClass().getDeclaredMethods();
         for (Method m : methods) {
            System.out.println(m.getName());
         }

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/stacks/simple_stack/3095aefc-09fb-4bc7-b1f0-f21a304e864c");

         /*
          * Check response
          */
         assertThat(stack).isNotNull();
         //assertThat(stacks.size()).isEqualTo(1);

      } finally {
         server.shutdown();
      }

   }
   public void testList() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(
            new MockResponse().setResponseCode(200).setBody(stringFromResource("/stack_list_response.json"))));

      try {
         HeatApi heatApi = api(server.getUrl("/").toString(), "openstack-heat", overrides);
         StackApi api = heatApi.getStackApi("RegionOne");

         List<Stack> stacks = api.list().toList();

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/stacks");

         /*
          * Check response
          */
         assertThat(stacks).isNotEmpty();
         assertThat(stacks.size()).isEqualTo(1);

      } finally {
         server.shutdown();
      }
   }

   public void testListIsEmpty() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404)));

      try {
         HeatApi heatApi = api(server.getUrl("/").toString(), "openstack-heat", overrides);
         StackApi api = heatApi.getStackApi("RegionOne");

         List<Stack> stacks = api.list().toList();

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/stacks");

         /*
          * Check response
          */
         assertThat(stacks).isEmpty();
      } finally {
         server.shutdown();
      }
   }
}
