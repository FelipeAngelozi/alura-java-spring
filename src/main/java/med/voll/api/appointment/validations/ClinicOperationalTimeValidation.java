package med.voll.api.appointment.validations;

import med.voll.api.appointment.model.dto.AppointmentSaveDTO;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ClinicOperationalTimeValidation implements AppointmentValidation {

    public void valid(AppointmentSaveDTO appointmentSaveDTO) {
        Boolean isSunday = appointmentSaveDTO.date().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        Boolean beforeOpen = appointmentSaveDTO.date().getHour() < 7;
        Boolean afterClose = appointmentSaveDTO.date().getHour() > 18;

        if(isSunday || beforeOpen || afterClose){
            throw new RuntimeException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
