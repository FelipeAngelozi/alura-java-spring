package med.voll.api.doctor.repository;

import med.voll.api.address.model.AddressDTO;
import med.voll.api.appointment.model.Appointment;
import med.voll.api.doctor.model.Doctor;
import med.voll.api.doctor.model.dtos.DoctorSaveDTO;
import med.voll.api.doctor.model.enums.DocRoles;
import med.voll.api.patient.model.Patient;
import med.voll.api.patient.model.dto.PatientSaveDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    private final DoctorRepository doctorRepository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public DoctorRepositoryTest(DoctorRepository doctorRepository,
                                TestEntityManager testEntityManager) {
        this.doctorRepository = doctorRepository;
        this.testEntityManager = testEntityManager;
    }

    @Test
    @DisplayName("Deveria devolver null quando único médico cadastrado não está disponível na data")
    void getRandomAvaiblByRole1() {
        //given ou arrange
        LocalDateTime nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);
        Doctor doctor = saveDoctor("Doc", "doc@doc.com", "12345", DocRoles.CARDIOLOGIA);
        Patient patient = savePatient("Patient", "patient@patient.com", "1234356789");
        saveAppointment(doctor, patient, nextMondayAt10);

        //when ou act
        Doctor avaiableDoctor = doctorRepository.getRandomAvaiblByRole(DocRoles.CARDIOLOGIA, nextMondayAt10);

        //then ou assert
        assertThat(avaiableDoctor).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estivar disponível na data")
    void getRandomAvaiblByRole2() {
        //given ou arrange
        LocalDateTime nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        //when ou act
        Doctor doctor = saveDoctor("Doc", "doc@doc.com", "12345", DocRoles.CARDIOLOGIA);

        //then ou assert
        Doctor avaiableDoctor = doctorRepository.getRandomAvaiblByRole(DocRoles.CARDIOLOGIA, nextMondayAt10);
        assertThat(avaiableDoctor).isEqualTo(doctor);
    }

    private void saveAppointment(Doctor doctor, Patient patient, LocalDateTime date) {
        testEntityManager.persist(new Appointment(null, doctor, patient, date));
    }

    private Doctor saveDoctor(String name, String email, String crm, DocRoles docRole) {
        Doctor doctor = new Doctor(this.doctorData(name, email, crm, docRole));
        testEntityManager.persist(doctor);
        return doctor;
    }

    private Patient savePatient(String name, String email, String cpf) {
        Patient patient = new Patient(patientData(name, email, cpf));
        testEntityManager.persist(patient);
        return patient;
    }

    private DoctorSaveDTO doctorData(String name, String email, String crm, DocRoles docRoles) {
        return new DoctorSaveDTO(
                name,
                email,
                "61999999999",
                crm,
                docRoles,
                addressData()
        );
    }

    private PatientSaveDTO patientData(String name, String email, String cpf) {
        return new PatientSaveDTO(
                name,
                email,
                "61999999999",
                cpf,
                addressData()
        );
    }

    private AddressDTO addressData() {
        return new AddressDTO(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }

}