package com.sqli.intern.api.validator.client.rest;

import com.sqli.intern.api.validator.services.impl.JiraTicketService;
import com.sqli.intern.api.validator.utilities.models.JiraPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/bugs")
public class ServiceTicketController {

    @Autowired
    private JiraTicketService jiraTicketService;

    @PostMapping
    public ResponseEntity<String> createJiraTicket(@RequestBody JiraPayload jiraPayload) {
        try {
            String response = jiraTicketService.createJiraTicket(jiraPayload);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Unable to process your request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
