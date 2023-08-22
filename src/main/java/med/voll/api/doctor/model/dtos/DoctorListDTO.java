package med.voll.api.doctor.model.dtos;

import jakarta.validation.constraints.NotNull;
import med.voll.api.doctor.model.Doctor;
import med.voll.api.doctor.model.enums.DocRoles;

public record DoctorListDTO(
        Long id,
        String name,
        String email,
        String crm,
        DocRoles docRoles) {

    public DoctorListDTO(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getDocRole());
    }

}
