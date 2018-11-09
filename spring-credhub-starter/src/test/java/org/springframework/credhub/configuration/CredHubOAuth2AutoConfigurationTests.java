/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.credhub.configuration;

import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.credhub.autoconfig.CredHubAutoConfiguration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class CredHubOAuth2AutoConfigurationTests {

	private ApplicationContextRunner context = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(
					CredHubAutoConfiguration.class,
					ReactiveOAuth2ClientAutoConfiguration.class))
			.withPropertyValues(
					"spring.security.oauth2.client.registration.credhub-client.provider=uaa",
					"spring.security.oauth2.client.registration.credhub-client.client-id=test-client",
					"spring.security.oauth2.client.registration.credhub-client.client-secret=test-secret",
					"spring.security.oauth2.client.registration.credhub-client.authorization-grant-type=client_credentials",
					"spring.security.oauth2.client.provider.uaa.token-uri=http://example.com/uaa/oauth/token",
					"debug=true"
			);

	@Test
	public void oauth2ContextConfigured() {
		context.run((context) -> {
					assertThat(context).hasSingleBean(ReactiveClientRegistrationRepository.class);
					assertThat(context).hasSingleBean(ServerOAuth2AuthorizedClientRepository.class);
				});
	}
}
