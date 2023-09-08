package med.voll.api.appointment.validations;

import med.voll.api.appointment.model.dto.AppointmentSaveDTO;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HourAntecipationValidation implements AppointmentValidation {
    public void valid(AppointmentSaveDTO appointmentSaveDTO) {
        if(Duration.between(LocalDateTime.now(), appointmentSaveDTO.date()).toMinutes() < 30) {
            throw new RuntimeException("Consulta deve ser agendada com antecedencia mínima de 30 minutos");
        }
    }
}
