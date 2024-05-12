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

package com.proxyrestmq.controllers;

import com.proxyrestmq.services.MessageSendService;
import com.proxyrestmq.services.MessageReceiveService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.proxyrestmq.services.Response;
import com.proxyrestmq.services.Request;

@RestController
public class Controller {

	private final MessageSendService messageSendService;
	private final MessageReceiveService messageReceiveService;

	public Controller(MessageSendService messageSendService, MessageReceiveService messageReceiveService) {
		this.messageSendService = messageSendService;
		this.messageReceiveService = messageReceiveService;
	}

	@PostMapping("send")
	public String postController(@RequestBody Request request) {
		return messageSendService.send(request.getMsg());
	}
}
