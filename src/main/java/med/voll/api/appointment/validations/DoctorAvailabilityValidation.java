package med.voll.api.appointment.validations;

import med.voll.api.appointment.model.dto.AppointmentSaveDTO;
import med.voll.api.appointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DoctorAvailabilityValidation implements AppointmentValidation {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public DoctorAvailabilityValidation(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void valid(AppointmentSaveDTO appointmentSaveDTO) {
        if( this.appointmentRepository.existsByDoctorIdAndDate(appointmentSaveDTO.doctorId(), appointmentSaveDTO.date())) {
            throw new RuntimeException("O médico não está disponível nesse horário!");
        }
    }
}
