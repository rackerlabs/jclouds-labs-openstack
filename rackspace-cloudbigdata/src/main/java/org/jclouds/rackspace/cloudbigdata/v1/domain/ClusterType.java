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
 * Cloud Big Data Cluster Type.
 * Contains information about a Cloud Big Data Cluster Type.
 * Contains a descriptive name of the cluster as well as an ID that can be used when creating a cluster.
 * //@see ClusterTypeApi#get
 * @author Zack Shoylev
 */
public class ClusterType {
   private final String id;
   private final String name;
   private final ImmutableList<Service> services;
   private final ImmutableList<Link> links;

   @ConstructorProperties({
      "id", "name", "services", "links" 
   })
   protected ClusterType(String id, String name, ImmutableList<Service> services, ImmutableList<Link> links) {
      this.id = checkNotNull(id, "id must not be null");
      this.name = checkNotNull(name, "name must not be null");
      this.services = services;
      this.links = checkNotNull(links, "links must not be null");
   }

   /**
    * @return the id for this Cluster Type. Use when creating a new cluster.
    */
   public String getId() {
      return id;
   }   

   /**
    * @return the name of this Cluster Type.
    */
   public String getName() {
      return name;
   }

   /**
    * @return the services for this cluster type.
    */
   public ImmutableList<Service> getServices() {
      return services;
   }

   /**
    * @return the links for this node.
    */
   public ImmutableList<Link> getLinks() {
      return links;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(super.hashCode(), id, name, services, links);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      ClusterType that = ClusterType.class.cast(obj);
      return Objects.equal(this.id, that.id) &&
            Objects.equal(this.name, that.name) &&
            Objects.equal(this.services, that.services) &&
            Objects.equal(this.links, that.links);
   }

   protected ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("id", id)
            .add("name", name)
            .add("services", services)
            .add("links", links);
   }

   @Override
   public String toString() {
      return string().toString();
   }
}
