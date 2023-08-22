package med.voll.api.doctor.model;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.address.model.Address;
import med.voll.api.doctor.model.dtos.DoctorSaveDTO;
import med.voll.api.doctor.model.dtos.DoctorUpdateDTO;
import med.voll.api.doctor.model.enums.DocRoles;

import java.util.Objects;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "crm")
    private String crm;
    @Column(name = "doc_role")
    @Enumerated(EnumType.STRING)
    private DocRoles docRole;
    @Embedded
    private Address address;
    @Column(name= "active")
    private Boolean active;

    public Doctor(DoctorSaveDTO doctorSaveDTO) {
        this.active = true;
        this.name = doctorSaveDTO.name();
        this.email = doctorSaveDTO.email();
        this.crm = doctorSaveDTO.crm();
        this.phone = doctorSaveDTO.phone();
        this.docRole = doctorSaveDTO.docRole();
        this.address = new Address(doctorSaveDTO.address());
    }

    public void updateFields(DoctorUpdateDTO doctorUpdateDTO) {
        this.name = Objects.nonNull(doctorUpdateDTO.name()) ? doctorUpdateDTO.name() : this.name;
        this.phone = Objects.nonNull(doctorUpdateDTO.phone()) ? doctorUpdateDTO.phone() : this.phone;
        if (Objects.nonNull(doctorUpdateDTO.address())) this.address.updateFields(doctorUpdateDTO.address());
    }
}
