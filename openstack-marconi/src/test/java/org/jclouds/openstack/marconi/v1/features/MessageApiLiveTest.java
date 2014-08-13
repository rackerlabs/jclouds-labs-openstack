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
package org.jclouds.openstack.marconi.v1.features;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import org.jclouds.openstack.marconi.v1.domain.CreateMessage;
import org.jclouds.openstack.marconi.v1.domain.Message;
import org.jclouds.openstack.marconi.v1.domain.MessageStream;
import org.jclouds.openstack.marconi.v1.domain.MessagesCreated;
import org.jclouds.openstack.marconi.v1.internal.BaseMarconiApiLiveTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Iterables.getOnlyElement;
import static org.jclouds.openstack.marconi.v1.options.StreamMessagesOptions.Builder.echo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@Test(groups = "live", testName = "MessageApiLiveTest", singleThreaded = true)
public class MessageApiLiveTest extends BaseMarconiApiLiveTest {
   private static final UUID CLIENT_ID = UUID.fromString("3381af92-2b9e-11e3-b191-71861300734c");

   private final Map<String, List<String>> messageIds = Maps.newHashMap();

   public void createQueues() throws Exception {
      for (String regionId : regions) {
         QueueApi queueApi = api.getQueueApi(regionId, CLIENT_ID);
         boolean success = queueApi.create("jclouds-test");

         assertTrue(success);
      }
   }

   @Test(dependsOnMethods = { "createQueues" })
   public void streamZeroPagesOfMessages() throws Exception {
      for (String regionId : regions) {
         MessageApi messageApi = api.getMessageApi(regionId, CLIENT_ID, "jclouds-test");

         MessageStream messageStream = messageApi.stream(echo(true));

         assertTrue(Iterables.isEmpty(messageStream));
         assertFalse(messageStream.nextMarker().isPresent());
      }
   }

   @Test(dependsOnMethods = { "streamZeroPagesOfMessages" })
   public void createMessage() throws Exception {
      for (String regionId : regions) {
         MessageApi messageApi = api.getMessageApi(regionId, CLIENT_ID, "jclouds-test");

         String json1 = "{\"event\":{\"name\":\"Edmonton Java User Group\",\"attendees\":[\"bob\",\"jim\",\"sally\"]}}";
         CreateMessage message1 = CreateMessage.builder().ttl(120).body(json1).build();
         List<CreateMessage> message = ImmutableList.of(message1);

         MessagesCreated messagesCreated = messageApi.create(message);

         assertNotNull(messagesCreated);
         assertEquals(messagesCreated.getMessageIds().size(), 1);
      }
   }

   @Test(dependsOnMethods = { "createMessage" })
   public void streamOnePageOfMessages() throws Exception {
      for (String regionId : regions) {
         MessageApi messageApi = api.getMessageApi(regionId, CLIENT_ID, "jclouds-test");

         MessageStream messageStream = messageApi.stream(echo(true));

         while (messageStream.nextMarker().isPresent()) {
            assertEquals(Iterables.size(messageStream), 1);

            messageStream = messageApi.stream(messageStream.nextStreamOptions());
         }

         assertFalse(messageStream.nextMarker().isPresent());
      }
   }

   @Test(dependsOnMethods = { "streamOnePageOfMessages" })
   public void createMessages() throws Exception {
      for (String regionId : regions) {
         MessageApi messageApi = api.getMessageApi(regionId, CLIENT_ID, "jclouds-test");

         String json1 = "{\"event\":{\"name\":\"Austin Java User Group\",\"attendees\":[\"bob\",\"jim\",\"sally\"]}}";
         CreateMessage message1 = CreateMessage.builder().ttl(120).body(json1).build();
         String json2 = "{\"event\":{\"name\":\"SF Java User Group\",\"attendees\":[\"bob\",\"jim\",\"sally\"]}}";
         CreateMessage message2 = CreateMessage.builder().ttl(120).body(json2).build();
         String json3 = "{\"event\":{\"name\":\"HK Java User Group\",\"attendees\":[\"bob\",\"jim\",\"sally\"]}}";
         CreateMessage message3 = CreateMessage.builder().ttl(120).body(json3).build();
         List<CreateMessage> messages = ImmutableList.of(message1, message2, message3);

         MessagesCreated messagesCreated = messageApi.create(messages);

         assertNotNull(messagesCreated);
         assertEquals(messagesCreated.getMessageIds().size(), 3);
      }
   }

   @Test(dependsOnMethods = { "createMessages" })
   public void streamManyPagesOfMessages() throws Exception {
      for (String regionId : regions) {
         MessageApi messageApi = api.getMessageApi(regionId, CLIENT_ID, "jclouds-test");
         messageIds.put(regionId, new ArrayList<String>());

         MessageStream messageStream = messageApi.stream(echo(true).limit(2));

         while (messageStream.nextMarker().isPresent()) {
            assertEquals(Iterables.size(messageStream), 2);

            for (Message message : messageStream) {
               messageIds.get(regionId).add(message.getId());
            }

            messageStream = messageApi.stream(messageStream.nextStreamOptions());
         }

         assertFalse(messageStream.nextMarker().isPresent());
      }
   }

   @Test(dependsOnMethods = { "streamManyPagesOfMessages" })
   public void listMessagesByIds() throws Exception {
      for (String regionId : regions) {
         MessageApi messageApi = api.getMessageApi(regionId, CLIENT_ID, "jclouds-test");

         List<Message> messages = messageApi.list(messageIds.get(regionId));

         assertEquals(messages.size(), 4);

         for (Message message : messages) {
            assertNotNull(message.getId());
            assertNotNull(message.getBody());
         }
      }
   }

   @Test(dependsOnMethods = { "listMessagesByIds" })
   public void getMessage() throws Exception {
      for (String regionId : regions) {
         MessageApi messageApi = api.getMessageApi(regionId, CLIENT_ID, "jclouds-test");

         Message message = messageApi.get(getLast(messageIds.get(regionId)));

         assertNotNull(message.getId());
         assertNotNull(message.getBody());
      }
   }

   @Test(dependsOnMethods = { "getMessage" })
   public void deleteMessagesByClaimId() throws Exception {
      for (String regionId : regions) {
         UUID clientId = UUID.fromString("3381af92-2b9e-11e3-b191-71861300734c");
         MessageApi messageApi = api.getMessageApi(regionId, CLIENT_ID, "jclouds-test");
         ClaimApi claimApi = api.getClaimApi(regionId, clientId, "jclouds-test");
         Message message = getOnlyElement(claimApi.claim(300, 100, 1));

         boolean success = messageApi.deleteByClaim(message.getId(), message.getClaimId().get());

         assertTrue(success);
      }
   }

   @Test(dependsOnMethods = { "deleteMessagesByClaimId" })
   public void deleteMessages() throws Exception {
      for (String regionId : regions) {
         MessageApi messageApi = api.getMessageApi(regionId, CLIENT_ID, "jclouds-test");

         boolean success = messageApi.delete(messageIds.get(regionId));

         assertTrue(success);
      }
   }

   @Test(dependsOnMethods = { "deleteMessages" })
   public void delete() throws Exception {
      for (String regionId : regions) {
         QueueApi queueApi = api.getQueueApi(regionId, CLIENT_ID);
         boolean success = queueApi.delete("jclouds-test");

         assertTrue(success);
      }
   }
}
