package med.voll.api.patient.service;

import jakarta.transaction.Transactional;
import med.voll.api.patient.model.Patient;
import med.voll.api.patient.model.dto.PatientListDTO;
import med.voll.api.patient.model.dto.PatientSaveDTO;
import med.voll.api.patient.model.dto.PatientUpdateDTO;
import med.voll.api.patient.model.dto.PatientResponseDTO;
import med.voll.api.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    public PatientResponseDTO save(PatientSaveDTO patientSaveDTO) {
        return new PatientResponseDTO(this.patientRepository.save(new Patient(patientSaveDTO)));
    }

    public Page<PatientListDTO> getAll(Pageable pageable) {
        return this.patientRepository.findAllByActiveTrue(pageable).map(PatientListDTO::new);
    }

    @Transactional
    public PatientResponseDTO update(PatientUpdateDTO patientUpdateDTO) {
        Patient patient = this.patientRepository.getReferenceById(patientUpdateDTO.id());
        patient.updateFields(patientUpdateDTO);
        return new PatientResponseDTO(patient);
    }

    @Transactional
    public void delete(Long id) {
        Patient patient = this.patientRepository.getReferenceById(id);
        patient.setActive(false);
    }

    public PatientResponseDTO getPatientResponseDTOById(Long id) {
        return new PatientResponseDTO(this.patientRepository.getById(id));
    }

    public Patient getById(Long id){
        return this.patientRepository.findById(id).orElse(null);
    }

    public boolean verifyIfExists(Long id) {
        return this.patientRepository.existsById(id);
    }
}
