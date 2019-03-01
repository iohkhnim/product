package com.example.demo;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import myapplication.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = DemoApplication.class)
public class CalculateControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void AccessCalculate_thenReturnStringResult() throws Exception {
    this.mockMvc.perform(get("/calculate?p1=10&p2=20")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("30")));
  }
}
