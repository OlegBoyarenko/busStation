package com.olegB.Bus.services;

import com.olegB.Bus.dto.BusDTO;
import com.olegB.Bus.model.Bus;
import com.olegB.Bus.repository.BusRepository;
import com.olegB.Bus.util.DateTimeUtil;
import com.olegB.Bus.util.Type;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final DateTimeUtil dateTimeUtil;

    public BusServiceImpl(BusRepository busRepository, DateTimeUtil dateTimeUtil) {
        this.busRepository = busRepository;
        this.dateTimeUtil = dateTimeUtil;
    }

    @Override
    public List<BusDTO> findAll() {
        List<BusDTO> resultDTO = new ArrayList<>();

        List<Bus> result = dateTimeUtil.sortByTime(busRepository.findAll());
        for (Bus bus : result) {
            resultDTO.add(new BusDTO(bus.getType(), bus.getStartTime(), bus.getEndTime()));
        }
        return resultDTO;
    }

    @Override
    public List<Bus> saveAll(List<Bus> busses) {
        busRepository.saveAll(busses);
        return busses;
    }

    @Override
    public void delete(Type type, String startTime, String endTime) {
        busRepository.deleteBus(type, startTime, endTime);
    }

    @Override
    public List<Bus> findAllBus() {
        return dateTimeUtil.sortByTime(busRepository.findAll());
    }

}
