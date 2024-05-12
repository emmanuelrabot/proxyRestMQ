/**
 * Copyright 2022, 2023 IBM Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the 'License');
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
 **/

package com.proxyrestmq.services;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.logging.Level;

import jakarta.jms.Message;

import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import jakarta.jms.JMSException;

import org.springframework.beans.factory.annotation.Value;

@Service
public class MessageSendService {

	Logger logger = Logger.getLogger(MessageSendService.class.getName());

	private final JmsTemplate jmsTemplate;

	@Value("${ibm.mq.sendqueue}")
	private String sendQueue;

	// @Value("${ibm.mq.recvqueue}")
	@Value("${ibm.mq.sendqueue}")
	private String recvQeue;

	@Value("5000")
	private String recvtimeout;

	public MessageSendService(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
		logger.setLevel(Level.FINE);
		logger.log(Level.CONFIG, "!!!!!!!!!! recvtimeout: " + recvtimeout + "!!!!!!!!!!!!!!!!!");
		// this.jmsTemplate.setReceiveTimeout(Long.parseLong(recvtimeout));
	}

	public String send(String message) {
		try {
			final AtomicReference<Message> jmsMessage = new AtomicReference<>();

			jmsTemplate.convertAndSend(sendQueue, message, messagePostProcessor -> {
				jmsMessage.set(messagePostProcessor);
				return messagePostProcessor;
			});

			String messageId = jmsMessage.get().getJMSMessageID();

			Object msgRaw = jmsTemplate.receiveSelectedAndConvert(recvQeue, "JMSCorrelationID = '" + messageId + "'");
			return msgRaw.toString();

		} catch (JmsException ex) {
			ex.printStackTrace();
			return "{ \"message\" : \"Some errors occured on sending the message: " + message + "\" }";
		} catch (JMSException ex) {
			ex.printStackTrace();
			return "{ \"message\" : \"Some errors occured on sending the message: " + message + "\" }";
		}
	}
}
