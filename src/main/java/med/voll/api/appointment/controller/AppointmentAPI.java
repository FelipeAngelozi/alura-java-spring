package med.voll.api.appointment.controller;

import jakarta.validation.Valid;
import med.voll.api.appointment.model.dto.AppointmentSaveDTO;
import med.voll.api.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentAPI {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentAPI(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/booking")
    public ResponseEntity booking(@RequestBody @Valid AppointmentSaveDTO appointmentSaveDTO) {
        return null;
    }
}
