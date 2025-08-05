package com.autopilot.models.payload;

import lombok.Data;

@Data
public class QueuePayload<T> {
    private String eventType;
    private T payload;

    public QueuePayload(String eventType, T payload) {
        this.eventType = eventType;
        this.payload = payload;
    }
}
