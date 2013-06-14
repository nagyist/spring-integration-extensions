package org.springframework.integration.reactor;

import org.springframework.integration.core.SubscribableChannel;
import reactor.R;
import reactor.core.Environment;
import reactor.core.Reactor;

/**
 * Simple PublishSubscribeChannel {@link reactor.core.Reactor}-based {@link org.springframework.integration.core
 * .SubscribableChannel} implementation, using a broadcastEventRouting strategy.
 *
 * @author Stephane Maldini
 */
public class ReactorPublishSubscribeChannel extends AbstractReactorChannel implements SubscribableChannel {

	public ReactorPublishSubscribeChannel(Environment env, String dispatcher) {
		super(R.reactor().using(env).broadcastEventRouting(), dispatcher);
	}

	public ReactorPublishSubscribeChannel(Reactor reactor) {
		super(reactor);
	}
}
