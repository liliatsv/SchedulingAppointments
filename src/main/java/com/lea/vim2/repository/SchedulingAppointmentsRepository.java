package com.lea.vim2.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lea.vim2.exception.VimException;
import com.lea.vim2.service.model.SchedulingAppointmentsModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class SchedulingAppointmentsRepository {
    private final ObjectMapper objectMapper;

    private List<SchedulingAppointmentsModel> schedulingAppointmentsModels;

    public List<SchedulingAppointmentsModel> getAll() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("providers/providers.json").getFile());
            SchedulingAppointmentsModel[] list = objectMapper.readValue(file, SchedulingAppointmentsModel[].class);

            schedulingAppointmentsModels = Arrays.stream(list).toList();
            schedulingAppointmentsModels.stream()
                    .forEach(e ->
                            e.setSpecialties(
                                    e.getSpecialties().stream()
                                            .map(s -> s.toLowerCase())
                                            .collect(Collectors.toSet())
                            )
                    );
            return schedulingAppointmentsModels;
        } catch (IOException e) {
            throw new VimException("Error during file parsing " + e.getMessage());
        }
    }
}
