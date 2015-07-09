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
public abstract class Network {

   @Nullable public abstract String getId();
   @Nullable public abstract NetworkStatus getStatus();
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

   @SerializedNames({"id", "status", "subnets", "name", "admin_state_up", "shared", "tenant_id",
         "provider:network_type", "provider:physical_network", "provider:segmentation_id", "router:external",
         "port_security_enabled", "n1kv:profile_id", "n1kv:multicast_ip", "n1kv:segment_add", "n1kv:segment_del",
         "n1kv:member_segments", "segments", "flavor:network"})
   private static Network create(String id, NetworkStatus status, ImmutableSet<String> subnets, String name, Boolean adminStateUp,
         Boolean shared, String tenantId, NetworkType networkType, String physicalNetworkName, Integer segmentationId,
         Boolean external, Boolean portSecurity, String profileId, String multicastIp, String segmentAdd,
         String segmentDel, String memberSegments, ImmutableSet<NetworkSegment> segments, String networkFlavor) {
      return new AutoValue_Network(
            id,
            status,
            subnets,
            name,
            adminStateUp,
            shared,
            tenantId,
            networkType,
            physicalNetworkName,
            segmentationId,
            external,
            portSecurity,
            profileId,
            multicastIp,
            segmentAdd,
            segmentDel,
            memberSegments,
            segments,
            networkFlavor);
   }
}
