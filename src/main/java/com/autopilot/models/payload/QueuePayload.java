package com.autopilot.models.payload;

import com.autopilot.enums.EventType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueuePayload<T> {
    private EventType eventType;
    private T payload;

    public QueuePayload(EventType eventType, T payload) {
        this.eventType = eventType;
        this.payload = payload;
    }
}
