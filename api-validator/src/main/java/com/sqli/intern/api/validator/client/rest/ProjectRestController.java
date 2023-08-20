package com.sqli.intern.api.validator.client.rest;

import com.sqli.intern.api.validator.services.ProjectService;
import com.sqli.intern.api.validator.utilities.dtos.ProjectDto;
import com.sqli.intern.api.validator.utilities.exceptions.ProjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@CrossOrigin("*")
public class ProjectRestController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projectDto = projectService.getAllProjects();
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        ProjectDto projectDto = projectService.getProjectById(id);
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addProject(@RequestBody ProjectDto projectDto) {
        Long projectId = projectService.addProject(projectDto);
        return new ResponseEntity<>(projectId, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Long> updateProjectById(@RequestBody ProjectDto projectDto,
                                                  @PathVariable Long id) {
        Long projectId = projectService.updateProject(id, projectDto);
        return new ResponseEntity<>(projectId, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Long> deleteProjectById(@PathVariable Long id) {
        Long projectId = projectService.deleteProject(id);
        return new ResponseEntity<>(projectId, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/tests")
    public ResponseEntity<ProjectDto> runProjectTests(@PathVariable Long id) {
        ProjectDto projectDto = projectService.runProjectTests(id);
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/rapport")
    public ResponseEntity<ProjectDto> compareJson(@PathVariable Long id) {
        try {
            ProjectDto projectDto = projectService.compareJsonAndValidate(id);
            return ResponseEntity.ok(projectDto);
        } catch (IllegalArgumentException e) {
            throw new ProjectException("Validation error");
        }
    }
}
