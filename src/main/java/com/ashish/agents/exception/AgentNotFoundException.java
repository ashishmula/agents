package com.ashish.agents.exception;

public class AgentNotFoundException extends RuntimeException {

    public AgentNotFoundException(Long id) {
        super("Agent not found: " + id);
    }
}
