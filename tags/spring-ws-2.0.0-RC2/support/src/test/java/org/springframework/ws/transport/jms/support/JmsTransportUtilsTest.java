/*
 * Copyright 2005-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ws.transport.jms.support;

import java.net.URI;
import javax.jms.DeliveryMode;
import javax.jms.Message;

import org.springframework.ws.transport.jms.JmsTransportConstants;

import org.junit.Assert;
import org.junit.Test;

public class JmsTransportUtilsTest {

    @Test
    public void testGetDestinationName() throws Exception {
        URI uri = new URI("jms:RequestQueue?replyToName=RESP_QUEUE");
        String destinationName = JmsTransportUtils.getDestinationName(uri);
        Assert.assertEquals("Invalid destination", "RequestQueue", destinationName);

        uri = new URI("jms:RequestQueue");
        destinationName = JmsTransportUtils.getDestinationName(uri);
        Assert.assertEquals("Invalid destination", "RequestQueue", destinationName);
    }

    @Test
    public void testGetDeliveryMode() throws Exception {
        URI uri = new URI("jms:RequestQueue?deliveryMode=NON_PERSISTENT");
        int deliveryMode = JmsTransportUtils.getDeliveryMode(uri);
        Assert.assertEquals("Invalid deliveryMode", DeliveryMode.NON_PERSISTENT, deliveryMode);

        uri = new URI("jms:RequestQueue?deliveryMode=PERSISTENT");
        deliveryMode = JmsTransportUtils.getDeliveryMode(uri);
        Assert.assertEquals("Invalid deliveryMode", DeliveryMode.PERSISTENT, deliveryMode);

        uri = new URI("jms:RequestQueue?replyToName=RESP_QUEUE");
        deliveryMode = JmsTransportUtils.getDeliveryMode(uri);
        Assert.assertEquals("Invalid deliveryMode", Message.DEFAULT_DELIVERY_MODE, deliveryMode);
    }

    @Test
    public void testGetMessageType() throws Exception {
        URI uri = new URI("jms:RequestQueue?messageType=BYTESMESSAGE");
        int messageType = JmsTransportUtils.getMessageType(uri);
        Assert.assertEquals("Invalid messageType", JmsTransportConstants.BYTES_MESSAGE_TYPE, messageType);

        uri = new URI("jms:RequestQueue?messageType=TEXT_MESSAGE");
        messageType = JmsTransportUtils.getMessageType(uri);
        Assert.assertEquals("Invalid messageType", JmsTransportConstants.TEXT_MESSAGE_TYPE, messageType);

        uri = new URI("jms:RequestQueue?replyToName=RESP_QUEUE");
        messageType = JmsTransportUtils.getMessageType(uri);
        Assert.assertEquals("Invalid messageType", JmsTransportConstants.BYTES_MESSAGE_TYPE, messageType);
    }

    @Test
    public void testGetTimeToLive() throws Exception {
        URI uri = new URI("jms:RequestQueue?timeToLive=100");
        long timeToLive = JmsTransportUtils.getTimeToLive(uri);
        Assert.assertEquals("Invalid timeToLive", 100, timeToLive);

        uri = new URI("jms:RequestQueue?replyToName=RESP_QUEUE");
        timeToLive = JmsTransportUtils.getTimeToLive(uri);
        Assert.assertEquals("Invalid timeToLive", Message.DEFAULT_TIME_TO_LIVE, timeToLive);
    }

    @Test
    public void testGetPriority() throws Exception {
        URI uri = new URI("jms:RequestQueue?priority=5");
        int priority = JmsTransportUtils.getPriority(uri);
        Assert.assertEquals("Invalid priority", 5, priority);

        uri = new URI("jms:RequestQueue?replyToName=RESP_QUEUE");
        priority = JmsTransportUtils.getPriority(uri);
        Assert.assertEquals("Invalid priority", Message.DEFAULT_PRIORITY, priority);
    }

    @Test
    public void testGetReplyToName() throws Exception {
        URI uri = new URI("jms:RequestQueue?replyToName=RESP_QUEUE");
        String replyToName = JmsTransportUtils.getReplyToName(uri);
        Assert.assertEquals("Invalid replyToName", "RESP_QUEUE", replyToName);

        uri = new URI("jms:RequestQueue?priority=5");
        replyToName = JmsTransportUtils.getReplyToName(uri);
        Assert.assertNull("Invalid replyToName", replyToName);
    }

    @Test
    public void testJndi() throws Exception {
        URI uri = new URI("jms:jms/REQUEST_QUEUE?replyToName=jms/REPLY_QUEUE");
        String destination = JmsTransportUtils.getDestinationName(uri);
        Assert.assertEquals("Invalid destination name", "jms/REQUEST_QUEUE", destination);

        String replyTo = JmsTransportUtils.getReplyToName(uri);
        Assert.assertEquals("Invalid reply to name", "jms/REPLY_QUEUE", replyTo);
    }
}