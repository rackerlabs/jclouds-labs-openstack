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

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.json.SerializedNames;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;

/**
 * A Neutron network
 *
 * @see <a
 *      href="http://docs.openstack.org/api/openstack-network/2.0/content/Networks.html">api
 *      doc</a>
 */
@AutoValue
public abstract class UpdateNetwork {

   // Package-private constructor
   UpdateNetwork() {}

   @Nullable public abstract ImmutableSet<String> getSubnets();
   @Nullable public abstract String getName();
   @Nullable public abstract Boolean getAdminStateUp();
   /**
    * The shared attribute can be used to create a public network, i.e.: a network which is shared with all other tenants.
    * Control of the shared attribute could be reserved to particular users only, such as administrators.
    * In this case, regular users trying to create a shared network will receive a 403 - Forbidden error.
    * @return true if the network resource can be accessed by any tenant or not, false if not
    */
   @Nullable public abstract Boolean getShared();
   @Nullable public abstract String getTenantId();

   // providernet.py: Provider Networks Extension
   @Nullable public abstract NetworkType getNetworkType();
   @Nullable public abstract String getPhysicalNetworkName();
   @Nullable public abstract Integer getSegmentationId();

   // external_net.py: Configurable external gateway modes extension
   @Nullable public abstract Boolean getExternal();

   // portsecurity.py: VMWare port security
   @Nullable public abstract Boolean getPortSecurity();

   // n1kv.py: Cisco plugin extension; admin rights might be needed
   // UUID
   @Nullable public abstract String getProfileId();
   @Nullable public abstract String getMulticastIp();
   @Nullable public abstract String getSegmentAdd();
   @Nullable public abstract String getSegmentDel();
   @Nullable public abstract String getMemberSegments();
   // multiprovidernet.py: Multiprovider net extension; Segments and provider
   // values cannot both be set.
   @Nullable public abstract ImmutableSet<NetworkSegment> getSegments();

   // flavor.py: Flavor support for network and router
   @Nullable public abstract String getNetworkFlavor();

   @SerializedNames({"subnets", "name", "admin_state_up", "shared", "tenant_id",
         "provider:network_type", "provider:physical_network", "provider:segmentation_id", "router:external",
         "port_security_enabled", "n1kv:profile_id", "n1kv:multicast_ip", "n1kv:segment_add", "n1kv:segment_del",
         "n1kv:member_segments", "segments", "flavor:network"})
   private static UpdateNetwork create(ImmutableSet<String> subnets, String name, Boolean adminStateUp,
         Boolean shared, String tenantId, NetworkType networkType, String physicalNetworkName, Integer segmentationId,
         Boolean external, Boolean portSecurity, String profileId, String multicastIp, String segmentAdd,
         String segmentDel, String memberSegments, ImmutableSet<NetworkSegment> segments, String networkFlavor) {
      return builder()
            .subnets(subnets)
            .name(name)
            .adminStateUp(adminStateUp)
            .shared(shared)
            .tenantId(tenantId)
            .networkType(networkType)
            .physicalNetworkName(physicalNetworkName)
            .segmentationId(segmentationId)
            .external(external)
            .portSecurity(portSecurity)
            .profileId(profileId)
            .multicastIp(multicastIp)
            .segmentAdd(segmentAdd)
            .segmentDel(segmentDel)
            .memberSegments(memberSegments)
            .segments(segments)
            .networkFlavor(networkFlavor)
            .build();
   }

   public static Builder builder() {
      return new AutoValue_UpdateNetwork.Builder();
   }

   @AutoValue.Builder
   public abstract static class Builder {
      /**
       * Provides a set of the associated subnets.
       *
       * @param subnets
       * @return the Builder.
       */
      public abstract Builder subnets(ImmutableSet<String> subnets);

      /**
       * Provide the name to the Network's Builder.
       *
       * @return the Builder.
       * @see UpdateNetwork#getName()
       */
      public abstract Builder name(String name);

      /**
       * Provide the adminStateUp to the Network's Builder.
       *
       * @return the Builder.
       * @see UpdateNetwork#getAdminStateUp()
       */
      public abstract Builder adminStateUp(Boolean adminStateUp);

      /**
       * Provide the shared to the Network's Builder.
       *
       * @return the Builder.
       * @see UpdateNetwork#getShared()
       */
      public abstract Builder shared(Boolean shared);

      /**
       * Provide the tenantId to the Network's Builder.
       *
       * @return the Builder.
       * @see UpdateNetwork#getTenantId()
       */
      public abstract Builder tenantId(String tenantId);

      /**
       * Provide the networkType to the Network's Builder.
       *
       * @return the Builder.
       * @see UpdateNetwork#getNetworkType()
       */
      public abstract Builder networkType(NetworkType networkType);

      /**
       * Provide the physicalNetworkName to the Network's Builder.
       *
       * @return the Builder.
       * @see UpdateNetwork#getPhysicalNetworkName()
       */
      public abstract Builder physicalNetworkName(String physicalNetworkName);

      /**
       * Provide the segmentationId to the Network's Builder.
       *
       * @return the Builder.
       * @see UpdateNetwork#getSegmentationId()
       */
      public abstract Builder segmentationId(Integer segmentationId);

      /**
       * Adds external network attribute to network resource.
       *
       * @return the Builder.
       * @see UpdateNetwork#getExternal()
       */
      public abstract Builder external(Boolean external);

      /**
       * Provide the portSecurity to the Network's Builder.
       *
       * @return the Builder.
       * @see UpdateNetwork#getPortSecurity()
       */
      public abstract Builder portSecurity(Boolean portSecurity);

      /**
       * Provide the profileId to the Network's Builder.
       *
       * @return the Builder.
       * @see UpdateNetwork#getProfileId()
       */
      public abstract Builder profileId(String profileId);

      /**
       * Provide the multicastIp to the Network's Builder.
       *
       * @return the Builder.
       * @see UpdateNetwork#getMulticastIp()
       */
      public abstract Builder multicastIp(String multicastIp);

      /**
       * Provide the segmentAdd to the Network's Builder.
       * Cisco plugin extension; admin right might be needed to use this.
       *
       * @return the Builder.
       * @see UpdateNetwork#getSegmentAdd()
       */
      public abstract Builder segmentAdd(String segmentAdd);

      /**
       * Provide the segmentDel to the Network's Builder.
       * Cisco plugin extension; admin right might be needed to use this.
       *
       * @return the Builder.
       * @see UpdateNetwork#getSegmentDel()
       */
      public abstract Builder segmentDel(String segmentDel);

      /**
       * Provide the memberSegments to the Network's Builder.
       * Cisco plugin extension; admin right might be needed to use this.
       *
       * @return the Builder.
       * @see UpdateNetwork#getMemberSegments()
       */
      public abstract Builder memberSegments(String memberSegments);

      /**
       * Provide the segments to the Network's Builder.
       * Multiprovider extension.
       *
       * @return the Builder.
       * @see UpdateNetwork#getSegments()
       */
      public abstract Builder segments(ImmutableSet<NetworkSegment> segments);

      /**
       * Provide the networkFlavor to the Network's Builder.
       *
       * @return the Builder.
       * @see UpdateNetwork#getNetworkFlavor()
       */
      public abstract Builder networkFlavor(String networkFlavor);

      abstract ImmutableSet<NetworkSegment> getSegments();
      abstract ImmutableSet<String> getSubnets();

      abstract UpdateNetwork autoBuild();

      public UpdateNetwork build() {
         segments(getSegments() != null ? ImmutableSet.copyOf(getSegments()) : null);
         subnets(getSubnets() != null ? ImmutableSet.copyOf(getSubnets()) : null);
         return autoBuild();
      }
   }
}
