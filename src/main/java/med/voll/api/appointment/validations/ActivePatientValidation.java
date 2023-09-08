package med.voll.api.appointment.validations;

import med.voll.api.appointment.model.dto.AppointmentSaveDTO;
import med.voll.api.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ActivePatientValidation implements AppointmentValidation {
    private final PatientRepository patientRepository;

    @Autowired
    public ActivePatientValidation(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void valid(AppointmentSaveDTO appointmentSaveDTO) {
        if(this.patientRepository.findActiveById(appointmentSaveDTO.doctorId())){
            throw new RuntimeException("Consulta não pode ser agendada com paciente excluido!");
        }
    }
}
