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
 * Cloud Big Data Flavor.
 * Contains information about a Cloud Big Data Flavor.
 * A flavor is a machine hardware type.
 * //@see FlavorApi#get
 * @author Zack Shoylev
 */
public class Flavor {
   private final String id;
   private final String name;
   private final int disk;
   private final int ram;
   private final int vcpus;
   private final ImmutableList<Link> links;

   @ConstructorProperties({
      "id", "name", "disk", "ram", "vcpus", "links" 
   })
   protected Flavor(String id, String name, int disk, int ram, int vcpus, ImmutableList<Link> links) {
      this.id = checkNotNull(id, "id must not be null");
      this.name = checkNotNull(name, "name must not be null");
      this.disk = disk;
      this.ram = ram;
      this.vcpus = vcpus;
      this.links = checkNotNull(links, "links must not be null");
   }

   /**
    * @return the id for this Flavor.
    */
   public String getId() {
      return id;
   }   

   /**
    * @return the name of this Flavor.
    */
   public String getName() {
      return name;
   }

   /**
    * @return the disk size for this Flavor.
    */
   public int getDisk() {
      return disk;
   }

   /**
    * @return the RAM size of this Flavor.
    */
   public int getRAM() {
      return ram;
   }

   /**
    * @return the number of virtual CPUs for this Flavor.
    */
   public int getVCPUs() {
      return vcpus;
   }

   /**
    * @return the links for this node.
    */
   public ImmutableList<Link> getLinks() {
      return links;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(super.hashCode(), id, name, disk, ram, vcpus, links);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Flavor that = Flavor.class.cast(obj);
      return Objects.equal(this.id, that.id) &&
            Objects.equal(this.name, that.name) &&
            Objects.equal(this.disk, that.disk) &&
            Objects.equal(this.ram, that.ram) &&
            Objects.equal(this.vcpus, that.vcpus) &&
            Objects.equal(this.links, that.links);
   }

   protected ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("id", id)
            .add("name", name)
            .add("disk", disk)
            .add("ram", ram)
            .add("vcpus", vcpus)
            .add("links", links);
   }

   @Override
   public String toString() {
      return string().toString();
   }
}
