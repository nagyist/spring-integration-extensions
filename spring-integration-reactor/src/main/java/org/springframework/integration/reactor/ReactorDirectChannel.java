package org.springframework.integration.reactor;

import org.springframework.integration.core.SubscribableChannel;
import reactor.R;
import reactor.core.Environment;
import reactor.core.Reactor;

/**
 * Simple DirectChannel {@link Reactor}-based {@link SubscribableChannel} implementation,
 * using a round robin dispatcher.
 *
 * @author Stephane Maldini
 */
public class ReactorDirectChannel extends AbstractReactorChannel {

	public ReactorDirectChannel(Environment env, String dispatcher) {
		super(R.reactor().using(env).roundRobinEventRouting(), dispatcher);
	}

	public ReactorDirectChannel(Reactor reactor) {
		super(reactor);
	}
}
