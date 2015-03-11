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
package org.jclouds.openstack.poppy.v1.domain;

import java.util.List;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.json.SerializedNames;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

/**
 * Representation of updatable options for an OpenStack Poppy Service.
 */
@AutoValue
public abstract class UpdateService {
   /**
    * @see Builder#name(String)
    */
   public abstract String getName();

   /**
    * @see Builder#domains(List)
    */
   public abstract List<Domain> getDomains();

   /**
    * @see Builder#origins(List)
    */
   public abstract List<Origin> getOrigins();

   /**
    * @see Builder#caching(List)
    */
   @Nullable public abstract List<Caching> getCaching();

   /**
    * @see Builder#restrictions(List)
    */
   @Nullable public abstract List<Restriction> getRestrictions();

   /**
    * @see Builder#flavorId(String)
    */
   public abstract String getFlavorId();

   public static Builder builder() {
      return new AutoValue_UpdateService.Builder().caching(null).restrictions(null);
   }
   public Builder toBuilder() {
      return builder()
            .name(getName())
            .domains(getDomains())
            .origins(getOrigins())
            .caching(getCaching())
            .restrictions(getRestrictions())
            .flavorId(getFlavorId());
   }

   @SerializedNames({ "name", "domains", "origins", "caching", "restrictions", "flavor_id" })
   private static UpdateService create(String name, List<Domain> domains, List<Origin> origins, List<Caching> caching,
         List<Restriction> restrictions, String flavorId) {
      return builder()
            .name(name)
            .domains(domains)
            .origins(origins)
            .caching(caching)
            .restrictions(restrictions)
            .flavorId(flavorId).build();
   }

   public static final class Builder {
      private String name;
      private List<Domain> domains;
      private List<Origin> origins;
      private List<Caching> caching;
      private List<Restriction> restrictions;
      private String flavorId;
      Builder() {
      }
      Builder(UpdateService source) {
         name(source.getName());
         domains(source.getDomains());
         origins(source.getOrigins());
         caching(source.getCaching());
         restrictions(source.getRestrictions());
         flavorId(source.getFlavorId());
      }

      /**
       * Required.
       * @param name Specifies the name of the service. The minimum length for name is 3. The maximum length is 256.
       * @return The UpdateService builder.
       */
      public Builder name(String name) {
         this.name = name;
         return this;
      }

      /**
       * Required.
       * @param domains Specifies a list of domains used by users to access their website.
       * @return The UpdateService builder.
       */
      public Builder domains(List<Domain> domains) {
         this.domains = domains;
         return this;
      }

      /**
       * Required.
       * @param origins Specifies a list of origin domains or IP addresses where the original assets are stored.
       * @return The UpdateService builder.
       */
      public Builder origins(List<Origin> origins) {
         this.origins = origins;
         return this;
      }

      /**
       * Optional.
       * @param caching Specifies the TTL rules for the assets under this service. Supports wildcards for fine-grained control.
       * @return The UpdateService builder.
       */
      public Builder caching(List<Caching> caching) {
         this.caching = caching;
         return this;
      }

      /**
       * Optional.
       * @param restrictions Specifies the restrictions that define who can access assets (content from the CDN cache).
       * @return The UpdateService builder.
       */
      public Builder restrictions(List<Restriction> restrictions) {
         this.restrictions = restrictions;
         return this;
      }

      /**
       * Required.
       * @param flavorId Specifies the CDN provider flavor ID to use. For a list of flavors, see the operation to list
       *                 the available flavors. The minimum length for flavor_id is 3. The maximum length is 256.
       * @return The UpdateService builder.
       */
      public Builder flavorId(String flavorId) {
         this.flavorId = flavorId;
         return this;
      }

      public UpdateService build() {
         String missing = "";
         if (name == null) {
            missing += " name";
         }
         if (domains == null) {
            missing += " domains";
         }
         if (origins == null) {
            missing += " origins";
         }
         if (flavorId == null) {
            missing += " flavorId";
         }
         if (!missing.isEmpty()) {
            throw new IllegalStateException("Missing required properties:" + missing);
         }
         UpdateService result = new AutoValue_UpdateService(
               this.name,
               this.domains,
               this.origins,
               caching != null ? ImmutableList.copyOf(this.caching) : null,
               restrictions != null ? ImmutableList.copyOf(this.restrictions) : null,
               this.flavorId);
         return result;
      }
   }
}
