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
package org.jclouds.openstack.poppy.v1.options;

import java.util.List;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.json.SerializedNames;
import org.jclouds.openstack.poppy.v1.domain.Caching;
import org.jclouds.openstack.poppy.v1.domain.Domain;
import org.jclouds.openstack.poppy.v1.domain.Origin;
import org.jclouds.openstack.poppy.v1.domain.Restriction;

import com.google.auto.value.AutoValue;

/**
 * Representation of creation options for an OpenStack Poppy Service.
 */
@AutoValue
public abstract class ServiceCreateOptions {

   public static Builder builder() {
      return new AutoValue_ServiceCreateOptions.Builder().caching(null).restrictions(null);
   }

   @AutoValue.Builder
   public interface Builder {
      Builder name(String name); //required
      Builder domains(List<Domain> domains); // required
      Builder origins(List<Origin> origins); // required
      Builder caching(List<Caching> caching); // optional
      Builder restrictions(List<Restriction> restrictions); // optional
      Builder flavorId(String flavorId); // required
      ServiceCreateOptions build();
   }

   public abstract String getName();
   @Nullable public abstract List<Domain> getDomains();
   @Nullable public abstract List<Origin> getOrigins();
   @Nullable public abstract List<Caching> getCaching();
   @Nullable public abstract List<Restriction> getRestrictions();
   public abstract String getFlavorId();

   @SerializedNames({ "name", "domains", "origins", "caching", "restrictions", "flavor_id" })
   static ServiceCreateOptions create(String name, List<Domain> domains, List<Origin> origins, List<Caching> caching,
         List<Restriction> restrictions, String flavorId) {
      return new AutoValue_ServiceCreateOptions(name, domains, origins, caching, restrictions, flavorId);
   }

   ServiceCreateOptions() {
   }

}
