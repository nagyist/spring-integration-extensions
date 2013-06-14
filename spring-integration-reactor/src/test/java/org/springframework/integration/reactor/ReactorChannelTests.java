package org.springframework.integration.reactor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Jon Brisbin
 * @author Stephane Maldini
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ReactorChannelTests {

	@Autowired
	CountDownLatch counter;
	@Autowired
	CountDownLatch counterPubSub;
	@Autowired
	MessageChannel reactor;
	@Autowired
	MessageChannel reactorPubSub;

	@Test
	public void reactorDirectMessageChannelsSendAndReceiveMessages() throws InterruptedException {
		reactor.send(MessageBuilder.withPayload("Hey").build());

		counter.await(5, TimeUnit.SECONDS);
		assertThat("latch was counted down", counter.getCount(), is(0L));
	}

	@Test
	public void reactorPubSubMessageChannelsSendAndReceiveMessages() throws InterruptedException {
		reactorPubSub.send(MessageBuilder.withPayload("Hey").build());

		counterPubSub.await(5, TimeUnit.SECONDS);
		assertThat("latch was counted down", counterPubSub.getCount(), is(0L));
	}

}
