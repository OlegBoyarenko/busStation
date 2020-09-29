package com.olegB.Bus.services;

import com.olegB.Bus.dto.BusDTO;
import com.olegB.Bus.model.Bus;
import com.olegB.Bus.util.Type;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusService {
    List<BusDTO> findAll();
    List<Bus> saveAll(List<Bus> busses);
    void delete(Type type, String startTime, String endTime);
    List<Bus> findAllBus();
}
