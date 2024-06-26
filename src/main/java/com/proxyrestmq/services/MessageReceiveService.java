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

import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class MessageReceiveService {

	private final JmsTemplate jmsTemplate;

	@Value("${ibm.mq.recvqueue}")
	private String queue;

	public MessageReceiveService(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
		this.jmsTemplate.setReceiveTimeout(5000L);
	}

	public String recv() {
		try {
			Object msgRaw = jmsTemplate.receiveAndConvert(queue);
			return msgRaw.toString();
		} catch (JmsException ex) {
			ex.printStackTrace();
			return "{ \"message\" : \"Error on receiving the message\" }";
		}
	}
}
