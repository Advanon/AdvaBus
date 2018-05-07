package com.advanon.bus

import com.amazonaws.services.sns.AmazonSNS
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(MessageBusConfiguration::class)
@ConfigurationProperties(prefix = "message-bus")
internal class MessageBusConfiguration {

    @Bean
    internal fun snsMessagingTemplate(amazonSns: AmazonSNS): NotificationMessagingTemplate =
        NotificationMessagingTemplate(amazonSns)

    @Bean
    @ConditionalOnProperty(prefix = "message-bus", name = ["enabled"], matchIfMissing = true)
    internal fun messageBus(
        converters: List<MessageBusConverter>,
        context: ApplicationContext,
        @Value("\${message-bus.topics-prefix:}") topicsPrefix: String
    ): MessageBusSubscription = MessageBusSubscription(converters, context, topicsPrefix)

    @Bean
    internal fun messagePublisher(
        template: NotificationMessagingTemplate,
        mapper: ObjectMapper,
        @Value("\${message-bus.topics-prefix:}") topicsPrefix: String
    ): MessageBusPublisher = MessageBusPublisher(template, mapper, topicsPrefix)

    @Bean
    @ConditionalOnMissingBean(value = [ObjectMapper::class])
    internal fun objectMapper(): ObjectMapper = jacksonObjectMapper()
}
