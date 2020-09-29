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
public class BusStationExceptionTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BusRepository busRepository;
    Bus bus;
    private static final String JSON_TO_DESERIALIZE =
            "[{\"startTime\":\"25:10\",\"endTime\":\"12:30\",\"type\":\"POSH\"}]";

    @Before
    public void setup() {
        busRepository.deleteAll();
        bus = new Bus();
        bus.setType(Type.POSH);
        bus.setEndTime("25:30");
        bus.setStartTime("12:10");

    }

    @Test
    public void add_invalidBusTime() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/save")
                .content(JSON_TO_DESERIALIZE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
