package med.voll.api.patient.model.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.address.model.Address;

public record PatientUpdateDTO(
        @NotNull
        Long id,
        String name,
        String phone,
        Address address) {
}
