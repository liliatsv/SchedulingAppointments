package com.lea.vim2.service;

import com.lea.vim2.repository.SchedulingAppointmentsRepository;
import com.lea.vim2.service.model.SchedulingAppointmentsModel;
import com.lea.vim2.utils.VimUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class SchedulingAppointmentsService {

    private final SchedulingAppointmentsRepository schedulingAppointmentsRepository;

    public List<String> getAppointments(@NotEmpty String specialty, long date, double minScore) {
        VimUtils.validateDate(date);
        List<SchedulingAppointmentsModel> list = schedulingAppointmentsRepository.getAll();
        List<String> namesList = list.stream()
                .filter(e -> e.getSpecialties().contains(specialty.toLowerCase()))
                .filter(e -> e.getScore() >= minScore)
                .filter(e -> VimUtils.dateMatches(e, date))
                .map(SchedulingAppointmentsModel::getName)
                .collect(Collectors.toList());
        if (namesList.size() >= 2) {
            namesList.sort(Comparator.reverseOrder());
        }
        return namesList;
    }

    public boolean checkAvailability(@NotEmpty String name, long date) {
        VimUtils.validateDate(date);
        List<SchedulingAppointmentsModel> list = schedulingAppointmentsRepository.getAll();
        List<SchedulingAppointmentsModel> collect = list.stream()
                .filter(e -> e.getName().equals(name))
                .filter(e -> VimUtils.dateMatches(e, date))
                .toList();
        return !collect.isEmpty();
    }

}
