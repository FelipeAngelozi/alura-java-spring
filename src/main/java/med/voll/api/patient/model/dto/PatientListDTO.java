package med.voll.api.patient.model.dto;

import med.voll.api.patient.model.Patient;

public record PatientListDTO(String name, String email, String cpf) {

    public PatientListDTO(Patient patient) {
        this(patient.getName(), patient.getEmail(), patient.getCpf());
    }
}
