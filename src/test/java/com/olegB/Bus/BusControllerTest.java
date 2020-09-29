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
public class BusControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BusRepository busRepository;
    private static final String JSON_TO_DESERIALIZE =
            "[{\"startTime\":\"12:10\",\"endTime\":\"12:30\",\"type\":\"POSH\"}]";
    private static final String JSON_TO_DELETE =
            "{\"startTime\":\"12:10\",\"endTime\":\"12:30\",\"type\":\"POSH\"}";
    Bus bus;

    @Before
    public void setup() {
        busRepository.deleteAll();
        bus = new Bus();
        bus.setType(Type.POSH);
        bus.setEndTime("12:30");
        bus.setStartTime("12:10");

    }

    @Test
    public void add_new_BusDTOEntities() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/save")
                .content(JSON_TO_DESERIALIZE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].startTime").value("12:10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endTime").value("12:30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("POSH"));
    }

    @Test
    public void get_all_BusDTOEntities() throws Exception {
        busRepository.save(bus);
        mockMvc.perform(get("/timetable"))
                .andExpect(status().isOk())
                .andExpect(content().json(JSON_TO_DESERIALIZE));
    }

    @Test
    public void delete_BusByField() throws Exception {
        busRepository.save(bus);
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete")
                .content(JSON_TO_DELETE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

}
