package med.voll.api.doctor.model.dtos;

import med.voll.api.address.model.Address;
import med.voll.api.doctor.model.Doctor;
import med.voll.api.doctor.model.enums.DocRoles;

public record DoctorResponseDTO(Long id, String name, String email, String crm, String phone, DocRoles docRole, Address address) {

    public DoctorResponseDTO(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getPhone(), doctor.getDocRole(), doctor.getAddress());
    }
}
