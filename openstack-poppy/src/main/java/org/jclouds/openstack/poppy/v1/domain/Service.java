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
import java.util.Set;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.json.SerializedNames;
import org.jclouds.openstack.poppy.v1.options.ServiceCreateOptions;
import org.jclouds.openstack.v2_0.domain.Link;

import com.google.auto.value.AutoValue;
import com.google.common.base.CaseFormat;

/**
 * Representation of an OpenStack Poppy Service.
 */
@AutoValue
public abstract class Service {

   public enum Status {
      CREATE_IN_PROGRESS,
      DEPLOYED,
      UPDATE_IN_PROGRESS,
      DELETE_IN_PROGRESS,
      FAILED;

      public String value() {
         return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, name());
      }

      public static Status fromValue(String status) {
         return valueOf(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, status));
      }

      @Override
      public String toString() {
         return name().toLowerCase();
      }
   }

   @Nullable abstract String getId();
   @Nullable abstract String getName();
   @Nullable abstract List<Domain> getDomains();
   @Nullable abstract List<Origin> getOrigins();
   @Nullable abstract List<Caching> getCaching();
   @Nullable abstract List<Restriction> getRestrictions();
   @Nullable abstract String getFlavorId();
   @Nullable abstract Status getStatus();
   @Nullable abstract List<Error> getErrors();
   @Nullable abstract Set<Link> getLinks();

   @SerializedNames({ "id", "name", "domains", "origins", "caching", "restrictions", "flavor_id",
      "status", "errors", "links" })
   static Service create(String id, String name, List<Domain> domains,
         List<Origin> origins, List<Caching> caching, List<Restriction> restrictions,
         String flavorId, Status status, List<Error> errors, Set<Link> links) {
      return new AutoValue_Service(id, name, domains, origins, caching, restrictions,
            flavorId, status, errors, links);
   }

   static Service create(ServiceCreateOptions options) {
      return new AutoValue_Service(null, options.getName(), options.getDomains(), options.getOrigins(),
            options.getCaching(), options.getRestrictions(), options.getFlavorId(), null, null, null);
   }

   Service() {
   }

}
