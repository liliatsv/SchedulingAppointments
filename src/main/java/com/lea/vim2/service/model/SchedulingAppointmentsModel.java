package com.lea.vim2.service.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchedulingAppointmentsModel {

    private String name;
    private Double score;
    private Set<String> specialties;
    private List<AvailabilityDates> availableDates;

}
