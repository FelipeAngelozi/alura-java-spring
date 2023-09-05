package med.voll.api.patient.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import med.voll.api.address.model.Address;
import med.voll.api.patient.model.dto.PatientSaveDTO;
import med.voll.api.patient.model.dto.PatientUpdateDTO;

import java.util.Objects;

@Entity
@Data
@Table(name = "patients")
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "cpf")
    private String cpf;
    @Embedded
    private Address address;
    @Column(name= "active")
    private Boolean active;

    public Patient(PatientSaveDTO patientSaveDTO) {
        this.name = patientSaveDTO.name();
        this.email = patientSaveDTO.email();
        this.phone = patientSaveDTO.phone();
        this.cpf = patientSaveDTO.cpf();
        this.address = patientSaveDTO.address();
    }

    public void updateFields(PatientUpdateDTO patientUpdateDTO) {
        this.name = Objects.nonNull(patientUpdateDTO.name()) ? patientUpdateDTO.name() : this.name;
        this.phone = Objects.nonNull(patientUpdateDTO.phone()) ? patientUpdateDTO.phone() : this.phone;
        this.address = Objects.nonNull(patientUpdateDTO.address()) ? patientUpdateDTO.address() : this.address;
    }
}
