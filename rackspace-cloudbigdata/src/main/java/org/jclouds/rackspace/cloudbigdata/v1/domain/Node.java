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
import java.util.Date;

import org.jclouds.openstack.v2_0.domain.Link;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.SerializedName;

/**
 * Cloud Big Data Cluster Node.
 * Contains information about a Cloud Big Data node.
 * //@see NodeApi#get
 * @author Zack Shoylev
 */
public class Node {
   private final String id;
   private final Date created;
   private final String role;
   private final String name;
   private final ScriptStatus postInitScriptStatus;
   private final Status status;
   private final Addresses addresses;
   private final ImmutableList<Service> services;
   private final ImmutableList<Link> links;

   @ConstructorProperties({
      "id", "created", "role", "name", "postInitScriptStatus", "status", "addresses", "services", "links" 
   })
   protected Node(String id, Date created, String role, String name, ScriptStatus postInitScriptStatus,
         Addresses addresses, Status status, ImmutableList<Service> services, ImmutableList<Link> links) {
      this.id = checkNotNull(id, "id must not be null");
      this.created = checkNotNull(created, "created must not be null");
      this.role = checkNotNull(role, "role must not be null");
      this.name = checkNotNull(name, "name must not be null");
      this.postInitScriptStatus = postInitScriptStatus;
      this.status = checkNotNull(status, "status must not be null");
      this.addresses = checkNotNull(addresses, "addresses must not be null");
      this.services = services;
      this.links = checkNotNull(links, "links must not be null");
   }

   /**
    * @return the id for this node
    */
   public String getId() {
      return id;
   }

   /**
    * @return the timestamp when this node was created.
    */
   public Date getCreated() {
      return created;
   }

   /**
    * @return the id for this node
    */
   public String getRole() {
      return role;
   }

   /**
    * @return the name of this node
    */
   public String getName() {
      return name;
   }

   /**
    * @return The status of the execution of the init script.
    */
   public ScriptStatus getPostInitScriptStatus() {
      return postInitScriptStatus;
   }

   /**
    * @return the current status.
    */
   public Status getStatus() {
      return status;
   }

   /**
    * @return returns list of public addresses
    */
   public ImmutableList<Address> getPublicAddresses() {
      return addresses.publicAddresses;
   }

   /**
    * @return returns list of public addresses
    */
   public ImmutableList<Address> getPrivateAddresses() {
      return addresses.privateAddresses;
   }

   /**
    * @return the services running on this node.
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
      return Objects.hashCode(id, created, role, name, postInitScriptStatus, status, services, links);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      Node that = Node.class.cast(obj);
      return Objects.equal(this.id, that.id) &&
            Objects.equal(this.created, that.created) &&
            Objects.equal(this.role, that.role) &&
            Objects.equal(this.name, that.name) &&
            Objects.equal(this.postInitScriptStatus, that.postInitScriptStatus) &&
            Objects.equal(this.status, that.status) &&
            Objects.equal(this.services, that.services) &&
            Objects.equal(this.links, that.links);
   }

   protected ToStringHelper string() {
      return Objects.toStringHelper(this)
            .add("id", id)
            .add("created", created)
            .add("role", role)
            .add("name", name)
            .add("postInitScriptStatus", postInitScriptStatus)
            .add("status", status)
            .add("services", services)
            .add("links", links);
   }

   @Override
   public String toString() {
      return string().toString();
   }

   /**
    * Lists possible Node status.
    */
   public static enum Status {
      /**
       * The node is in the process of being created. Servers are being created and booted.
       * This is similar to the cluster status.
       */
      BUILDING,
      /**
       * The node is still in the process of being created. All servers are booted and are now being provisioned.
       */
      CONFIGURING,      
      /**
       * The node is operational. All nodes are provisioned and ready for use.
       */
      ACTIVE,
      /**
       * node is changing configuration (resizing, etc) at customers request.
       */
      UPDATING,
      /**
       * Customer has requested an operation that failed but node is still operational. e.g. resize up but can't acquire more nodes
       */
      IMPAIRED,
      /**
       * Customer has requested the node be deleted but the operation hasn't yet completed.
       */
      DELETING,
      /**
       * The node is deleted.
       */
      DELETED,
      /**
       * A fatal error has occurred during node provisioning.
       */
      ERROR,
      /**
       * Unrecognized status response.
       */
      UNRECOGNIZED;

      @Override
      public String toString() {
         return name();
      }

      /**
       * @param status The string representation of a Status
       * @return The corresponding Status.
       */
      public static Status fromValue(String status) {
         try {
            return valueOf(checkNotNull(status, "status"));
         } catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

   /**
    * Lists possible node script status.
    * This is similar to the cluster script status.
    * This is the current status of the init script.
    */
   public static enum ScriptStatus {
      /**
       * The init script has failed.
       */
      FAILED,
      /**
       * The init script has not started executing.
       */
      PENDING,
      /**
       * The init script has been uploaded.
       */
      DELIVERED,
      /**
       * The init script is executing.
       */
      RUNNING,
      /**
       * The init script has finished successfully.
       */
      SUCCEEDED,
      /**
       * Unrecognized status response.
       */
      UNRECOGNIZED;

      @Override
      public String toString() {
         return name();
      }

      /**
       * @param scriptStatus The string representation of a ScriptStatus
       * @return The corresponding ScriptStatus.
       */
      public static ScriptStatus fromValue(String scriptStatus) {
         try {
            return valueOf(checkNotNull(scriptStatus, "scriptStatus"));
         } catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

   protected static class Addresses {
      @SerializedName("public")
      public ImmutableList<Address> publicAddresses;
      @SerializedName("private")
      public ImmutableList<Address> privateAddresses;
   }
}
