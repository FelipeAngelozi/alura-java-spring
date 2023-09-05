package med.voll.api.appointment.model.dto;

import java.time.LocalDateTime;

public record AppointmentResponseDTO(Long id, Long doctorId, Long patientId, LocalDateTime date) {
}
