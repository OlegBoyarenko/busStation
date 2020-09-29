package com.olegB.Bus;

import com.olegB.Bus.dto.BusDTO;
import com.olegB.Bus.model.Bus;
import com.olegB.Bus.repository.BusRepository;
import com.olegB.Bus.util.Type;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = BusApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class Logic_BusStationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BusRepository busRepository;
    private static final String JSON_TO_DESERIALIZE =
            "[{\"startTime\":\"10:15\",\"endTime\":\"11:10\",\"type\":\"POSH\"}," +
            "{\"startTime\":\"10:10\",\"endTime\":\"11:00\",\"type\":\"POSH\"}," +
            "{\"startTime\":\"16:30\",\"endTime\":\"18:45\",\"type\":\"GROTTY\"}," +
            "{\"startTime\":\"12:05\",\"endTime\":\"12:30\",\"type\":\"POSH\"}," +
            "{\"startTime\":\"12:30\",\"endTime\":\"13:25\",\"type\":\"GROTTY\"}," +
            "{\"startTime\":\"12:45\",\"endTime\":\"13:25\",\"type\":\"GROTTY\"}," +
            "{\"startTime\":\"17:25\",\"endTime\":\"18:01\",\"type\":\"POSH\"}," +
            "{\"startTime\":\"10:10\",\"endTime\":\"11:00\",\"type\":\"GROTTY\"}]";
    private static final String JSON_ORDER_OF_BUSSES =
            "[{\"startTime\":\"10:10\",\"endTime\":\"11:00\",\"type\":\"POSH\"}," +
                    "{\"startTime\":\"10:15\",\"endTime\":\"11:10\",\"type\":\"POSH\"}," +
                    "{\"startTime\":\"12:05\",\"endTime\":\"12:30\",\"type\":\"POSH\"}," +
                    "{\"startTime\":\"12:45\",\"endTime\":\"13:25\",\"type\":\"GROTTY\"}," +
                    "{\"startTime\":\"17:25\",\"endTime\":\"18:01\",\"type\":\"POSH\"}]";
    private static final String JSON_UN_EFFICIENT_BUS =
            "[{\"startTime\":\"12:50\",\"endTime\":\"13:25\",\"type\":\"POSH\"}]";




    List<Bus> busList;

    @Before
    public void setup() {
        busRepository.deleteAll();

        Bus posh = new Bus();
        posh.setType(Type.POSH);
        posh.setStartTime("10:10");
        posh.setEndTime("11:00");

        Bus posh2 = new Bus();
        posh2.setType(Type.POSH);
        posh2.setStartTime("10:15");
        posh2.setEndTime("11:10");

        Bus posh8 = new Bus();
        posh8.setType(Type.POSH);
        posh8.setStartTime("12:05");
        posh8.setEndTime("12:30");

        Bus posh3 = new Bus();
        posh3.setType(Type.GROTTY);
        posh3.setStartTime("12:45");
        posh3.setEndTime("13:25");

        Bus posh4 = new Bus();
        posh4.setType(Type.POSH);
        posh4.setStartTime("17:25");
        posh4.setEndTime("18:01");
        busList = new ArrayList<>();
        busList.add(posh);
        busList.add(posh2);
        busList.add(posh8);
        busList.add(posh3);
        busList.add(posh4);
    }

    @Test
    public void add_onlyEfficient_BusEntities() throws Exception {
        busRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/save")
                .content(JSON_TO_DESERIALIZE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].startTime").value("10:15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endTime").value("11:10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("POSH"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].startTime").value("10:10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endTime").value("11:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type").value("POSH"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].startTime").value("12:05"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].endTime").value("12:30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].type").value("POSH"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].startTime").value("12:45"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].endTime").value("13:25"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].type").value("GROTTY"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].startTime").value("17:25"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].endTime").value("18:01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].type").value("POSH"));
    }

    @Test
    @Transactional
    public void check_orderOfBus() throws Exception {
        busRepository.saveAll(busList);
        mockMvc.perform(get("/timetable"))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_ORDER_OF_BUSSES));
    }

    @Test
    public void add_efficientService() throws Exception {
        busRepository.saveAll(busList);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/save")
                .content(JSON_UN_EFFICIENT_BUS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_ORDER_OF_BUSSES));
    }
}
