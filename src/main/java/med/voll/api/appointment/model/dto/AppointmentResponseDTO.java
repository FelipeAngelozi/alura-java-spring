package med.voll.api.appointment.model.dto;

import med.voll.api.appointment.model.Appointment;

import java.time.LocalDateTime;

public record AppointmentResponseDTO(Long id, Long doctorId, Long patientId, LocalDateTime date) {
    public AppointmentResponseDTO(Appointment appointment) {
        this(appointment.getId(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getDate()) ;
    }
}
