package med.voll.api.appointment.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentSaveDTO(
        Long doctorId,
        @NotNull
        Long patientId,
        @NotNull
        @Future
        LocalDateTime date
        ) {
}
