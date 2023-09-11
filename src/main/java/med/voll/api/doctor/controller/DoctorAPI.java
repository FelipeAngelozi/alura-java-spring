package med.voll.api.doctor.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.doctor.model.dtos.DoctorSaveDTO;
import med.voll.api.doctor.model.dtos.DoctorListDTO;
import med.voll.api.doctor.model.dtos.DoctorUpdateDTO;
import med.voll.api.doctor.model.dtos.DoctorResponseDTO;
import med.voll.api.doctor.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequestMapping("/doctors")
@RestController
@SecurityRequirement(name = "bearer-key")
public class DoctorAPI {

    DoctorService doctorService;

    @Autowired
    public DoctorAPI(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<DoctorResponseDTO> save(@RequestBody @Valid DoctorSaveDTO doctorSaveDTO, UriComponentsBuilder uriComponentsBuilder) {
        DoctorResponseDTO doctorResponseDTO = this.doctorService.save(doctorSaveDTO);
        URI uri = uriComponentsBuilder.path("/doctors/{id}").buildAndExpand(doctorResponseDTO.id()).toUri();
        return ResponseEntity.created(uri).body(doctorResponseDTO);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<DoctorListDTO>> getAll(@PageableDefault(sort = {"name"}) Pageable pageable) {
        return ResponseEntity.ok(this.doctorService.getAll(pageable));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<DoctorResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(this.doctorService.getDoctorResponseDTOById(id));
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<DoctorResponseDTO> update(@RequestBody DoctorUpdateDTO doctorUpdateDTO) {
        return ResponseEntity.ok(this.doctorService.update(doctorUpdateDTO));
    }

    @DeleteMapping("delete/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        this.doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
