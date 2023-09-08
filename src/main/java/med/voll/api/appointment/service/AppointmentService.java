package med.voll.api.appointment.service;

import jakarta.transaction.Transactional;
import med.voll.api.appointment.model.Appointment;
import med.voll.api.appointment.model.dto.AppointmentResponseDTO;
import med.voll.api.appointment.model.dto.AppointmentSaveDTO;
import med.voll.api.appointment.repository.AppointmentRepository;
import med.voll.api.appointment.validations.AppointmentValidation;
import med.voll.api.doctor.model.Doctor;
import med.voll.api.doctor.service.DoctorService;
import med.voll.api.patient.model.Patient;
import med.voll.api.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final List<AppointmentValidation> appointmentValidations;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorService doctorService,
                              PatientService patientService,
                              List<AppointmentValidation> appointmentValidations) {
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.appointmentValidations = appointmentValidations;
    }

    @Transactional
    public AppointmentResponseDTO booking(AppointmentSaveDTO appointmentSaveDTO) {
        if (this.patientService.verifyIfExists(appointmentSaveDTO.patientId()))
            throw new RuntimeException("ID do paciente inválido!");
        if (Objects.nonNull(appointmentSaveDTO.doctorId()) && this.doctorService.verifyIfExists(appointmentSaveDTO.doctorId()))
            throw new RuntimeException("ID do médico inválido!");

        appointmentValidations.forEach(validation -> validation.valid(appointmentSaveDTO));

        Patient patient = this.patientService.getById(appointmentSaveDTO.patientId());
        Doctor doctor = this.chooseDoctor(appointmentSaveDTO);

        if(Objects.isNull(doctor)) throw new RuntimeException("Não existe médico disponível nessa data!");

        Appointment appointment = new Appointment(null, doctor, patient, appointmentSaveDTO.date());
        appointmentRepository.save(appointment);

        return new AppointmentResponseDTO(appointment);
    }

    private Doctor chooseDoctor(AppointmentSaveDTO appointmentSaveDTO) {
        if (Objects.nonNull(appointmentSaveDTO.doctorId())) return doctorService.getById(appointmentSaveDTO.doctorId());
        if (Objects.isNull(appointmentSaveDTO.docRole())) throw new RuntimeException("Especialidade é obrigadtória quando médico não for escolhido!");

        return this.doctorService.getRandomAvaiblByRole(appointmentSaveDTO.docRole(), appointmentSaveDTO.date());
    }
}
