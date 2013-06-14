package org.springframework.integration.reactor.channel;

import org.springframework.integration.Message;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.core.MessageHandler;
import org.springframework.integration.core.SubscribableChannel;
import org.springframework.util.Assert;
import reactor.Fn;
import reactor.R;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.fn.Consumer;
import reactor.fn.Event;
import reactor.fn.dispatch.SynchronousDispatcher;
import reactor.fn.registry.Registration;
import reactor.fn.selector.Selector;

/**
 * Abstract {@link reactor.core.Reactor}-based {@link org.springframework.integration.core.SubscribableChannel}
 * implementation.
 *
 * @author Jon Brisbin
 * @author Stephane Maldini
 */
public abstract class AbstractReactorChannel extends AbstractMessageChannel implements SubscribableChannel {

	private final Reactor reactor;

	private final Object   key      = new Object();
	private final Selector selector = Fn.$(key);

	public AbstractReactorChannel(Reactor reactor) {
		Assert.notNull(reactor, "Reactor cannot be null.");
		this.reactor = reactor;
	}

	public AbstractReactorChannel(Reactor.Spec rspec, String dispatcher) {
		Assert.notNull(rspec, "Reactor.Spec cannot be null.");
		if (null != dispatcher) {
			if ("sync".equalsIgnoreCase(dispatcher)) {
				rspec.sync();
			} else {
				rspec.dispatcher(dispatcher);
			}
		}
		this.reactor = rspec.get();
	}

	@Override
	protected void onInit() throws Exception {
		super.onInit();

	}

	@Override
	protected boolean doSend(Message<?> message, long timeout) {
		reactor.notify(key, Event.wrap(message));
		return true;
	}

	@Override
	public boolean subscribe(MessageHandler handler) {
		reactor.on(selector, new MessageHandlerConsumer(handler));
		return true;
	}

	@Override
	public boolean unsubscribe(MessageHandler handler) {
		for (Registration<? extends Consumer<? extends Event<?>>> reg : reactor.getConsumerRegistry()) {
			if (reg.getObject() instanceof MessageHandlerConsumer && ((MessageHandlerConsumer) reg.getObject()).handler == handler) {
				reg.cancel();
			}
		}
		return true;
	}

	private static class MessageHandlerConsumer implements Consumer<Event<Message<?>>> {
		private final MessageHandler handler;

		private MessageHandlerConsumer(MessageHandler handler) {
			this.handler = handler;
		}

		@Override
		public void accept(Event<Message<?>> ev) {
			handler.handleMessage(ev.getData());
		}
	}

}
