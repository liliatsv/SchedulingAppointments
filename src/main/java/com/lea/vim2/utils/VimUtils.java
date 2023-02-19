package com.lea.vim2.utils;

import com.lea.vim2.exception.VimException;
import com.lea.vim2.service.model.AvailabilityDates;
import com.lea.vim2.service.model.SchedulingAppointmentsModel;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class VimUtils {
    public static boolean dateMatches(SchedulingAppointmentsModel provider, long date) {
        List<AvailabilityDates> list = provider.getAvailableDates().stream()
                .filter(p -> date >= p.getFrom() && date <= p.getTo())
                .collect(Collectors.toList());
        return !list.isEmpty();
    }

    public static void validateDate(long longDate) {
        Date date = new Date(longDate);
        if (date.getTime() != longDate) {
            throw new VimException("Wrong date format");
        }
    }
}
