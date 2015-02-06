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
package org.jclouds.openstack.poppy.v1.features;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.jclouds.openstack.poppy.v1.PoppyApi;
import org.jclouds.openstack.poppy.v1.domain.Caching;
import org.jclouds.openstack.poppy.v1.domain.Domain;
import org.jclouds.openstack.poppy.v1.domain.Origin;
import org.jclouds.openstack.poppy.v1.domain.Restriction;
import org.jclouds.openstack.poppy.v1.domain.Service;
import org.jclouds.openstack.poppy.v1.internal.BasePoppyApiMockTest;
import org.jclouds.openstack.poppy.v1.options.ServiceCreateOptions;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

/**
 * Tests annotation parsing of {@code ServiceApi}
 */
@Test(groups = "unit", testName = "ServiceApiMockTest")
public class ServiceApiMockTest extends BasePoppyApiMockTest {

   public void testCreateService() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(202))
                        .setHeader(HttpHeaders.LOCATION, "https://poppycdn.org/v1.0/services/123123"));

      try {
         PoppyApi poppyApi = api(server.getUrl("/").toString(), "openstack-poppy", overrides);
         ServiceApi api = poppyApi.getServiceApi();

         ServiceCreateOptions options = ServiceCreateOptions.builder()
               .name("mywebsite.com")
               .flavorId("cdn")
               .domains(ImmutableList.of(
                     Domain.builder().domain("www.mywebsite.com").build(),
                     Domain.builder().domain("blog.mywebsite.com").build()))
               .origins(ImmutableList.of(
                     Origin.builder().origin("mywebsite.com").port(80).sslEnabled(false).build()))
               .restrictions(ImmutableList.of(
                     Restriction.builder()
                        .name("website only")
                        .rules(ImmutableList.of(
                              Restriction.Rule.builder().name("mywebsite.com").referrer("www.mywebsite.com").build()))
                        .build()))
               .caching(ImmutableList.of(
                     Caching.builder().name("default").ttl(3600).rules(ImmutableList.<Caching.Rule> of()).build()))
               .build();

         URI uri = api.create(options);

         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/services");

         assertThat(uri).isEqualTo(URI.create("https://poppycdn.org/v1.0/services/123123"));

      } finally {
         server.shutdown();
      }
   }

   public void testGetService() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(
            new MockResponse().setResponseCode(200).setBody(stringFromResource("/poppy_service_get_response.json"))));

      try {
         PoppyApi poppyApi = api(server.getUrl("/").toString(), "openstack-poppy", overrides);
         ServiceApi api = poppyApi.getServiceApi();

         Service oneService = api.get("96737ae3-cfc1-4c72-be88-5d0e7cc9a3f0");

         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/services/96737ae3-cfc1-4c72-be88-5d0e7cc9a3f0");

         assertThat(oneService).isNotNull();

      } finally {
         server.shutdown();
      }
   }

   public void testGetServiceFailOn404() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404)));

      try {
         PoppyApi poppyApi = api(server.getUrl("/").toString(), "openstack-poppy", overrides);
         ServiceApi api = poppyApi.getServiceApi();

         Service oneService  = api.get("unknown");

         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/services/unknown");

         assertThat(oneService).isNull();

      } finally {
         server.shutdown();
      }
   }

}
