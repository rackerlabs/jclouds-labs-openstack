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

import java.io.Closeable;
import java.util.List;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks.FalseOnNotFoundOr404;
import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.rackspace.autoscale.v1.binders.BindCreateGroupToJson;
import org.jclouds.rackspace.autoscale.v1.binders.BindLaunchConfigurationToJson;
import org.jclouds.rackspace.autoscale.v1.binders.BindToGroupConfigurationRequestPayload;
import org.jclouds.rackspace.autoscale.v1.domain.CreateScalingPolicy;
import org.jclouds.rackspace.autoscale.v1.domain.Group;
import org.jclouds.rackspace.autoscale.v1.domain.GroupConfiguration;
import org.jclouds.rackspace.autoscale.v1.domain.GroupState;
import org.jclouds.rackspace.autoscale.v1.domain.LaunchConfiguration;
import org.jclouds.rackspace.autoscale.v1.functions.ParseGroupLaunchConfigurationResponse;
import org.jclouds.rackspace.autoscale.v1.functions.ParseGroupResponse;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.MapBinder;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.SelectJson;

import com.google.common.collect.FluentIterable;

/**
 * The API for controlling scaling groups.
 * A scaling group is a high-level autoscaling concept that encompasses a group configuration, a launch configuration, and a set of scaling policies.
 */
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/groups")
public interface GroupApi extends Closeable {

   /**
    * Create a scaling group.
    * @param groupConfiguration The group configuration.
    * @param launchConfiguration The launch configuration.
    * @param scalingPolicies The list of scaling policies.
    * @return Group The group created by this call.
    * @see GroupConfiguration
    * @see LaunchConfiguration
    * @see CreateScalingPolicy
    * @see Group
    */
   @Named("group:create")
   @POST
   @MapBinder(BindCreateGroupToJson.class)
   @ResponseParser(ParseGroupResponse.class)
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   Group create(@PayloadParam("groupConfiguration") GroupConfiguration groupConfiguration,
         @PayloadParam("launchConfiguration") LaunchConfiguration launchConfiguration,
         @PayloadParam("scalingPolicies") List<CreateScalingPolicy> scalingPolicies);

   /**
    * This operation pauses the specified Autoscaling Group
    *
    * @param groupId The id for the specified Group.
    * @return true if successful.
    * @see GroupApi#resume(String)
    */
   @Named("group:pause")
   @POST
   @Path("/{groupId}/pause")
   @Fallback(FalseOnNotFoundOr404.class)
   boolean pause(@PathParam("groupId") String groupId);

   /**
    * This operation resumes the specified Autoscaling Group.
    *
    * @param groupId The id for the specified Group.
    * @return true if successful.
    * @see GroupApi#pause(String)
    */
   @Named("group:resume")
   @POST
   @Path("/{groupId}/resume")
   @Fallback(FalseOnNotFoundOr404.class)
   boolean resume(@PathParam("groupId") String groupId);

   /**
    * This operation deletes the specified Autoscaling Group
    *
    * @param groupId The id for the specified Group.
    * @return true if successful.
    */
   @Named("group:delete")
   @DELETE
   @Path("/{id}")
   @Fallback(FalseOnNotFoundOr404.class)
   boolean delete(@PathParam("id") String groupId);

   /**
    * This operation gets group details for a group
    * @param id The unique identifier of the scaling group.
    * @return Group Full details for the scaling group.
    */
   @Named("group:get")
   @GET
   @Path("/{id}")
   @ResponseParser(ParseGroupResponse.class)
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   Group get(@PathParam("id") String id);

   /**
    * This operation gets the state of the Autoscaling Group. This is a slightly different set of information than the full details for a group.
    * @param id The unique identifier of the scaling group.
    * @return The state of the Group.
    * @see GroupState
    */
   @Named("group:getState")
   @GET
   @Path("/{id}/state")
   @SelectJson("group")
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   GroupState getState(@PathParam("id") String id);

   /**
    * This operation lists all autoscaling groups.
    * @return A list of group states for all scaling groups.
    * @see GroupState
    */
   @Named("group:listGroupStates")
   @GET
   @SelectJson("groups")
   FluentIterable<GroupState> listGroupStates();

   /**
    * This operation gets the configuration for the scaling group.
    * @return The group configuration for the scaling group.
    * @see GroupConfiguration
    */
   @Named("group:getGroupConfiguration")
   @GET
   @Path("/{groupId}/config")
   @SelectJson("groupConfiguration")
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   GroupConfiguration getGroupConfiguration(@PathParam("groupId") String id);

   /**
    * This operation updates the configuration for the scaling group.
    * @return true if successful.
    * @see GroupConfiguration
    */
   @Named("group:updateGroupConfiguration")
   @PUT
   @Path("/{groupId}/config")
   @Fallback(FalseOnNotFoundOr404.class)
   @MapBinder(BindToGroupConfigurationRequestPayload.class)
   boolean updateGroupConfiguration(@PathParam("groupId") String id,
         @PayloadParam("groupConfiguration") GroupConfiguration groupConfiguration);

   /**
    * This operation gets the launch configuration for the scaling group.
    * @return The launch configuration for the scaling group.
    * @see LaunchConfiguration
    */
   @Named("group:getLaunchConfiguration")
   @GET
   @Path("/{groupId}/launch")
   @ResponseParser(ParseGroupLaunchConfigurationResponse.class)
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   LaunchConfiguration getLaunchConfiguration(@PathParam("groupId") String id);

   /**
    * This operation updates the launch configuration for the scaling group.
    * @return true if successful.
    * @see LaunchConfiguration
    */
   @Named("group:updateLaunchConfiguration")
   @PUT
   @Path("/{groupId}/launch")
   @Fallback(FalseOnNotFoundOr404.class)
   @MapBinder(BindLaunchConfigurationToJson.class)
   boolean updateLaunchConfiguration(@PathParam("groupId") String id,
         @PayloadParam("launchConfiguration") LaunchConfiguration launchConfiguration);
}
