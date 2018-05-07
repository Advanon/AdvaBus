package com.advanon.bus

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate

/**
 * Class for sending messages to SNS topics.
 *
 * @author Andrea Pivetta
 */
class MessageBusPublisher(
    private val template: NotificationMessagingTemplate,
    private val objectMapper: ObjectMapper,
    private val topicsPrefix: String
) {

    /**
     * Send a message to the given topic name
     *
     * @param topic The topic name
     * @param message The message to send
     */
    fun <T> publish(topic: String, message: T) {
        template.sendNotification("$topicsPrefix$topic", objectMapper.writeValueAsString(message), null)
    }
}
