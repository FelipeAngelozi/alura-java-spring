package med.voll.api.appointment.controller;

import jakarta.validation.Valid;
import med.voll.api.appointment.model.dto.AppointmentResponseDTO;
import med.voll.api.appointment.model.dto.AppointmentSaveDTO;
import med.voll.api.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/appointment")
public class AppointmentAPI {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentAPI(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/booking")
    public ResponseEntity<AppointmentResponseDTO> booking(@RequestBody @Valid AppointmentSaveDTO appointmentSaveDTO,
                                                          UriComponentsBuilder uriComponentsBuilder) {
        AppointmentResponseDTO appointmentResponseDTO = this.appointmentService.booking(appointmentSaveDTO);
        URI uri = uriComponentsBuilder.path("/appointment/{id}").buildAndExpand(appointmentResponseDTO.id()).toUri();
        return ResponseEntity.created(uri).body(appointmentResponseDTO);
    }
}
