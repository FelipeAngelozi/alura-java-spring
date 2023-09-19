package med.voll.api.appointment.controller;

import med.voll.api.appointment.model.dto.AppointmentResponseDTO;
import med.voll.api.appointment.model.dto.AppointmentSaveDTO;
import med.voll.api.appointment.service.AppointmentService;
import med.voll.api.doctor.model.enums.DocRoles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentAPITest {

    private MockMvc mockMvc;
    private JacksonTester<AppointmentSaveDTO> saveDTOJacksonTester;
    private JacksonTester<AppointmentResponseDTO> responseDTOJacksonTester;
    @MockBean
    private AppointmentService appointmentService;

    @Autowired
    public AppointmentAPITest(MockMvc mockMvc,
                              JacksonTester<AppointmentSaveDTO> saveDTOJacksonTester,
                              JacksonTester<AppointmentResponseDTO> responseDTOJacksonTester) {
        this.mockMvc = mockMvc;
        this.saveDTOJacksonTester = saveDTOJacksonTester;
        this.responseDTOJacksonTester = responseDTOJacksonTester;
    }

    @Test
    @WithMockUser
    @DisplayName("Deveria devoler código HTTP 400 quando informações estão inválidas")
    void bookingCase1() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/appointment/booking"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    @DisplayName("Deveria devoler código HTTP 200 quando informações estão válidas")
    void bookingCase2() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(1);
        AppointmentResponseDTO responseDTO = new AppointmentResponseDTO(null, 2l, 5l, localDateTime);

        when(appointmentService.booking(any())).thenReturn(responseDTO);

        MockHttpServletResponse response = mockMvc.perform(
                post("/appointment/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(saveDTOJacksonTester.write(new AppointmentSaveDTO(2l, 5l, localDateTime, DocRoles.CARDIOLOGIA)).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var expectedJson = responseDTOJacksonTester.write(responseDTO).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}