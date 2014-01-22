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
package org.jclouds.openstack.glance.v2_0.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.jclouds.apis.ApiMetadata;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.jclouds.io.Payloads;
import org.jclouds.openstack.glance.v2_0.GlanceApiMetadata;
import org.jclouds.openstack.glance.v2_0.GlanceApi;
import org.jclouds.openstack.glance.v2_0.internal.BaseGlanceApiExpectTest;
import org.jclouds.openstack.glance.v2_0.options.CreateImageOptions;
import org.jclouds.openstack.glance.v2_0.options.UpdateImageOptions;
import org.jclouds.openstack.glance.v2_0.options.UpdateImageOptions.Operation;
import org.jclouds.openstack.glance.v2_0.parse.ParseImageTest;
import org.jclouds.openstack.glance.v2_0.parse.ParseImagesTest;
import org.jclouds.rest.AuthorizationException;
import org.jclouds.rest.ResourceNotFoundException;
import org.jclouds.util.Strings2;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;

/**
 * @author Ignacio Mulas
 */
@Test(groups = "unit", testName = "ImageApiExpectTest")
public class ImageApiExpectTest extends BaseGlanceApiExpectTest {

   public void testListWhenResponseIs2xx() throws Exception {
      HttpRequest list = HttpRequest.builder().method("GET").endpoint("https://glance.jclouds.org:9292/v2/images")
            .addHeader("Accept", "application/json").addHeader("X-Auth-Token", authToken).build();

      HttpResponse listResponse = HttpResponse.builder().statusCode(200).payload(payloadFromResource("/images_v2.json"))
            .build();

      GlanceApi apiWhenExist = requestsSendResponses(keystoneAuthWithUsernameAndPassword, responseWithKeystoneAccess,
            list, listResponse);

      assertEquals(apiWhenExist.getConfiguredZones(), ImmutableSet.of("az-1.region-a.geo-1"));

      assertEquals(apiWhenExist.getImageApiForZone("az-1.region-a.geo-1").list().concat().toString(),
            new ParseImagesTest().expected().toString());
   }

   public void testListWhenReponseIs404IsEmpty() throws Exception {
      HttpRequest list = HttpRequest.builder().method("GET").endpoint("https://glance.jclouds.org:9292/v2/images")
            .addHeader("Accept", "application/json").addHeader("X-Auth-Token", authToken).build();

      HttpResponse listResponse = HttpResponse.builder().statusCode(404).build();

      GlanceApi apiWhenNoExistInDetail = requestsSendResponses(keystoneAuthWithUsernameAndPassword,
            responseWithKeystoneAccess, list, listResponse);

      assertTrue(apiWhenNoExistInDetail.getImageApiForZone("az-1.region-a.geo-1").list().concat().isEmpty());
   }

   public void testShowWhenResponseIs2xx() throws Exception {
      HttpRequest show = HttpRequest.builder().method("GET")
            .endpoint("https://glance.jclouds.org:9292/v2/images/da3b75d9-3f4a-40e7-8a2c-bfab23927dea")
            .addHeader("Accept", MediaType.APPLICATION_JSON).addHeader("X-Auth-Token", authToken).build();

      HttpResponse showResponse = HttpResponse.builder().statusCode(200).payload(payloadFromResource("/image_v2.json"))
            .build();

      GlanceApi apiWhenExist = requestsSendResponses(keystoneAuthWithUsernameAndPassword, responseWithKeystoneAccess,
            show, showResponse);

      assertEquals(apiWhenExist.getConfiguredZones(), ImmutableSet.of("az-1.region-a.geo-1"));

      assertEquals(apiWhenExist.getImageApiForZone("az-1.region-a.geo-1").get("da3b75d9-3f4a-40e7-8a2c-bfab23927dea")
            .toString(), new ParseImageTest().expected().toString());
   }

   public void testShowWhenReponseIs404IsNull() throws Exception {
      HttpRequest show = HttpRequest.builder().method("GET")
            .endpoint("https://glance.jclouds.org:9292/v2/images/da3b75d9-3f4a-40e7-8a2c-bfab23927dea")
            .addHeader("Accept", MediaType.APPLICATION_JSON).addHeader("X-Auth-Token", authToken).build();

      HttpResponse showResponse = HttpResponse.builder().statusCode(404).build();

      GlanceApi apiWhenNoExist = requestsSendResponses(keystoneAuthWithUsernameAndPassword, responseWithKeystoneAccess,
            show, showResponse);

      assertNull(apiWhenNoExist.getImageApiForZone("az-1.region-a.geo-1").get("da3b75d9-3f4a-40e7-8a2c-bfab23927dea"));
   }

   public void testDownloadWhenResponseIs2xx() throws Exception {
      HttpRequest get = HttpRequest.builder().method("GET")
            .endpoint("https://glance.jclouds.org:9292/v2/images/da3b75d9-3f4a-40e7-8a2c-bfab23927dea/file")
            .addHeader("X-Auth-Token", authToken).build();

      HttpResponse getResponse = HttpResponse.builder().statusCode(200).payload(Payloads.newStringPayload("foo"))
            .build();

      GlanceApi apiWhenExist = requestsSendResponses(keystoneAuthWithUsernameAndPassword, responseWithKeystoneAccess,
            get, getResponse);

      assertEquals(apiWhenExist.getConfiguredZones(), ImmutableSet.of("az-1.region-a.geo-1"));

      assertEquals(
            Strings2.toStringAndClose(apiWhenExist.getImageApiForZone("az-1.region-a.geo-1").download(
                  "da3b75d9-3f4a-40e7-8a2c-bfab23927dea")), "foo");
   }

   public void testDownloadWhenReponseIs404IsNull() throws Exception {
      HttpRequest get = HttpRequest.builder().method("GET")
            .endpoint("https://glance.jclouds.org:9292/v2/images/da3b75d9-3f4a-40e7-8a2c-bfab23927dea/file")
            .addHeader("X-Auth-Token", authToken).build();

      HttpResponse getResponse = HttpResponse.builder().statusCode(404).build();

      GlanceApi apiWhenNoExist = requestsSendResponses(keystoneAuthWithUsernameAndPassword, responseWithKeystoneAccess,
            get, getResponse);

      assertNull(apiWhenNoExist.getImageApiForZone("az-1.region-a.geo-1").download(
            "da3b75d9-3f4a-40e7-8a2c-bfab23927dea"));
   }
   
   public void testUpdateWhenResponseIs2xx() throws Exception {
      HttpRequest patch = HttpRequest.builder().method("PATCH")
            .endpoint("https://glance.jclouds.org:9292/v2/images/da3b75d9-3f4a-40e7-8a2c-bfab23927dea")
            .payload(payloadFromStringWithContentType("[{\"op\":\"replace\",\"path\":\"/name\",\"value\":\"new-image-name\"}]", "application/openstack-images-v2.0-json-patch"))
            .addHeader("Accept", MediaType.APPLICATION_JSON).addHeader("X-Auth-Token", authToken).build();
      
      HttpResponse createResponse = HttpResponse.builder().statusCode(200).payload(payloadFromResource("/image_v2.json"))
            .build();

      GlanceApi apiWhenExist = requestsSendResponses(keystoneAuthWithUsernameAndPassword, responseWithKeystoneAccess,
            patch, createResponse);

      assertEquals(apiWhenExist.getConfiguredZones(), ImmutableSet.of("az-1.region-a.geo-1"));

      assertEquals(
            apiWhenExist.getImageApiForZone("az-1.region-a.geo-1").update("da3b75d9-3f4a-40e7-8a2c-bfab23927dea",
                  UpdateImageOptions.Builder.property(Operation.REPLACE, "name", "new-image-name")),
            new ParseImageTest().expected());
   }
   
   @Test(expectedExceptions = ResourceNotFoundException.class)
   public void testUpdateWhenReponseIs404IsNull() throws Exception {
      HttpRequest patch = HttpRequest.builder().method("PATCH")
            .endpoint("https://glance.jclouds.org:9292/v2/images/da3b75d9-3f4a-40e7-8a2c-bfab23927dea")
            .payload(payloadFromStringWithContentType("[{\"op\":\"replace\",\"path\":\"/name\",\"value\":\"new-image-name\"}]", "application/openstack-images-v2.0-json-patch"))
            .addHeader("Accept", MediaType.APPLICATION_JSON).addHeader("X-Auth-Token", authToken).build();
      
      HttpResponse getResponse = HttpResponse.builder().statusCode(404).build();

      GlanceApi apiWhenNoExist = requestsSendResponses(keystoneAuthWithUsernameAndPassword, responseWithKeystoneAccess,
            patch, getResponse);

      assertNull(apiWhenNoExist.getImageApiForZone("az-1.region-a.geo-1").update(
            "da3b75d9-3f4a-40e7-8a2c-bfab23927dea", UpdateImageOptions.Builder.property(Operation.REPLACE, "name", "new-image-name")));
   }

   public void testCreateWhenResponseIs2xx() throws Exception {
      HttpRequest patch = HttpRequest.builder().method("POST")
            .endpoint("https://glance.jclouds.org:9292/v2/images")
            .payload(payloadFromStringWithContentType("{\"name\":\"test-glance-create\",\"tags\":[\"ping\",\"pong\"]}", MediaType.APPLICATION_JSON))
            .addHeader("Accept", MediaType.APPLICATION_JSON).addHeader("X-Auth-Token", authToken).build();
      
      HttpResponse createResponse = HttpResponse.builder().statusCode(200).payload(payloadFromResource("/image_v2.json"))
            .build();

      GlanceApi apiWhenExist = requestsSendResponses(keystoneAuthWithUsernameAndPassword, responseWithKeystoneAccess,
            patch, createResponse);

      assertEquals(apiWhenExist.getConfiguredZones(), ImmutableSet.of("az-1.region-a.geo-1"));

      assertEquals(
            apiWhenExist.getImageApiForZone("az-1.region-a.geo-1").create(
                  CreateImageOptions.Builder.name("test-glance-create").tags("ping", "pong")),
            new ParseImageTest().expected());
   }

   @Test(expectedExceptions = AuthorizationException.class)
   public void testCreateWhenResponseIs4xx() throws Exception {
      HttpRequest get = HttpRequest
            .builder()
            .method("POST")
            .endpoint("https://glance.jclouds.org:9292/v2/images")
            .addHeader("Accept", MediaType.APPLICATION_JSON)
            .addHeader("X-Auth-Token", authToken)
            .payload(
                  payloadFromStringWithContentType("{\"name\":\"test-glance-create\",\"tags\":[\"ping\",\"pong\"]}",
                        MediaType.APPLICATION_JSON)).build();

      HttpResponse createResponse = HttpResponse.builder().statusCode(403).payload(payloadFromResource("/image_v2.json"))
            .build();

      GlanceApi apiWhenExist = requestsSendResponses(keystoneAuthWithUsernameAndPassword, responseWithKeystoneAccess,
            get, createResponse);

      assertEquals(apiWhenExist.getConfiguredZones(), ImmutableSet.of("az-1.region-a.geo-1"));

      apiWhenExist.getImageApiForZone("az-1.region-a.geo-1").create(
            CreateImageOptions.Builder.name("test-glance-create").tags("ping", "pong"));
   }

   public void testDeleteWhenResponseIs2xx() throws Exception {
      HttpRequest get = HttpRequest.builder().method("DELETE")
            .endpoint("https://glance.jclouds.org:9292/v2/images/da3b75d9-3f4a-40e7-8a2c-bfab23927dea")
            .addHeader("X-Auth-Token", authToken).build();

      HttpResponse getResponse = HttpResponse.builder().statusCode(200).build();

      GlanceApi apiWhenExist = requestsSendResponses(keystoneAuthWithUsernameAndPassword, responseWithKeystoneAccess,
            get, getResponse);

      assertEquals(apiWhenExist.getConfiguredZones(), ImmutableSet.of("az-1.region-a.geo-1"));

      assertTrue(apiWhenExist.getImageApiForZone("az-1.region-a.geo-1").delete("da3b75d9-3f4a-40e7-8a2c-bfab23927dea"));
   }

   public void testDeleteWhenResponseIs4xx() throws Exception {
      HttpRequest get = HttpRequest.builder().method("DELETE")
            .endpoint("https://glance.jclouds.org:9292/v2/images/da3b75d9-3f4a-40e7-8a2c-bfab23927dea")
            .addHeader("X-Auth-Token", authToken).build();

      HttpResponse getResponse = HttpResponse.builder().statusCode(404).build();

      GlanceApi apiWhenExist = requestsSendResponses(keystoneAuthWithUsernameAndPassword, responseWithKeystoneAccess,
            get, getResponse);

      assertEquals(apiWhenExist.getConfiguredZones(), ImmutableSet.of("az-1.region-a.geo-1"));

      assertFalse(apiWhenExist.getImageApiForZone("az-1.region-a.geo-1").delete("da3b75d9-3f4a-40e7-8a2c-bfab23927dea"));
   }
   
   @Override
   protected ApiMetadata createApiMetadata() {
      return new GlanceApiMetadata();
   }
}
