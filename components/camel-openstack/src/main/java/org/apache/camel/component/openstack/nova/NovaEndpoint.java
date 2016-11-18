/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.openstack.nova;

import org.apache.camel.Producer;
import org.apache.camel.component.openstack.common.AbstractOpenstackEndpoint;
import org.apache.camel.component.openstack.nova.producer.FlavorsProducer;
import org.apache.camel.component.openstack.nova.producer.KeypairProducer;
import org.apache.camel.component.openstack.nova.producer.ServerProducer;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;

@UriEndpoint(scheme = "openstack-nova", title = "OpenStack-Nova", syntax = "openstack-nova:host", label = "cloud, virtualization")
public class NovaEndpoint extends AbstractOpenstackEndpoint {

	@UriPath
	@Metadata(required = "true")
	private String host;

	@UriParam (enums = "flavors, servers, keypairs")
	@Metadata(required = "true")
	String subsystem;

	@UriParam(defaultValue = "default")
	private String domain = "default";

	@UriParam
	@Metadata(required = "true")
	private String project;

	@UriParam
	private String operation;

	@UriParam
	@Metadata(required = "true")
	private String username;

	@UriParam
	@Metadata(required = "true")
	private String password;

	public NovaEndpoint(String uri, NovaComponent component) {
		super(uri, component);
	}

	@Override
	public Producer createProducer() throws Exception {
		switch (getSubsystem()) {
			case NovaConstants.NOVA_SUBSYSTEM_FLAVORS:
				return new FlavorsProducer(this, createClient());
			case NovaConstants.NOVA_SUBSYSTEM_SERVERS:
				return new ServerProducer(this, createClient());
			case NovaConstants.NOVA_SUBSYSTEM_KEYPAIRS:
				return new KeypairProducer(this, createClient());
			default:
				throw new IllegalArgumentException("Can't create producer with subsystem " + subsystem);
		}
	}

	public String getSubsystem() {
		return subsystem;
	}

	/**
	 * OpenStack Nova subsystem
	 */
	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	@Override
	public String getDomain() {
		return domain;
	}

	/**
	 * Authentication domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String getProject() {
		return project;
	}

	/**
	 * The project ID
	 */
	public void setProject(String project) {
		this.project = project;
	}

	@Override
	public String getOperation() {
		return operation;
	}

	/**
	 * The operation to do
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * OpenStack username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * OpenStack password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getHost() {
		return host;
	}

	/**
	 * OpenStack host url
	 */
	public void setHost(String host) {
		this.host = host;
	}
}