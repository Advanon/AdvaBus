package com.advanon.bus

import junit.framework.TestCase.assertEquals
import org.junit.Test


class SNSMessageTest {

    @Test
    fun `given an empty prefix, then the whole topic name should be returned from getTopic`() {
        assertEquals("prod-topic-name", buildSNSMessage().getTopic(""))
    }

    @Test
    fun `given a non empty prefix, then the result of getTopic should not include it`() {
        assertEquals("topic-name", buildSNSMessage().getTopic("prod-"))
    }

    private fun buildSNSMessage(): SNSMessage = SNSMessage(
        type = "",
        topicArn = "arn:aws:sns:us-east-1:123456:prod-topic-name",
        messageId = "",
        message = "",
        timestamp = "",
        subject = null,
        signatureVersion = "",
        signature = "",
        signingCertURL = "",
        unsubscribeURL = "",
        messageAttributes = null
    )
}
