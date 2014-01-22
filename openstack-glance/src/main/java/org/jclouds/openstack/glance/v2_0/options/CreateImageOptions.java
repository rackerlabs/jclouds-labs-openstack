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
package org.jclouds.openstack.glance.v2_0.options;

import static com.google.common.base.Objects.equal;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.jclouds.http.HttpRequest;
import org.jclouds.openstack.glance.v2_0.domain.ContainerFormat;
import org.jclouds.openstack.glance.v2_0.domain.DiskFormat;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ImmutableList;

/**
 * Image's options
 * 
 * @author Ignacio Mulas
 * @see <a href="http://docs.openstack.org/api/openstack-image-service/2.0/content/create-an-image.html"/>
 */
public class CreateImageOptions implements MapBinder {

   @Inject
   private BindToJsonPayload jsonBinder;
   private String name;
   private String visibility;
   private ImmutableList<String> tags;
   private DiskFormat diskFormat;
   private ContainerFormat containerFormat;

   static class CreateImageRequest {
      String name;
      String visibility;
      ImmutableList<String> tags;
      @Named("disk_format")
      DiskFormat diskFormat;
      @Named("container_format")
      ContainerFormat containerFormat;
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object input) {
      return jsonBinder.bindToRequest(request, input);
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {

      CreateImageRequest image = new CreateImageRequest();
      if (null != name) {
         image.name = name;
      }
      if (null != visibility) {
         image.visibility = visibility;
      }
      if (null != tags) {
         image.tags = tags;
      }
      if (null != diskFormat) {
         image.diskFormat = diskFormat;
      }
      if (null != containerFormat) {
         image.containerFormat = containerFormat;
      }

      return bindToRequest(request, image);
   }

   protected ToStringHelper string() {
      ToStringHelper toString = Objects.toStringHelper("").omitNullValues();
      toString.add("name", name);
      toString.add("visibility", visibility);
      toString.add("tags", tags);
      toString.add("diskFormat", diskFormat);
      toString.add("containerFormat", containerFormat);
      return toString;
   }

   @Override
   public String toString() {
      return string().toString();
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (object instanceof CreateImageRequest) {
         final CreateImageRequest other = CreateImageRequest.class.cast(object);
         return equal(name, other.name) && equal(visibility, other.visibility) && equal(tags, other.tags)
               && equal(diskFormat, other.diskFormat);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(name, visibility, tags, diskFormat);
   }

   /**
    * Set name {@value org.jclouds.openstack.glance.v2_0.domain.Image#NAME}
    */
   public CreateImageOptions name(String name) {
      this.name = name;
      return this;
   }

   /**
    * Set visibility
    * {@value org.jclouds.openstack.glance.v2_0.domain.Image#VISIBILITY}
    * 
    */
   public CreateImageOptions visibility(String visibility) {
      this.visibility = visibility;
      return this;
   }

   /**
    * Set tags {@value org.jclouds.openstack.glance.v2_0.domain.Image#TAGS}
    * 
    * @return
    */
   public CreateImageOptions tags(String... tags) {
      this.tags = ImmutableList.copyOf(tags);
      return this;
   }

   /**
    * Set disk format
    * {@value org.jclouds.openstack.glance.v2_0.domain.Image#DISK_FORMAT}
    */
   public CreateImageOptions diskFormat(DiskFormat diskFormat) {
      this.diskFormat = diskFormat;
      return this;
   }

   /**
    * Set container format
    * {@value org.jclouds.openstack.glance.v2_0.domain.Image#CONTAINER_FORMAT}
    */
   public CreateImageOptions containerFormat(ContainerFormat containerFormat) {
      this.containerFormat = containerFormat;
      return this;
   }

   public static class Builder {

      /**
       * @see CreateImageOptions#name(String)
       */
      public static CreateImageOptions name(String name) {
         CreateImageOptions options = new CreateImageOptions();
         return options.name(name);
      }

      /**
       * @see CreateImageOptions#visibility(String)
       */
      public static CreateImageOptions visibility(String visibility) {
         CreateImageOptions options = new CreateImageOptions();
         return options.visibility(visibility);
      }

      /**
       * @see CreateImageOptions#tags(String[])
       */
      public static CreateImageOptions tags(String... tags) {
         CreateImageOptions options = new CreateImageOptions();
         return options.tags(tags);
      }

      /**
       * @see CreateImageOptions#diskFormat(DiskFormat)
       */
      public static CreateImageOptions diskFormat(DiskFormat diskFormat) {
         CreateImageOptions options = new CreateImageOptions();
         return options.diskFormat(diskFormat);
      }

      /**
       * @see CreateImageOptions#containerFormat(ContainerFormat)
       */
      public static CreateImageOptions containerFormat(ContainerFormat containerFormat) {
         CreateImageOptions options = new CreateImageOptions();
         return options.containerFormat(containerFormat);
      }
   }
}
