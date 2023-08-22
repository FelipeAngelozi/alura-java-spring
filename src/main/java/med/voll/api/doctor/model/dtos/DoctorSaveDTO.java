package med.voll.api.doctor.model.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.address.model.AddressDTO;
import med.voll.api.doctor.model.enums.DocRoles;

public record DoctorSaveDTO(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String phone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull
        DocRoles docRole,
        @NotNull
        @Valid
        AddressDTO address) {
}
