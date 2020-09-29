package com.olegB.Bus.util;

import com.olegB.Bus.model.Bus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Util class used to calculate efficient service
 */
@Component
public class DateTimeUtil {
    /**
     * Calculates the time difference between two values. Given the passage of time through zero
     * @param busStartTime
     * @param busEndTime
     * @return Returns a value in an easy-to-compare form
     */
    public double TimeDifference(String busStartTime, String busEndTime) {
        double endTime = split(busEndTime);
        double startTime = split(busStartTime);
        if (endTime > startTime) {
            return (endTime - startTime)/60;
        } else {
            double fullTime = 1440.0 - startTime;
            return (fullTime + endTime)/60;
        }
    }

    /**
     * Time parser.
     * @param time Time in valid form. @see com.olegB.Bus.util.Validator.class
     * @return returns the time in 0-1440 format
     */
    public Double split(String time) {
        String[] words = time.split("\\s*(\\s|:|\\.)\\s*");
        double hours = Double.parseDouble(words[0]);
        double minutes = Double.parseDouble(words[1]);
        return (hours*60) + (minutes);
    }

    /**
     * Compares whether a new service is more efficient than a scheduled service
     * @param candidate New service
     * @param busFromList One of current timetable service
     * @return true if new service if not efficient
     */
    public boolean checkForAdd(Bus candidate, Bus busFromList) {
        if (candidate.getStartTime().equals(busFromList.getStartTime()) &
                TimeDifference(candidate.getStartTime(), candidate.getEndTime()) > TimeDifference(busFromList.getStartTime(), busFromList.getEndTime())) {
            return true;
        }
        if((TimeDifference(busFromList.getStartTime(), candidate.getEndTime()) < TimeDifference(candidate.getStartTime(), candidate.getEndTime())) &
                (TimeDifference(busFromList.getStartTime(), busFromList.getEndTime()) <= TimeDifference(busFromList.getStartTime(), candidate.getEndTime()))) {
            return true;
        } else return false;
    }

    /**
     * Checks if the service is efficient
     * @param candidate New bus
     * @param buses Current timetable
     * @return true if  new Service is efficient
     */
    public boolean check(Bus candidate, List<Bus> buses) {
        for (Bus busFromList : buses) {
            //Drop out service longer than an hour
            if (TimeDifference(candidate.getStartTime(), candidate.getEndTime()) > 1.0) {
                return false;
            }
            //Compare company if service time is equals
            if (candidate.equals(busFromList)) {
                if (candidate.getType() == Type.POSH && busFromList.getType() == Type.GROTTY) {
                    return true;
                } else return false;
            }
            //Compare time
            if (checkForAdd(candidate, busFromList)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Drop out from list un efficient service
     * @param list list of service
     * @return list with efficient service
     */
    public List<Bus> reworkList(List<Bus> list) {
        List<Bus> newList = new ArrayList<>();
        List<Bus> outList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)  {
            newList.clear();
            newList.addAll(list);
            newList.remove(list.get(i));
            if (check(list.get(i), newList)) {
                outList.add(list.get(i));
            }
        }
        return outList;
    }

    /**
     * Sort services
     * @param busses unsorted list
     * @return sorted list
     */
    public List<Bus> sortByTime(List<Bus> busses) {
        List<Bus> poshBus = new ArrayList<>();
        List<Bus> grottyBus = new ArrayList<>();
        for (Bus bus : busses) {
            if (bus.getType().equals(Type.POSH)) {
                poshBus.add(bus);
            } else
                grottyBus.add(bus);
        }
        boolean isSortedPosh = false;
        boolean isSortedGrotty = false;
        Bus bufBus;
        if (poshBus.size() > 1) {
            while (!isSortedPosh) {
                isSortedPosh = true;
                for (int i = 0; i < poshBus.size() - 1; i++) {
                    if (split(poshBus.get(i).getStartTime()) > split(poshBus.get(i+1).getStartTime())) {
                        isSortedPosh = false;
                        bufBus = poshBus.get(i);
                        poshBus.set(i, poshBus.get(i+1));
                        poshBus.set(i+1, bufBus);
                    }
                }
            }
        }
        if (grottyBus.size() > 1) {
            while (!isSortedGrotty) {
                isSortedGrotty = true;
                for (int j = 0; j < grottyBus.size() - 1; j++) {
                    if (split(grottyBus.get(j).getStartTime()) > split(grottyBus.get(j+1).getStartTime())) {
                        isSortedGrotty = false;
                        bufBus = grottyBus.get(j);
                        grottyBus.set(j, grottyBus.get(j+1));
                        grottyBus.set(j+1, bufBus);
                    }
                }
            }
        }
        poshBus.addAll(grottyBus);
        return poshBus;
    }
}
