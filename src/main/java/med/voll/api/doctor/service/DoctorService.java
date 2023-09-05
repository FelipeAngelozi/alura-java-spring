package med.voll.api.doctor.service;

import jakarta.transaction.Transactional;
import med.voll.api.doctor.model.Doctor;
import med.voll.api.doctor.model.dtos.DoctorSaveDTO;
import med.voll.api.doctor.model.dtos.DoctorListDTO;
import med.voll.api.doctor.model.dtos.DoctorUpdateDTO;
import med.voll.api.doctor.model.dtos.DoctorResponseDTO;
import med.voll.api.doctor.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public DoctorResponseDTO save(DoctorSaveDTO doctorSaveDTO) {
        return new DoctorResponseDTO(this.doctorRepository.save(new Doctor(doctorSaveDTO)));
    }

    public Page<DoctorListDTO> getAll(Pageable pageable) {
        return this.doctorRepository.findAllByActiveTrue(pageable).map(DoctorListDTO::new);
    }

    @Transactional
    public DoctorResponseDTO update(DoctorUpdateDTO doctorUpdateDTO) {
        Doctor doctor = this.doctorRepository.getReferenceById(doctorUpdateDTO.id());
        doctor.updateFields(doctorUpdateDTO);
        return new DoctorResponseDTO(doctor);
    }

    @Transactional
    public void delete(Long id) {
        Doctor doctor = this.doctorRepository.getReferenceById(id);
        doctor.setActive(false);
    }

    public DoctorResponseDTO getDoctorResponseDTOById(Long id) {
        return new DoctorResponseDTO(this.doctorRepository.getById(id));
    }

    public Doctor getById(Long id) {
        return this.doctorRepository.findById(id).orElse(null);
    }

    public boolean verifyIfExists(Long id) {
        return this.doctorRepository.existsById(id);
    }
}
