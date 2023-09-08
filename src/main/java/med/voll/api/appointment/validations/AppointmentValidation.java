package med.voll.api.appointment.validations;

import med.voll.api.appointment.model.dto.AppointmentSaveDTO;

public interface AppointmentValidation {

    void valid(AppointmentSaveDTO appointmentSaveDTO);
}