package org.springframework.integration.reactor.config.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractChannelParser;
import org.springframework.integration.reactor.ReactorDirectChannel;
import org.springframework.integration.reactor.ReactorPublishSubscribeChannel;
import org.w3c.dom.Element;
import reactor.core.Environment;
import reactor.util.StringUtils;

/**
 * XML namespace parser.
 *
 * @author Stephane Maldini
 */
public class ReactorChainParser extends AbstractChannelParser {

	@Override
	protected BeanDefinitionBuilder buildBeanDefinition(Element element, ParserContext parserContext) {
		return null;
	}

}
