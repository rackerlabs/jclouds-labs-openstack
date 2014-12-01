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
package org.jclouds.rackspace.autoscale.v1;

import static org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties.CREDENTIAL_TYPE;
import static org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties.SERVICE_TYPE;

import java.net.URI;
import java.util.Properties;

import org.jclouds.apis.ApiMetadata;
import org.jclouds.openstack.keystone.v2_0.config.CredentialTypes;
import org.jclouds.openstack.keystone.v2_0.config.KeystoneAuthenticationModule.RegionModule;
import org.jclouds.rackspace.autoscale.v1.config.AutoscaleHttpApiModule;
import org.jclouds.rackspace.autoscale.v1.config.AutoscaleParserModule;
import org.jclouds.rackspace.cloudidentity.v2_0.ServiceType;
import org.jclouds.rackspace.cloudidentity.v2_0.config.CloudIdentityAuthenticationApiModule;
import org.jclouds.rackspace.cloudidentity.v2_0.config.CloudIdentityAuthenticationModule;
import org.jclouds.rest.internal.BaseHttpApiMetadata;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

/**
 * Implementation of {@link org.jclouds.apis.ApiMetadata} for the Rackspace Auto Scale API.
 *
 * @see AutoscaleApi
 */
@AutoService(ApiMetadata.class)
public class AutoscaleApiMetadata extends BaseHttpApiMetadata<AutoscaleApi> {

   @Override
   public Builder toBuilder() {
      return new Builder().fromApiMetadata(this);
   }

   public AutoscaleApiMetadata() {
      this(new Builder());
   }

   protected AutoscaleApiMetadata(Builder builder) {
      super(builder);
   }

   public static Properties defaultProperties() {
      Properties properties = BaseHttpApiMetadata.defaultProperties();
      properties.setProperty(SERVICE_TYPE, ServiceType.AUTO_SCALE);
      properties.setProperty(CREDENTIAL_TYPE, CredentialTypes.PASSWORD_CREDENTIALS);
      return properties;
   }

   public static class Builder extends BaseHttpApiMetadata.Builder<AutoscaleApi, Builder> {

      protected Builder() {
         id("rackspace-autoscale")
         .name("Rackspace Autoscale API")
         .identityName("${tenantName}:${userName} or ${userName}, if your keystone supports a default tenant")
         .credentialName("${password}")
         .endpointName("Keystone base URL ending in /v2.0/")
         .documentation(URI.create("http://api.openstack.org/"))
         .version("1.0")
         .defaultEndpoint("http://localhost:5000/v2.0/")
         .defaultProperties(AutoscaleApiMetadata.defaultProperties())
         .defaultModules(ImmutableSet.<Class<? extends Module>>builder()
               .add(CloudIdentityAuthenticationApiModule.class)
               .add(CloudIdentityAuthenticationModule.class)
               .add(RegionModule.class)
               .add(AutoscaleParserModule.class)
               .add(AutoscaleHttpApiModule.class)
               .build());
      }

      @Override
      public AutoscaleApiMetadata build() {
         return new AutoscaleApiMetadata(this);
      }

      @Override
      protected Builder self() {
         return this;
      }
   }
}
