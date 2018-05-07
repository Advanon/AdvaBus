package com.advanon.bus

import com.fasterxml.jackson.annotation.JsonProperty

internal data class SNSMessage(
    @JsonProperty("Type") val type: String,
    @JsonProperty("MessageId") val messageId: String,
    @JsonProperty("TopicArn") val topicArn: String,
    @JsonProperty("Subject") val subject: String?,
    @JsonProperty("Message") val message: String,
    @JsonProperty("Timestamp") val timestamp: String,
    @JsonProperty("SignatureVersion") val signatureVersion: String,
    @JsonProperty("Signature") val signature: String,
    @JsonProperty("SigningCertURL") val signingCertURL: String,
    @JsonProperty("UnsubscribeURL") val unsubscribeURL: String,
    @JsonProperty("MessageAttributes") val messageAttributes: Map<String, Map<String, String>>?
) {

    fun getTopic(prefix: String): String = topicArn.split(':').last().removePrefix(prefix)
}
