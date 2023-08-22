package med.voll.api.patient.model.dto;

import med.voll.api.address.model.Address;
import med.voll.api.patient.model.Patient;

public record PatientResponseDTO(Long id, String name, String email, String phone, String cpf, Address address) {
    public PatientResponseDTO(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getPhone(), patient.getCpf(), patient.getAddress());
    }
}
