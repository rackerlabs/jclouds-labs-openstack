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
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;

import org.jclouds.io.payloads.StringPayload;
import org.jclouds.openstack.glance.v2_0.domain.ContainerFormat;
import org.jclouds.openstack.glance.v2_0.domain.DiskFormat;
import org.jclouds.openstack.glance.v2_0.domain.Image;
import org.jclouds.openstack.glance.v2_0.internal.BaseGlanceApiLiveTest;
import org.jclouds.openstack.glance.v2_0.options.CreateImageOptions;
import org.jclouds.openstack.glance.v2_0.options.ListImageOptions;
import org.jclouds.openstack.glance.v2_0.options.UpdateImageOptions;
import org.jclouds.util.Strings2;
import org.testng.annotations.Test;

/**
 * @author Ignacio Mulas
 */
@Test(groups = "live", testName = "ImageApiLiveTest")
public class ImageApiLiveTest extends BaseGlanceApiLiveTest {

   private void checkImage(Image image) {
      assertNotNull(image.getId());
      assertNotNull(image.getCreatedAt());
      assertNotNull(image.getUpdatedAt());
   }

   private void checkImageEqual(Image imageListItem, Image image) {
      assertEquals(imageListItem.getId(), image.getId());
      assertEquals(imageListItem.getName(), image.getName());
      assertEquals(imageListItem.getLinks(), image.getLinks());
      assertEquals(imageListItem.getCreatedAt(), image.getCreatedAt());
      assertEquals(imageListItem.getUpdatedAt(), image.getUpdatedAt());
   }

   @Test
   public void testListShowImage() throws Exception {
      for (String zoneId : api.getConfiguredZones()) {
         ImageApi imageApi = api.getImageApiForZone(zoneId);
         Set<? extends Image> response = imageApi.list().concat().toSet();
         assertNotNull(response);
         for (Image imageListItem : response) {
            checkImage(imageListItem);
            Image image = imageApi.get(imageListItem.getId());
            checkImage(image);
            checkImageEqual(imageListItem, image);
         }
      }
   }

   @Test
   public void testCreateDeleteImage() {
      for (String zoneId : api.getConfiguredZones()) {
         ImageApi imageApi = api.getImageApiForZone(zoneId);
         Image createdImage = imageApi
               .create(CreateImageOptions.Builder.name("jclouds-live-test").tags("tag1", "tag2"));

         Image image = imageApi.get(createdImage.getId());
         assertEquals(createdImage.getName(), image.getName());

         // TODO : Include update when working
         imageApi.update(image.getId(),
               UpdateImageOptions.Builder.name(UpdateImageOptions.Operation.REMOVE, "jclouds-live-test2"));
         assertEquals(image.getName(), "jclouds-live-test2");

         image = imageApi.get(createdImage.getId());
         assertEquals(createdImage.getName(), image.getName());

         assertTrue(imageApi.delete(image.getId()));

         assertTrue(imageApi.list(ListImageOptions.Builder.name("jclouds-live-test")).isEmpty());
      }
   }

   @Test
   public void tesCreateUploadDownloadAndDeleteImage() throws IOException {
      StringPayload imageData = new StringPayload("This isn't an image!");
      for (String zoneId : api.getConfiguredZones()) {
         ImageApi imageApi = api.getImageApiForZone(zoneId);
         Image image = imageApi.create(CreateImageOptions.Builder.name("jclouds-live-res-test")
               .containerFormat(ContainerFormat.BARE).diskFormat(DiskFormat.RAW));
         assertEquals(image.getName(), "jclouds-live-res-test");
         assertEquals(image.getContainerFormat(), ContainerFormat.BARE);
         assertEquals(image.getDiskFormat(), DiskFormat.RAW);

         imageApi.upload(image.getId(), imageData);
         assertEquals(Strings2.toStringAndClose(imageApi.download(image.getId())), "This isn't an image!");

         assertTrue(imageApi.delete(image.getId()));
         assertTrue(imageApi.list(ListImageOptions.Builder.name("jclouds-live-res-test")).isEmpty());
      }
   }

}
