package com.lea.vim2.controller;

import com.lea.vim2.controller.response.SchedulingAppointmentsRequest;
import com.lea.vim2.exception.VimException;
import com.lea.vim2.service.SchedulingAppointmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
@Validated
public class SchedulingAppointmentsController {

    private final SchedulingAppointmentsService schedulingAppointmentsService;

    @GetMapping
    public List<String> getAppointments(@RequestParam String specialty, @RequestParam long date, @RequestParam double minScore) {
        List<String> appointments = schedulingAppointmentsService.getAppointments(specialty, date, minScore);
        return appointments;
    }

    @PostMapping
    public ResponseEntity<Boolean> makeAppointment(@RequestBody SchedulingAppointmentsRequest request) {
        boolean available = schedulingAppointmentsService.checkAvailability(request.getName(), request.getDate());
        if (!available) {
            throw new VimException("Provider is not available");
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
