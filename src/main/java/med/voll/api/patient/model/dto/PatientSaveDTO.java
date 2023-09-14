package med.voll.api.patient.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.address.model.Address;
import med.voll.api.address.model.AddressDTO;

public record PatientSaveDTO (
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String phone,
        @NotBlank
        String cpf,
        @NotNull
        @Valid
        AddressDTO address) {
}
