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
package org.jclouds.rackspace.autoscale.v1.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jclouds.openstack.v2_0.domain.Link;
import org.jclouds.rackspace.autoscale.v1.domain.Group;
import org.jclouds.rackspace.autoscale.v1.domain.GroupConfiguration;
import org.jclouds.rackspace.autoscale.v1.domain.LaunchConfiguration;
import org.jclouds.rackspace.autoscale.v1.domain.LaunchConfiguration.LaunchConfigurationType;
import org.jclouds.rackspace.autoscale.v1.domain.LoadBalancer;
import org.jclouds.rackspace.autoscale.v1.domain.Personality;
import org.jclouds.rackspace.autoscale.v1.domain.CreateScalingPolicy;
import org.jclouds.rackspace.autoscale.v1.domain.CreateScalingPolicy.ScalingPolicyScheduleType;
import org.jclouds.rackspace.autoscale.v1.domain.CreateScalingPolicy.ScalingPolicyTargetType;
import org.jclouds.rackspace.autoscale.v1.domain.CreateScalingPolicy.ScalingPolicyType;
import org.jclouds.rackspace.autoscale.v1.domain.ScalingPolicy;
import org.jclouds.rackspace.autoscale.v1.internal.BaseAutoscaleApiLiveTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.Uninterruptibles;

/**
 * Scaling Policy live test
 */
@Test(groups = "live", testName = "ScalingPolicyApiLiveTest", singleThreaded = true)
public class ScalingPolicyApiLiveTest extends BaseAutoscaleApiLiveTest {

   private static Map<String, List<Group>> created = Maps.newHashMap();

   @Override
   @BeforeClass(groups = { "integration", "live" })
   public void setup() {
      super.setup();
      for (String region : api.getConfiguredRegions()) {
         List<Group> createdGroupList = Lists.newArrayList();
         created.put(region, createdGroupList);
         GroupApi groupApi = api.getGroupApi(region);

         GroupConfiguration groupConfiguration = GroupConfiguration.builder().maxEntities(10).cooldown(3)
               .name("testscalinggroup198547").minEntities(0)
               .metadata(ImmutableMap.of("gc_meta_key_2", "gc_meta_value_2", "gc_meta_key_1", "gc_meta_value_1"))
               .build();

         LaunchConfiguration launchConfiguration = LaunchConfiguration
               .builder()
               .loadBalancers(ImmutableList.of(LoadBalancer.builder().port(8080).id(9099).build()))
               .serverName("autoscale_server")
               .serverImageRef("5cc098a5-7286-4b96-b3a2-49f4c4f82537")
               .serverFlavorRef("2")
               .serverDiskConfig("AUTO")
               .serverMetadata(
                     ImmutableMap
                     .of("build_config", "core", "meta_key_1", "meta_value_1", "meta_key_2", "meta_value_2"))
                     .networks(
                           ImmutableList.of("11111111-1111-1111-1111-111111111111", "00000000-0000-0000-0000-000000000000"))
                           .personalities(
                                 ImmutableList.of(Personality.builder().path("testfile")
                                       .contents("VGhpcyBpcyBhIHRlc3QgZmlsZS4=").build()))
                                       .type(LaunchConfigurationType.LAUNCH_SERVER).build();

         List<CreateScalingPolicy> scalingPolicies = Lists.newArrayList();

         CreateScalingPolicy scalingPolicy = CreateScalingPolicy.builder().cooldown(3).type(ScalingPolicyType.WEBHOOK)
               .name("scale up by 1").targetType(ScalingPolicyTargetType.INCREMENTAL).target("1").build();
         scalingPolicies.add(scalingPolicy);

         Group g = groupApi.create(groupConfiguration, launchConfiguration, scalingPolicies);
         createdGroupList.add(g);

         assertNotNull(g);
         assertNotNull(g.getId());
         assertEquals(g.getLinks().size(), 1);
         assertEquals(g.getLinks().get(0).getHref().toString(),
               "https://" + region.toLowerCase() + ".autoscale.api.rackspacecloud.com/v1.0/" + api.getCurrentTenantId().get().getId() + "/groups/" + g.getId() + "/");
         assertEquals(g.getLinks().get(0).getRelation(), Link.Relation.SELF);

         assertNotNull(g.getScalingPolicies().get(0).getId());
         assertEquals(g.getScalingPolicies().get(0).getLinks().size(), 1);
         assertEquals(
               g.getScalingPolicies().get(0).getLinks().get(0).getHref().toString(),
               "https://" + region.toLowerCase() + ".autoscale.api.rackspacecloud.com/v1.0/" + api.getCurrentTenantId().get().getId() + "/groups/" + g.getId() + "/policies/" + g.getScalingPolicies().get(0).getId() + "/");
         assertEquals(g.getScalingPolicies().get(0).getLinks().get(0).getRelation(), Link.Relation.SELF);
         assertEquals(g.getScalingPolicies().get(0).getCooldown(), 3);
         assertEquals(g.getScalingPolicies().get(0).getTarget(), "1");
         assertEquals(g.getScalingPolicies().get(0).getTargetType(), ScalingPolicyTargetType.INCREMENTAL);
         assertEquals(g.getScalingPolicies().get(0).getType(), ScalingPolicyType.WEBHOOK);
         assertEquals(g.getScalingPolicies().get(0).getName(), "scale up by 1");

         assertEquals(g.getLaunchConfiguration().getLoadBalancers().size(), 1);
         assertEquals(g.getLaunchConfiguration().getLoadBalancers().get(0).getId(), 9099);
         assertEquals(g.getLaunchConfiguration().getLoadBalancers().get(0).getPort(), 8080);
         assertEquals(g.getLaunchConfiguration().getServerName(), "autoscale_server");
         assertNotNull(g.getLaunchConfiguration().getServerImageRef());
         assertEquals(g.getLaunchConfiguration().getServerFlavorRef(), "2");
         assertEquals(g.getLaunchConfiguration().getServerDiskConfig(), "AUTO");
         assertEquals(g.getLaunchConfiguration().getPersonalities().size(), 1);
         assertEquals(g.getLaunchConfiguration().getPersonalities().get(0).getPath(), "testfile");
         assertEquals(g.getLaunchConfiguration().getPersonalities().get(0).getContents(),
               "VGhpcyBpcyBhIHRlc3QgZmlsZS4=");
         assertEquals(g.getLaunchConfiguration().getNetworks().size(), 2);
         assertEquals(g.getLaunchConfiguration().getNetworks().get(0), "11111111-1111-1111-1111-111111111111");
         assertEquals(g.getLaunchConfiguration().getNetworks().get(1), "00000000-0000-0000-0000-000000000000");
         assertEquals(g.getLaunchConfiguration().getServerMetadata().size(), 3);
         assertTrue(g.getLaunchConfiguration().getServerMetadata().containsKey("build_config"));
         assertTrue(g.getLaunchConfiguration().getServerMetadata().containsValue("core"));
         assertEquals(g.getLaunchConfiguration().getType(), LaunchConfigurationType.LAUNCH_SERVER);

         assertEquals(g.getGroupConfiguration().getMaxEntities(), 10);
         assertEquals(g.getGroupConfiguration().getCooldown(), 3);
         assertEquals(g.getGroupConfiguration().getName(), "testscalinggroup198547");
         assertEquals(g.getGroupConfiguration().getMinEntities(), 0);
         assertEquals(g.getGroupConfiguration().getMetadata().size(), 2);
         assertTrue(g.getGroupConfiguration().getMetadata().containsKey("gc_meta_key_2"));
         assertTrue(g.getGroupConfiguration().getMetadata().containsValue("gc_meta_value_2"));
      }
   }

   @Test
   public void testCreatePolicy() {
      for (String region : api.getConfiguredRegions()) {

         PolicyApi policyApi = api.getPolicyApi(region, created.get(region).get(0).getId());

         List<CreateScalingPolicy> scalingPolicies = Lists.newArrayList();

         CreateScalingPolicy scalingPolicy = CreateScalingPolicy.builder()
               .cooldown(3)
               .type(ScalingPolicyType.WEBHOOK)
               .name("scale up by one server")
               .targetType(ScalingPolicyTargetType.INCREMENTAL)
               .target("1")
               .build();
         scalingPolicies.add(scalingPolicy);

         FluentIterable<ScalingPolicy> scalingPolicyResponse = policyApi.create(scalingPolicies);
         assertNotNull(scalingPolicyResponse.iterator().next().getId());
         assertEquals(scalingPolicyResponse.iterator().next().getCooldown(), 3);
         assertEquals(scalingPolicyResponse.iterator().next().getTarget(), "1");
      }
   }

   @Test
   public void testCreateScheduleCronPolicy() {
      for (String region : api.getConfiguredRegions()) {

         PolicyApi policyApi = api.getPolicyApi(region, created.get(region).get(0).getId());

         List<CreateScalingPolicy> scalingPolicies = Lists.newArrayList();

         CreateScalingPolicy scalingPolicy = CreateScalingPolicy.builder()
               .cooldown(3)
               .type(ScalingPolicyType.SCHEDULE)
               .name("scale up by one server")
               .targetType(ScalingPolicyTargetType.INCREMENTAL)
               .target("1")
               .cronSchedule("23 * * * *")
               .build();
         scalingPolicies.add(scalingPolicy);

         FluentIterable<ScalingPolicy> scalingPolicyResponse = policyApi.create(scalingPolicies);
         assertNotNull(scalingPolicyResponse.iterator().next().getId());
         assertEquals(scalingPolicyResponse.iterator().next().getCooldown(), 3);
         assertEquals(scalingPolicyResponse.iterator().next().getTarget(), "1");
         assertEquals(scalingPolicyResponse.iterator().next().getSchedulingType(), ScalingPolicyScheduleType.CRON);
         assertEquals(scalingPolicyResponse.iterator().next().getSchedulingString(), "23 * * * *");
      }
   }

   @Test
   public void testCreateScheduleAtPolicy() {
      for (String region : api.getConfiguredRegions()) {

         PolicyApi policyApi = api.getPolicyApi(region, created.get(region).get(0).getId());

         List<CreateScalingPolicy> scalingPolicies = Lists.newArrayList();

         CreateScalingPolicy scalingPolicy = CreateScalingPolicy.builder()
               .cooldown(3)
               .type(ScalingPolicyType.SCHEDULE)
               .name("scale up by one server")
               .targetType(ScalingPolicyTargetType.INCREMENTAL)
               .target("1")
               .atSchedule("2020-12-05T03:12:00Z")
               .build();
         scalingPolicies.add(scalingPolicy);

         FluentIterable<ScalingPolicy> scalingPolicyResponse = policyApi.create(scalingPolicies);
         assertNotNull(scalingPolicyResponse.iterator().next().getId());
         assertEquals(scalingPolicyResponse.iterator().next().getCooldown(), 3);
         assertEquals(scalingPolicyResponse.iterator().next().getTarget(), "1");
         assertEquals(scalingPolicyResponse.iterator().next().getSchedulingType(), ScalingPolicyScheduleType.AT);
         assertEquals(scalingPolicyResponse.iterator().next().getSchedulingString(), "2020-12-05T03:12:00Z");
      }
   }

   @Test
   public void testListPolicy() {
      for (String region : api.getConfiguredRegions()) {

         PolicyApi policyApi = api.getPolicyApi(region, created.get(region).get(0).getId());

         FluentIterable<ScalingPolicy> scalingPolicyResponse = policyApi.list();
         assertNotNull(scalingPolicyResponse.iterator().next().getId());
      }
   }

   @Test
   public void testGetPolicy() {
      for (String region : api.getConfiguredRegions()) {

         PolicyApi policyApi = api.getPolicyApi(region, created.get(region).get(0).getId());

         assertNotNull(policyApi);
         ScalingPolicy listResponse = policyApi.list().iterator().next();
         ScalingPolicy getResponse = policyApi.get(listResponse.getId());
         assertEquals(listResponse.getId(), getResponse.getId());
         assertEquals(listResponse.getName(), getResponse.getName());
         assertEquals(listResponse.getCooldown(), getResponse.getCooldown());
         assertEquals(listResponse.getLinks(), getResponse.getLinks());
         assertEquals(listResponse.getTarget(), getResponse.getTarget());
         assertEquals(listResponse.getTargetType(), getResponse.getTargetType());
         assertEquals(listResponse.getType(), getResponse.getType());
      }
   }

   @Test
   public void testUpdatePolicy() {
      for (String region : api.getConfiguredRegions()) {

         PolicyApi policyApi = api.getPolicyApi(region, created.get(region).get(0).getId());

         List<CreateScalingPolicy> scalingPolicies = Lists.newArrayList();

         CreateScalingPolicy scalingPolicy = CreateScalingPolicy.builder()
               .cooldown(3)
               .type(ScalingPolicyType.WEBHOOK)
               .name("scale up by one server")
               .targetType(ScalingPolicyTargetType.INCREMENTAL)
               .target("1")
               .build();
         scalingPolicies.add(scalingPolicy);

         CreateScalingPolicy updated = CreateScalingPolicy.builder()
               .cooldown(3)
               .type(ScalingPolicyType.WEBHOOK)
               .name("scale up by 2 PERCENT server")
               .targetType(ScalingPolicyTargetType.PERCENT_CHANGE)
               .target("2")
               .build();


         FluentIterable<ScalingPolicy> scalingPolicyResponse = policyApi.create(scalingPolicies);
         String policyId = scalingPolicyResponse.iterator().next().getId();
         assertNotNull(policyId);

         boolean result = policyApi.update(policyId, updated);
         assertTrue(result);

         ScalingPolicy updatedResponse = policyApi.get(policyId);

         assertNotNull(updatedResponse.getId());
         assertEquals(updatedResponse.getCooldown(), 3);
         assertEquals(updatedResponse.getTarget(), "2");
         assertEquals(updatedResponse.getTargetType(), ScalingPolicyTargetType.PERCENT_CHANGE);
         assertEquals(updatedResponse.getType(), ScalingPolicyType.WEBHOOK);
         assertEquals(updatedResponse.getName(), "scale up by 2 PERCENT server");
      }
   }

   @Test
   public void testDeletePolicy() {
      for (String region : api.getConfiguredRegions()) {

         PolicyApi policyApi = api.getPolicyApi(region, created.get(region).get(0).getId());

         List<CreateScalingPolicy> scalingPolicies = Lists.newArrayList();

         CreateScalingPolicy scalingPolicy = CreateScalingPolicy.builder()
               .cooldown(3)
               .type(ScalingPolicyType.WEBHOOK)
               .name("scale up by one server")
               .targetType(ScalingPolicyTargetType.INCREMENTAL)
               .target("1")
               .build();
         scalingPolicies.add(scalingPolicy);

         FluentIterable<ScalingPolicy> scalingPolicyResponse = policyApi.create(scalingPolicies);
         String policyId = scalingPolicyResponse.iterator().next().getId();
         assertNotNull(policyId);

         boolean result = policyApi.delete(policyId);
         assertTrue(result);
      }
   }

   @Test
   public void testExecutePolicy() {
      for (String region : api.getConfiguredRegions()) {

         PolicyApi policyApi = api.getPolicyApi(region, created.get(region).get(0).getId());

         List<CreateScalingPolicy> scalingPolicies = Lists.newArrayList();

         CreateScalingPolicy scalingPolicy = CreateScalingPolicy.builder()
               .cooldown(3)
               .type(ScalingPolicyType.WEBHOOK)
               .name("scale up by 0 server")
               .targetType(ScalingPolicyTargetType.INCREMENTAL)
               .target("1")
               .build();
         scalingPolicies.add(scalingPolicy);

         FluentIterable<ScalingPolicy> scalingPolicyResponse = policyApi.create(scalingPolicies);
         String policyId = scalingPolicyResponse.iterator().next().getId();
         assertNotNull(policyId);

         boolean result = policyApi.execute(policyId);
         assertTrue(result);
      }
   }

   @Override
   @AfterClass(groups = { "integration", "live" })
   public void tearDown() {
      for (String region : api.getConfiguredRegions()) {
         GroupApi groupApi = api.getGroupApi(region);
         for (Group group : created.get(region)) {
            PolicyApi policyApi = api.getPolicyApi(region, group.getId());
            if (policyApi == null)
                continue;
            for (ScalingPolicy sgr : policyApi.list()) {
               if (!policyApi.delete(sgr.getId())) {
                  System.out.println("Could not delete an autoscale policy after tests!");
               }
            }

            List<CreateScalingPolicy> scalingPolicies = Lists.newArrayList();

            CreateScalingPolicy scalingPolicy = CreateScalingPolicy.builder()
                  .cooldown(3)
                  .type(ScalingPolicyType.WEBHOOK)
                  .name("0 machines")
                  .targetType(ScalingPolicyTargetType.DESIRED_CAPACITY)
                  .target("0")
                  .build();
            scalingPolicies.add(scalingPolicy);

            FluentIterable<ScalingPolicy> scalingPolicyResponse = policyApi.create(scalingPolicies);
            String policyId = scalingPolicyResponse.first().get().getId();

            Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);

            policyApi.execute(policyId);

            Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);

            if (!groupApi.delete(group.getId()))
               System.out.println("Could not delete an autoscale group after tests!");
         }
      }
      super.tearDown();
   }
}
