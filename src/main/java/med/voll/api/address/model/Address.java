package med.voll.api.address.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String street;
    private String neighbourhood;
    private String zipcode;
    private String number;
    private String complement;
    private String city;
    private String uf;

    public Address(AddressDTO addressDTO) {
        this.street = addressDTO.street();
        this.neighbourhood = addressDTO.neighbourhood();
        this.zipcode = addressDTO.zipcode();
        this.number = addressDTO.number();
        this.complement = addressDTO.complement();
        this.city = addressDTO.city();
        this.uf = addressDTO.uf();
    }

    public void updateFields(AddressDTO address) {
        this.street = Objects.nonNull(address.street()) ? address.street() : this.street;
        this.neighbourhood = Objects.nonNull(address.neighbourhood()) ? address.neighbourhood() : this.neighbourhood;
        this.zipcode = Objects.nonNull(address.zipcode()) ? address.zipcode() : this.zipcode;
        this.number = Objects.nonNull(address.number()) ? address.number() : this.number;
        this.complement = Objects.nonNull(address.complement()) ? address.complement() : this.complement;
        this.city = Objects.nonNull(address.city()) ? address.city() : this.city;
        this.uf = Objects.nonNull(address.uf()) ? address.uf() : this.uf;
    }
}
