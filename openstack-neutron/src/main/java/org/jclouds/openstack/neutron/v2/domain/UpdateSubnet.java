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
public abstract class UpdateSubnet {

   /**
    * Default constructor.
    */
   UpdateSubnet() {}

   @Nullable public abstract String getName();
   @Nullable public abstract String getNetworkId();
   @Nullable public abstract String getTenantId();
   @Nullable public abstract String getGatewayIp();
   @Nullable public abstract Integer getIpVersion();
   @Nullable public abstract String getCidr();
   @Nullable public abstract Boolean getEnableDhcp();
   @Nullable public abstract ImmutableSet<String> getDnsNameServers();
   @Nullable public abstract ImmutableSet<HostRoute> getHostRoutes();
   @Nullable public abstract IPv6DHCPMode getIpv6RaMode();
   @Nullable public abstract IPv6DHCPMode getIpv6AddressMode();

   @SerializedNames({"name", "network_id", "tenant_id", "gateway_ip", "ip_version",
         "cidr", "enable_dhcp", "dns_nameservers", "host_routes", "ipv6_address_mode", "ipv6_ra_mode"})
   private UpdateSubnet create(String name, String networkId, String tenantId,
         String gatewayIp, Integer ipVersion, String cidr, Boolean enableDhcp, ImmutableSet<String> dnsNameServers,
         ImmutableSet<HostRoute> hostRoutes,
         IPv6DHCPMode ipv6AddressMode, IPv6DHCPMode ipv6RaMode) {
      return UpdateSubnet.builder()
            .name(name)
            .networkId(networkId)
            .tenantId(tenantId)
            .gatewayIp(gatewayIp)
            .ipVersion(ipVersion)
            .cidr(cidr)
            .enableDhcp(enableDhcp)
            .dnsNameServers(dnsNameServers)
            .hostRoutes(hostRoutes)
            .ipv6AddressMode(ipv6AddressMode)
            .ipv6RaMode(ipv6RaMode)
            .build();
   }

   public static Builder builder() {
      return new AutoValue_UpdateSubnet.Builder();
   }

   @AutoValue.Builder
   public abstract static class Builder {
      /**
       * @see Subnet#getName()
       */
      public abstract Builder name(String name);

      /**
       * @see Subnet#getNetworkId()
       */
      public abstract Builder networkId(String networkId);

      /**
       * Only administrators can specify a tenant ID that is not their own.
       * As it is optional, this is usually omitted in requests.
       * @see Subnet#getTenantId()
       */
      public abstract Builder tenantId(String tenantId);

      /**
       * @see Subnet#getGatewayIp()
       */
      public abstract Builder gatewayIp(String gatewayIp);

      /**
       * @see Subnet#getIpVersion()
       */
      public abstract Builder ipVersion(Integer ipVersion);

      /**
       * @see Subnet#getCidr()
       */
      public abstract Builder cidr(String cidr);

      /**
       * @see Subnet#getEnableDhcp()
       */
      public abstract Builder enableDhcp(Boolean enableDhcp);

      /**
       * @see Subnet#getDnsNameServers()
       */
      public abstract Builder dnsNameServers(ImmutableSet<String> dnsNameServers);

      /**
       * @see Subnet#getHostRoutes()
       */
      public abstract Builder hostRoutes(ImmutableSet<HostRoute> hostRoutes);

      /**
       * @see Subnet#getIPv6RAMode()
       */
      public abstract Builder ipv6RaMode(IPv6DHCPMode ipv6RaMode);

      /**
       * @see Subnet#getIPv6AddressMode()
       */
      public abstract Builder ipv6AddressMode(IPv6DHCPMode ipv6AddressMode);

      abstract ImmutableSet<String> getDnsNameServers();
      abstract ImmutableSet<HostRoute> getHostRoutes();

      abstract UpdateSubnet autoBuild();

      public UpdateSubnet build() {
         dnsNameServers(getDnsNameServers() != null ? ImmutableSet.copyOf(getDnsNameServers()) : null);
         hostRoutes(getHostRoutes() != null ? ImmutableSet.copyOf(getHostRoutes()) : null);
         return autoBuild();
      }
   }
}
