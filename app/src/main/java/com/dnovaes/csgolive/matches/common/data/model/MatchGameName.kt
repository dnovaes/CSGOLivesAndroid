package com.dnovaes.csgolive.matches.common.data.model

import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@kotlinx.serialization.Serializable
enum class MatchGameName(val serialName: String) {
    LOL("LoL"),
    CSGO("CS:GO"),
    STARCRAFTII("StarCraft 2"),
    OTHER("OTHER");

    @Serializer(forClass = MatchGameName::class)
    companion object {
        override fun serialize(encoder: Encoder, value: MatchGameName) {
            encoder.encodeString(value.name.lowercase())
        }

        override fun deserialize(decoder: Decoder): MatchGameName {
            val value = decoder.decodeString()
            return try {
                valueOf(value)
            } catch (e: java.lang.IllegalArgumentException) {
                println("logd MatchGameName) unable to deserialize: '$value',\n\t" +
                        "Error Message: ${e.message}\n\tcause: ${e.cause}")
                OTHER
            }
        }

        fun valueOf(name: String?): MatchGameName {
            return enumValues<MatchGameName>().find { it.serialName == name } ?: OTHER
        }
    }
}