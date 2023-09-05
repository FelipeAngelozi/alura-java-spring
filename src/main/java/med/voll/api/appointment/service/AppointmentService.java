package med.voll.api.appointment.service;

import med.voll.api.appointment.model.Appointment;
import med.voll.api.appointment.model.dto.AppointmentSaveDTO;
import med.voll.api.appointment.repository.AppointmentRepository;
import med.voll.api.doctor.model.Doctor;
import med.voll.api.doctor.service.DoctorService;
import med.voll.api.patient.model.Patient;
import med.voll.api.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, DoctorService doctorService, PatientService patientService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    public void booking(AppointmentSaveDTO appointmentSaveDTO) {
        if (this.patientService.verifyIfExists(appointmentSaveDTO.patientId())) throw new RuntimeException("ID do paciente inválido!");
        if (Objects.nonNull(appointmentSaveDTO.doctorId()) && this.doctorService.verifyIfExists(appointmentSaveDTO.doctorId())) throw new RuntimeException("ID do médico inválido!");


        Patient patient = this.patientService.getById(appointmentSaveDTO.patientId());
        Doctor doctor = this.doctorService.getById(appointmentSaveDTO.doctorId());

        Appointment appointment = new Appointment(null, doctor, patient, appointmentSaveDTO.date());
//        return appointmentRepository.save(appointmentDataRequestDTO);
    }
}
