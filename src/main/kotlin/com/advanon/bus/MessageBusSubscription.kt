package com.advanon.bus

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.context.ApplicationContext
import java.lang.reflect.Method

internal class MessageBusSubscription(
    private val converters: List<MessageBusConverter>,
    private val context: ApplicationContext,
    private val topicsPrefix: String
) {

    private val listenersMap: Map<Class<*>, List<Pair<Any, Method>>> by lazy { getListenersMap(context) }
    private val convertersMap: Map<String?, MessageBusConverter> by lazy { getConvertersMap(converters) }

    @SqsListener(
        value = ["\${message-bus.queue}"],
        deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS
    )
    fun queueListener(payload: String) {
        val snsMessage = jacksonObjectMapper().readValue<SNSMessage>(payload)
        convertersMap[snsMessage.getTopic(topicsPrefix)]?.convertMessagePayload(snsMessage.message)
            ?.let { notify(it.javaClass, it) }
    }

    private fun <T> notify(clazz: Class<T>, argument: T) {
        listenersMap[clazz]?.forEach { (bean, method) -> method.invoke(bean, argument) }
    }
}
