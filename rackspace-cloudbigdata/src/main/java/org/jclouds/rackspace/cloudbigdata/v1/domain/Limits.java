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

import java.beans.ConstructorProperties;

import org.jclouds.openstack.v2_0.domain.Link;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ImmutableList;

/**
 * Cloud Big Data Cluster Node.
 * Contains information about a Cloud Big Data node.
 * //@see NodeApi#get
 * @author Zack Shoylev
 */
public class Limits {
   private final AbsoluteLimits absolute;
   private final ImmutableList<Link> links;

   @ConstructorProperties({
      "absolute", "links" 
   })
   protected Limits(AbsoluteLimits absolute, ImmutableList<Link> links) {
      this.absolute = checkNotNull(absolute, "Absolute limits must not be null");
      this.links = checkNotNull(links, "links must not be null");
   }

   /**
    * @return the disk limit.
    */
   public Limit getDisk() {
      return absolute.disk;
   }
   
   /**
    * @return the node count limit.
    */
   public Limit getNodeCount() {
      return absolute.nodeCount;
   }
   
   /**
    * @return the RAM limit.
    */
   public Limit getRAM() {
      return absolute.ram;
   }
   
   /**
    * @return the virtual CPUs limit.
    */
   public Limit getVCPUs() {
      return absolute.vcpus;
   }
   
   /**
    * @return the links for this node.
    */
   public ImmutableList<Link> getLinks() {
      return links;
   }
   
   @Override
   public int hashCode() {
      return Objects.hashCode(absolute, links);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Limits that = Limits.class.cast(obj);
      return Objects.equal(this.absolute, that.absolute) &&
            Objects.equal(this.links, that.links);
   }

   protected ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("absolute", absolute)
            .add("links", links);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   protected static class AbsoluteLimits {
      public Limit disk;
      public Limit nodeCount;
      public Limit ram;
      public Limit vcpus;
   }
}
