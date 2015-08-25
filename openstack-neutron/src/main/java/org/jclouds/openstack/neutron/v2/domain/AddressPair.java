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
package org.jclouds.openstack.neutron.v2.domain;

import org.jclouds.json.SerializedNames;
import com.google.auto.value.AutoValue;

/**
 * Contains a mapping between a MAC address and an IP address.
 */
@AutoValue
public abstract class AddressPair  {

   // Package-private constructor
   AddressPair() {}

   /**
    * @return the macAddress of the AddressPair
    */
   public abstract String getMacAddress();
   /**
    * @return the ipAddress of the AddressPair
    */
   public abstract String getIpAddress();

   /**
    * Gets a Builder configured as this object.
    */
   public Builder toBuilder() {
      return builder().macAddress(getMacAddress()).ipAddress(getIpAddress());
   }

   @SerializedNames({"mac_address", "ip_address"})
   private static AddressPair create(String macAddress, String ipAddress) {
      return builder().macAddress(macAddress).ipAddress(ipAddress).build();
   }

   public static Builder builder() {
      return new AutoValue_AddressPair.Builder();
   }

   @AutoValue.Builder
   public abstract static class Builder {
      /**
       * Provide the macAddress to the AddressPair's Builder.
       *
       * @return the Builder.
       * @see AddressPair#getMacAddress()
       */
      public abstract Builder macAddress(String macAddress);

      /**
       * Provide the ipAddress to the AddressPair's Builder.
       *
       * @return the Builder.
       * @see AddressPair#getIpAddress()
       */
      public abstract Builder ipAddress(String ipAddress);
      public abstract String getMacAddress();
      public abstract String getIpAddress();
      public abstract AddressPair build();
   }
}
