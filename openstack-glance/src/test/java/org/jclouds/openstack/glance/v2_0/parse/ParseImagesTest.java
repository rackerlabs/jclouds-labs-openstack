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
package org.jclouds.openstack.glance.v2_0.parse;

import java.util.LinkedHashMap;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.jclouds.json.BaseSetParserTest;
import org.jclouds.openstack.glance.v2_0.domain.ContainerFormat;
import org.jclouds.openstack.glance.v2_0.domain.DiskFormat;
import org.jclouds.openstack.glance.v2_0.domain.Image;
import org.jclouds.rest.annotations.SelectJson;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;

/**
 * @author Ignacio Mulas
 */
@Test(groups = "unit", testName = "ParseImageListTest")
public class ParseImagesTest extends BaseSetParserTest<Image> {

   @Override
   public String resource() {
      return "/images_v2.json";
   }

   @SuppressWarnings("serial")
   @Override
   @SelectJson("images")
   @Consumes(MediaType.APPLICATION_JSON)
   public Set<Image> expected() {
      return ImmutableSet
            .<Image> builder()
            .add(Image.builder().id("e0b7734d-2331-42a3-b19e-067adc0da17d")
                  .name("ericsson-cirros-0.3.1-x86_64-uec")
                  .containerFormat(ContainerFormat.AMI)
                  .diskFormat(DiskFormat.AMI)
                  .checksum("f8a2eeee2dc65b3d9b6e63678955bd83")
                  .minDisk(0l)
                  .minRam(0l)
                  .size(25165824l)
                  .status(Image.Status.ACTIVE)
                  .owner("f7ac731cc11f40efbc03a9f9e1d1d21f")
                  .deletedAt(null).isPublic(true)
                  .createdAt(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-08-29T19:18:26"))
                  .updatedAt(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-08-29T19:18:26"))
                  .properties(new LinkedHashMap<String, String>() {
                     {
                        put("kernel_id", "75bf193b-237b-435e-8712-896c51484de9");
                        put("ramdisk_id", "19eee81c-f972-44e1-a952-1dceee148c47");
                     }
                  }).build())
            .add(Image.builder().id("75bf193b-237b-435e-8712-896c51484de9")
                  .name("ericsson-cirros-0.3.1-x86_64-uec-kernel")
                  .containerFormat(ContainerFormat.AKI)
                  .diskFormat(DiskFormat.AKI)
                  .checksum("c352f4e7121c6eae958bc1570324f17e")
                  .size(4955792l)
                  .minDisk(0l)
                  .minRam(0l)
                  .status(Image.Status.ACTIVE)
                  .owner("f7ac731cc11f40efbc03a9f9e1d1d21f")
                  .isPublic(true)
                  .createdAt(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-08-29T19:18:24"))
                  .updatedAt(new SimpleDateFormatDateService().iso8601SecondsDateParse("2013-08-29T19:18:24")).build())
            .build();
   }

}
