package com.autopilot.messaging;

import com.autopilot.config.exception.ApplicationException;
import com.autopilot.models.payload.QueuePayload;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for publishing messages to a message queue using a generic QueuePayload wrapper.
 */
public interface IBaseQueuePublisher {

    /**
     * Publishes with additional metadata like correlationId, timestamp, etc.
     *
     * @param queueName Name of the queue
     * @param payload   Message wrapped in QueuePayload
     * @param headers   Optional headers/metadata
     */
    void publish(String queueName, QueuePayload<?> payload, Map<String, Object> headers);

    /**
     * Asynchronously publishes with additional metadata like correlationId, timestamp, etc.
     *
     * @param queueName Name of the queue
     * @param payload   Message wrapped in QueuePayload
     * @param headers   Optional headers/metadata
     * @return CompletableFuture representing the async operation
     */
    CompletableFuture<Void> publishAsync(String queueName, QueuePayload<?> payload, Map<String, Object> headers);

    /**
     * Publishes a message to an exchange with a routing key (for topic or direct exchanges).
     *
     * @param exchangeName Exchange to publish to
     * @param routingKey   Routing key for routing to correct queue
     * @param payload      Message wrapped in QueuePayload
     * @param headers      Optional headers/metadata
     */
    void publishWithRoutingKey(String exchangeName, String routingKey, QueuePayload<?> payload, Map<String, Object> headers);

    /**
     * Asynchronously publishes a message to an exchange with a routing key.
     *
     * @param exchangeName Exchange to publish to
     * @param routingKey   Routing key for routing to correct queue
     * @param payload      Message wrapped in QueuePayload
     * @param headers      Optional headers/metadata
     * @return CompletableFuture representing the async operation
     */
    CompletableFuture<Void> publishAsyncWithRoutingKey(String exchangeName, String routingKey, QueuePayload<?> payload, Map<String, Object> headers);
}
