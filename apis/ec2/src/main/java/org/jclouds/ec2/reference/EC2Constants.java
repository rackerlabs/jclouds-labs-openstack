/**
 *
 * Copyright (C) 2010 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.jclouds.ec2.reference;

import org.jclouds.compute.ComputeService;

/**
 * Configuration properties and constants used in EC2 connections.
 * 
 * @author Adrian Cole
 */
public interface EC2Constants{
   /**
    * Listing the universe of amis is extremely expensive. set this to a comma separated value of
    * the ami owners you wish to use in {@link ComputeService}
    */
   public static final String PROPERTY_EC2_AMI_OWNERS = "jclouds.ec2.ami-owners";
   public static final String PROPERTY_ELB_ENDPOINT = "jclouds.elb.endpoint";

   /**
    * amis that work on the cluster instance type
    * 
    * @see InstanceType.CC1_4XLARGE
    */
   public static final String PROPERTY_EC2_CC_AMIs = "jclouds.ec2.cc-amis";

}