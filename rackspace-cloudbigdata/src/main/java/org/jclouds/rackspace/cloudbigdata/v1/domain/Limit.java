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

import java.beans.ConstructorProperties;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * Cloud Big Data Limit.
 * Contains information about a Cloud Big Data Limits.
 * //@see LimitsApi#get
 * @author Zack Shoylev
 */
public class Limit {
   private final int limit;
   private final int remaining;

   @ConstructorProperties({
      "limit", "remaining" 
   })
   protected Limit(int limit, int remaining) {
      this.limit = limit;
      this.remaining = remaining;
   }

   /**
    * @return the total quantity for this limit.
    */
   public int getLimit() {
      return limit;
   }   

   /**
    * @return the remaining quantity for this limit.
    */
   public int getRemaining() {
      return remaining;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(limit, remaining);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Limit that = Limit.class.cast(obj);
      return Objects.equal(this.limit, that.limit) &&
            Objects.equal(this.remaining, that.remaining);
   }

   protected ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("limit", limit)
            .add("remaining", remaining);
   }

   @Override
   public String toString() {
      return string().toString();
   }
}
