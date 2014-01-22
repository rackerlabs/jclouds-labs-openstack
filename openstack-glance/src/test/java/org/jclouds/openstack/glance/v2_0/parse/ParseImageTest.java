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

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.jclouds.json.BaseItemParserTest;
import org.jclouds.openstack.glance.v2_0.domain.Image;
import org.testng.annotations.Test;

/**
 * @author Ignacio Mulas
 */
@Test(groups = "unit", testName = "ParseImageTest")
public class ParseImageTest extends BaseItemParserTest<Image> {

	@Override
	public String resource() {
		return "/image_v2.json";
	}

   @Override   
   @Consumes(MediaType.APPLICATION_JSON)
   public Image expected() {
	  List<String> tags = new LinkedList<String> ();
	  tags.add("ping");
	  tags.add("pong");
     return Image
              .builder()
              .id("da3b75d9-3f4a-40e7-8a2c-bfab23927dea")
              .name("ericsson-cirros-0.3.0-x86_64-uec-ramdisk")
              .visibility("public")
              .checksum("2cec138d7dae2aa59038ef8c9aec2390")
              .size(2254249l)
              .status(Image.Status.ACTIVE)
              .createdAt(new SimpleDateFormatDateService().iso8601SecondsDateParse("2012-08-10T19:23:50Z"))
              .updatedAt(new SimpleDateFormatDateService().iso8601SecondsDateParse("2012-08-10T19:23:50Z"))
              .tags(tags)                  
              .self("/v2/images/da3b75d9-3f4a-40e7-8a2c-bfab23927dea")
              .file("/v2/images/da3b75d9-3f4a-40e7-8a2c-bfab23927dea/file")
              .schema("/v2/schemas/image")
              .build();     
   }
}
