package com.ashish.agents.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.ashish.agents.exception.AgentNotFoundException;
import com.ashish.agents.model.Agent;
import com.ashish.agents.model.AgentRequest;
import com.ashish.agents.model.AgentStatus;

/**
 * In-memory store for agents. Swap this for a database-backed repository later.
 */
@Service
public class AgentService {

    private final Map<Long, Agent> agents = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong();

    public List<Agent> findAll() {
        return new ArrayList<>(agents.values());
    }

    public Agent findById(Long id) {
        Agent agent = agents.get(id);
        if (agent == null) {
            throw new AgentNotFoundException(id);
        }
        return agent;
    }

    public Agent create(AgentRequest request) {
        long id = idSequence.incrementAndGet();
        Agent agent = new Agent(id, request.name(), request.role(), statusOrDefault(request));
        agents.put(id, agent);
        return agent;
    }

    public Agent update(Long id, AgentRequest request) {
        findById(id);
        Agent updated = new Agent(id, request.name(), request.role(), statusOrDefault(request));
        agents.put(id, updated);
        return updated;
    }

    public void delete(Long id) {
        findById(id);
        agents.remove(id);
    }

    private AgentStatus statusOrDefault(AgentRequest request) {
        return request.status() != null ? request.status() : AgentStatus.ACTIVE;
    }
}
