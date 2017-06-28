package com.kotlintest.services.eventbus

import de.greenrobot.event.EventBus

/**
 * Created by alex on 12/27/16.
 */

class EventBusCreator {

    companion object {
        fun createDefault(): EventBus {
            return EventBus.builder()
                    .sendNoSubscriberEvent(false)
                    .logNoSubscriberMessages(false)
                    .build()
        }
    }

}
