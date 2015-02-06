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
package org.jclouds.openstack.poppy.v1.features;

import java.net.URI;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.poppy.v1.config.CDN;
import org.jclouds.openstack.poppy.v1.domain.Service;
import org.jclouds.openstack.poppy.v1.functions.ParseServiceURIFromHeaders;
import org.jclouds.openstack.poppy.v1.options.ServiceCreateOptions;
import org.jclouds.rest.annotations.Endpoint;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;

import com.google.common.annotations.Beta;

/**
 * Provides access to OpenStack Poppy Service features.
 */
@Beta
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Endpoint(CDN.class)
@Path("/services")
public interface ServiceApi {

   /**
    * Gets a service.
    *
    * @param id  the id of the {@code Service}
    * @return the {@code Service} for the specified id, otherwise {@code null}
    */
   @Named("service:get")
   @GET
   @Path("/{id}")
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   Service get(@PathParam("id") String id);

   /**
    * Creates a service.
    *
    * @param options  the options to create the service with
    * @return a URI to the created service
    */
   @Named("service:create")
   @POST
   @ResponseParser(ParseServiceURIFromHeaders.class)
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   URI create(ServiceCreateOptions options);
}
