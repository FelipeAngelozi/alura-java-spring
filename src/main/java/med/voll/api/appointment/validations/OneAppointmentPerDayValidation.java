package med.voll.api.appointment.validations;

import med.voll.api.appointment.model.dto.AppointmentSaveDTO;
import med.voll.api.appointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OneAppointmentPerDayValidation implements AppointmentValidation {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public OneAppointmentPerDayValidation(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void valid(AppointmentSaveDTO appointmentSaveDTO) {
        if(this.appointmentRepository.existsByPatientIdAndDateBetween(appointmentSaveDTO.patientId(), appointmentSaveDTO.date().withHour(7), appointmentSaveDTO.date().withHour(18))){
            throw new RuntimeException("O paciente só pode marcar uma consulta por dia!");
        }
    }
}
