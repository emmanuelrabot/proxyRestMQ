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

import jakarta.jms.Message;

import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import jakarta.jms.JMSException;

import org.springframework.beans.factory.annotation.Value;

@Service
public class MessageSendService {

	private final JmsTemplate jmsTemplate;

	@Value("${ibm.mq.sendqueue}")
	private String queue;

	public MessageSendService(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public String send(String message) {
		try {
			final String correlationId = UUID.randomUUID().toString();
			jmsTemplate.convertAndSend(queue, message, new CorrelationIdPostProcessor(correlationId));
			return "{ \"message\" : \"Message Sent: " + message + "\" }";
		} catch (JmsException ex) {
			ex.printStackTrace();
			return "{ \"message\" : \"Some errors occured on sending the message: " + message + "\" }";
		}
	}

	private class CorrelationIdPostProcessor implements MessagePostProcessor {
		private final String correlationId;

		public CorrelationIdPostProcessor(final String correlationId) {
			this.correlationId = correlationId;
		}

		@Override
		public Message postProcessMessage(final Message msg)
				throws JMSException {
			msg.setJMSCorrelationID(correlationId);
			return msg;
		}
	}
}
