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

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.json.SerializedNames;

import com.google.auto.value.AutoValue;
import com.google.common.base.CaseFormat;

/**
 * Representation of an OpenStack Poppy Domain.
 */
@AutoValue
public abstract class Domain {

   public enum Protocol {
      HTTP,
      HTTPS;

      public String value() {
         return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, name());
      }

      public static Protocol fromValue(String status) {
         return valueOf(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, status));
      }

      @Override
      public String toString() {
         return name().toLowerCase();
      }
   }

   abstract String getDomain();
   @Nullable abstract Protocol getProtocol();

   @SerializedNames({ "domain", "protocol" })
   static Domain create(String domain, Protocol protocol) {
      return new AutoValue_Domain(domain, protocol);
   }

   Domain() {
   }

   public static Builder builder() {
      return new AutoValue_Domain.Builder().protocol(null);
   }

   @AutoValue.Builder
   public interface Builder {
      Builder domain(String name); //required
      Builder protocol(Protocol protocol); // optional
      Domain build();
   }
}
