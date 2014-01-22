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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.jclouds.http.HttpRequest;
import org.jclouds.openstack.glance.v2_0.domain.Image;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.annotations.Unwrap;
import org.jclouds.rest.binders.BindToJsonPayload;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * Update options
 * 
 * @author Ignacio Mulas
 * @see <a href="http://docs.openstack.org/api/openstack-image-service/2.0/content/update-an-image.html"/>
 */
public class UpdateImageOptions implements MapBinder {

   @Inject
   private BindToJsonPayload jsonBinder;   
   private List<UpdateImageField<String>> property;

   public enum Operation {
      ADD, REPLACE, REMOVE
   }

   static class UpdateImageField<T> {
      @Named("op")
      String operation;
      String path;
      T value;

      UpdateImageField(Operation operation, String path, T value) {
         this.operation = operation.toString().toLowerCase();
         this.path = path;
         this.value = value;
      }
   }

   static class UpdateImageRequest {
      List<UpdateImageField<String>> property;
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object input) {
      return jsonBinder.bindToRequest(request, input);
   }

   @Unwrap
   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {

      UpdateImageRequest image = new UpdateImageRequest();
    
      if (null != property) {
         image.property = property;
      }

      return bindToRequest(request, image.property);
   }

   protected ToStringHelper string() {
      ToStringHelper toString = Objects.toStringHelper("").omitNullValues();
      toString.add("update-properties", property);
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
      if (object instanceof UpdateImageRequest) {
         final UpdateImageRequest other = UpdateImageRequest.class.cast(object);
         return equal(property, other.property);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(property);
   }

   /**
    * Set name {@value org.jclouds.openstack.glance.v2_0.domain.Image#NAME}
    */
   public UpdateImageOptions name(Operation operation, String name) {
      if (property == null) {
         this.property = new ArrayList<UpdateImageField<String>>();
      }
      this.property.add(new UpdateImageField<String>(operation, "/" + Image.NAME, name));
      return this;
   }

   /**
    * Set visibility
    * {@value org.jclouds.openstack.glance.v2_0.domain.Image#VISIBILITY}
    * 
    */
   public UpdateImageOptions visibility(Operation operation, String visibility) {
      if (property == null) {
         this.property = new ArrayList<UpdateImageField<String>>();
      }
      this.property.add(new UpdateImageField<String>(operation, "/" + Image.VISIBILITY, visibility));
      return this;
   }

   /**
    * Set fields to update
    * 
    * @return
    */
   public UpdateImageOptions property(Operation operation, String path, String value) {
      if (property == null) {
         this.property = new ArrayList<UpdateImageField<String>>();
      }
      this.property.add(new UpdateImageField<String>(operation, "/" + path, value));
      return this;
   }

   public static class Builder {

      /**
       * @see CreateImageOptions#name(Operation, String)
       */
      public static UpdateImageOptions name(Operation operation, String name) {
         return new UpdateImageOptions().name(operation, name);
      }
      
      /**
       * @see CreateImageOptions#name(Operation, String)
       */
      public static UpdateImageOptions property(Operation operation, String path, String value) {
         return new UpdateImageOptions().property(operation, path, value);
      }

      /**
       * @see CreateImageOptions#visibility(Operation, String)
       */
      public static UpdateImageOptions visibility(Operation operation, String visibility) {         
         return new UpdateImageOptions().visibility(operation, visibility);
      }    

   }
}
