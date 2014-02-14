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
package org.jclouds.rackspace.cloudbigdata.v1.domain;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import java.beans.ConstructorProperties;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.gson.annotations.SerializedName;

/**
 * Cloud Big Data Node Address.
 * Used to provide information about a a node address (such as a private or public IP address).
 * @author Zack Shoylev
 */
public class Address implements Comparable<Address> {
   @SerializedName("addr")
   private String address;
   private int version;

   @ConstructorProperties({
      "address", "version"
   })
   protected Address(String address, int version) {
      this.address = checkNotNull(address, "address required");
      checkArgument(version==4 || (version==6), "IP address version should be valid - 4 or 6");
      this.version = version;
   }

   /**
    * @return An IP address for a cluster node.
    */
   public String getAddress() {
      return this.address;
   }

   /**
    * @return IP address version (4 or 6).
    */
   public int getVersion() {
      return this.version;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(address, version);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Address that = Address.class.cast(obj);
      return Objects.equal(this.address, that.address) && 
            Objects.equal(this.version, that.version);
   }

   protected ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("address", address)
            .add("version", version);
   }

   @Override
   public String toString() {
      return string().toString();
   }   

   @Override
   public int compareTo(Address that) {
      return this.getAddress().compareTo(that.getAddress());
   }
}
