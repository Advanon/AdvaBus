package com.advanon.bus

/**
 * Annotation for mapping SNS messages onto specific handler functions.
 *
 * A function annotated with [MessageListener] will be called with an instance of the specified parameter every
 * time a new message of the parameter's type is received.
 *
 * @author Andrea Pivetta
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class MessageListener

/**
 * Indicates that an annotated class is a MessageBusConverterService, meaning that it's responsible for converting a
 * received SNS message to the correct instance.
 *
 * @param topic The topic [ARN](https://docs.aws.amazon.com/general/latest/gr/aws-arns-and-namespaces.html) that this
 * service is interested in
 *
 * @author Andrea Pivetta
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class MessageBusConverterService(@Suppress("unused") val topic: String)
