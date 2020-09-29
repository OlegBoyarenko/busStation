package com.olegB.Bus.controller;

import com.olegB.Bus.dto.BusDTO;
import com.olegB.Bus.exeption.WrongTimeFormatException;
import com.olegB.Bus.model.Bus;
import com.olegB.Bus.services.BusServiceImpl;
import com.olegB.Bus.util.DateTimeUtil;
import com.olegB.Bus.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class BusStationController {

    @Autowired
    private BusServiceImpl busService;
    @Autowired
    private DateTimeUtil dateTimeUtil;
    @Autowired
    private Validator validator;



    @GetMapping("/timetable")
    public ResponseEntity<List<BusDTO>> getTimetable() {
        return ResponseEntity.ok().body(busService.findAll());
    }

    @PostMapping("/save")
    @ResponseBody
    public List<Bus> save(@Valid @RequestBody List<Bus> busses) throws WrongTimeFormatException {
        List<Bus> busFromDB = busService.findAllBus();
        List<Bus> result = new ArrayList<>();
        for (Bus bus : busses) {
            if (!validator.isValid(bus)) {
                throw new WrongTimeFormatException("Parse exception");
            }
            if (dateTimeUtil.check(bus, busFromDB)) {
                result.add(bus);
            }
        }
        List<Bus> out = dateTimeUtil.reworkList(result);
        busService.saveAll(out);
        return out;
    }

    @DeleteMapping("/delete")
    public void deleteBus(@Valid @RequestBody Bus bus) {
        busService.delete(bus.getType(), bus.getStartTime(), bus.getEndTime());

    }

}
