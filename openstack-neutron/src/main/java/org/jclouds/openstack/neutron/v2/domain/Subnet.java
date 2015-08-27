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
 * A Neutron subnet
 *
 * @see <a href="http://docs.openstack.org/api/openstack-network/2.0/content/Subnets.html">api doc</a>
 */
@AutoValue
public abstract class Subnet {
   /**
    * Default constructor.
    */
   Subnet() {}

   /**
    * @return the id of the subnet
    */
   @Nullable public abstract String getId();

   /**
    * @return the name of the subnet
    */
   @Nullable public abstract String getName();

   /**
    * @return the id of the network this subnet is associated with.
    */
   @Nullable public abstract String getNetworkId();

   /**
    * @return the id of the tenant where this entity is associated with.
    */
   @Nullable public abstract String getTenantId();

   /**
    * Cannot be used for updates.
    * @return the sub-ranges of CIDR available for dynamic allocation to ports.
    */
   @Nullable public abstract ImmutableSet<AllocationPool> getAllocationPools();

   /**
    * @return the default gateway used by devices in this subnet.
    */
   @Nullable public abstract String getGatewayIp();

   /**
    * @return the IP version used by this subnet.
    */
   @Nullable public abstract Integer getIpVersion();

   /**
    * @return the CIDR representing the IP range for this subnet, based on IP version.
    */
   @Nullable public abstract String getCidr();

   /**
    * @return true if DHCP is enabled for this subnet, false if not.
    */
   @Nullable public abstract Boolean getEnableDhcp();

   /**
    * @return Configurable maximum amount of name servers per subnet. The default is 5.
    */
   @Nullable public abstract ImmutableSet<String> getDnsNameServers();

   /**
    * @return Configurable maximum amount of routes per subnet. The default is 20.
    */
   @Nullable public abstract ImmutableSet<HostRoute> getHostRoutes();

   /**
    * @return The IP v6 Address Mode.
    */
   @Nullable public abstract IPv6DHCPMode getIPv6AddressMode();

   /**
    * @return The IP v6 Router Advertisement mode.
    */
   @Nullable public abstract IPv6DHCPMode getIPv6RAMode();

   @SerializedNames({"id", "name", "network_id", "tenant_id", "allocation_pools", "gateway_ip", "ip_version",
         "cidr", "enable_dhcp", "dns_nameservers", "host_routes", "ipv6_address_mode", "ipv6_ra_mode"})
   private static Subnet create(String id, String name, String networkId, String tenantId, ImmutableSet<AllocationPool> allocationPools,
         String gatewayIp, Integer ipVersion, String cidr, Boolean enableDhcp, ImmutableSet<String> dnsNameServers, ImmutableSet<HostRoute> hostRoutes,
         IPv6DHCPMode ipv6AddressMode, IPv6DHCPMode ipv6RaMode) {
      return new AutoValue_Subnet(
      id,
      name,
      networkId,
      tenantId,
      allocationPools,
      gatewayIp,
      ipVersion,
      cidr,
      enableDhcp,
      dnsNameServers,
      hostRoutes,
      ipv6AddressMode,
      ipv6RaMode
      );
   }
}
