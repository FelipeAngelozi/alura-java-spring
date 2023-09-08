package med.voll.api.appointment.validations;

import med.voll.api.appointment.model.dto.AppointmentSaveDTO;
import med.voll.api.doctor.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ActiveDoctorValidation implements AppointmentValidation {
    private final DoctorRepository doctorRepository;

    @Autowired
    public ActiveDoctorValidation(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public void valid(AppointmentSaveDTO appointmentSaveDTO) {
        if(Objects.isNull(appointmentSaveDTO.doctorId())) return;

        if(this.doctorRepository.findActiveById(appointmentSaveDTO.doctorId())){
            throw new RuntimeException("Consulta não pode ser agendada com médico excluído!");
        }
    }
}
