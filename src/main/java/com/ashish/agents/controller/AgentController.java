package com.ashish.agents.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashish.agents.model.Agent;
import com.ashish.agents.model.AgentRequest;
import com.ashish.agents.service.AgentService;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping
    public List<Agent> getAll() {
        return agentService.findAll();
    }

    @GetMapping("/{id}")
    public Agent getById(@PathVariable Long id) {
        return agentService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Agent> create(@RequestBody AgentRequest request) {
        Agent created = agentService.create(request);
        return ResponseEntity.created(URI.create("/api/agents/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public Agent update(@PathVariable Long id, @RequestBody AgentRequest request) {
        return agentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        agentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
