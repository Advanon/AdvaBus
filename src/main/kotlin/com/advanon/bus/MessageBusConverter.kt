package com.advanon.bus

/**
 * A class implementing [MessageBusConverter] is responsible for the conversion between a message payload and the
 * corresponding instance.
 *
 * @author Andrea Pivetta
 */
interface MessageBusConverter {

    /**
     * Convert a String containing a message payload to the corresponding instance.
     *
     * @param payload The message payload
     * @return The corresponding instance
     */
    fun convertMessagePayload(payload: String): Any?
}
