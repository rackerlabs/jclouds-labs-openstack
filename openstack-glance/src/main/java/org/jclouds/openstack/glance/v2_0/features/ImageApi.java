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
package org.jclouds.openstack.glance.v2_0.features;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks.EmptyPagedIterableOnNotFoundOr404;
import org.jclouds.Fallbacks.FalseOnNotFoundOr404;
import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.collect.PagedIterable;
import org.jclouds.io.Payload;
import org.jclouds.openstack.glance.v2_0.domain.Image;
import org.jclouds.openstack.glance.v2_0.functions.internal.ParseImages;
import org.jclouds.openstack.glance.v2_0.options.CreateImageOptions;
import org.jclouds.openstack.glance.v2_0.options.ListImageOptions;
import org.jclouds.openstack.glance.v2_0.options.UpdateImageOptions;
import org.jclouds.openstack.keystone.v2_0.KeystoneFallbacks.EmptyPaginatedCollectionOnNotFoundOr404;
import org.jclouds.openstack.v2_0.domain.PaginatedCollection;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.MapBinder;
import org.jclouds.rest.annotations.PATCH;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.Transform;
import org.jclouds.rest.annotations.Unwrap;

/**
 * Image Services
 * 
 * @author Ignacio Mulas
 * 
 * @see <a href="http://glance.openstack.org/glanceapi.html">api doc</a>
 * @see <a
 *      href="https://github.com/openstack/glance/blob/master/glance/api/v2/images.py">api
 *      src</a>
 */
@RequestFilters(AuthenticateRequest.class)
@Path("/v2/images")
public interface ImageApi {

   /**
    * List all images (all details)
    * 
    * @return all images (all details)
    */
   @GET
   @Consumes(APPLICATION_JSON)
   @ResponseParser(ParseImages.class)
   @Transform(ParseImages.ToPagedIterable.class)
   @Fallback(EmptyPagedIterableOnNotFoundOr404.class)
   PagedIterable<? extends Image> list();

   /**
    * List all images according to the options provided
    * 
    * @param options
    * @return
    */
   @GET
   @Consumes(APPLICATION_JSON)
   @RequestFilters(AuthenticateRequest.class)
   @ResponseParser(ParseImages.class)
   @Fallback(EmptyPaginatedCollectionOnNotFoundOr404.class)
   PaginatedCollection<? extends Image> list(ListImageOptions options);

   /**
    * Return metadata about an image with id
    */
   @GET
   @Path("/{id}")
   @Consumes(MediaType.APPLICATION_JSON)
   @Fallback(NullOnNotFoundOr404.class)
   Image get(@PathParam("id") String id);

   /**
    * Create a new image
    * 
    * @return detailed metadata about the newly stored image
    */
   @POST
   @Produces(APPLICATION_JSON)
   @Consumes(APPLICATION_JSON)
   @MapBinder(CreateImageOptions.class)
   Image create(CreateImageOptions options);

   /**
    * Upload image data
    * <p/>
    * An image record must exist before a client can store binary image data
    * with it.
    * 
    * @param imageData
    *           the new image to upload
    */
   @PUT
   @Path("/{id}/file")
   @Produces(APPLICATION_OCTET_STREAM)
   @Consumes(APPLICATION_JSON)
   void upload(@PathParam("id") String id, Payload imageData);

   /**
    * Return image data for image with id
    */
   @GET
   @Path("/{id}/file")
   @Fallback(NullOnNotFoundOr404.class)
   InputStream download(@PathParam("id") String id);

   /**
    * Adjust the metadata stored for an existing image
    * 
    * @return detailed metadata about the updated image
    */
   @PATCH
   @Path("/{id}")
   @Consumes(APPLICATION_JSON)
   @Produces("application/openstack-images-v2.0-json-patch")
   @MapBinder(UpdateImageOptions.class)
   Image update(@PathParam("id") String id, UpdateImageOptions options);

   /**
    * Delete the image with the specified id
    * 
    * @return true if successful
    */
   @DELETE
   @Path("/{id}")
   @Fallback(FalseOnNotFoundOr404.class)
   boolean delete(@PathParam("id") String id);
}
