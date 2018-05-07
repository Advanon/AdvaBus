package com.advanon.bus

import org.springframework.aop.support.AopUtils
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.AnnotationUtils.getAnnotation
import org.springframework.core.annotation.AnnotationUtils.getAnnotationAttributes
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Method

internal fun getListenersMap(context: ApplicationContext): Map<Class<*>, List<Pair<Any, Method>>> =
    context.beanDefinitionNames
        .map { context.getBean(it) }
        .map { it.getAnnotatedMethods(MessageListener::class.java).map { method -> it to method } }
        .flatten()
        .filter { it.second.parameters.isNotEmpty() }
        .fold(mutableMapOf<Class<*>, MutableList<Pair<Any, Method>>>()) { map, pair ->
            val key = pair.second.parameterTypes.first()
            map[key] = (map[key] ?: mutableListOf()).apply { add(pair) }
            return@fold map
        }

internal fun getConvertersMap(converters: List<MessageBusConverter>): Map<String?, MessageBusConverter> =
    converters
        .map { extractAnnotationAttribute(it.javaClass, MessageBusConverterService::class.java, "topic") to it }
        .filter { (topic, _) -> topic != null }
        .toMap()

private fun <T : Annotation> Any.getAnnotatedMethods(annotation: Class<T>): List<Method> =
    AopUtils.getTargetClass(this).declaredMethods.filter { it.isAnnotationPresent(annotation) }

private fun <T : Annotation> extractAnnotationAttribute(
    instance: AnnotatedElement,
    annotation: Class<T>,
    attributeName: String
): String? = getAnnotationAttributes(getAnnotation(instance, annotation))[attributeName]?.let { it as String }
