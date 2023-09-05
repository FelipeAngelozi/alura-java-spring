package med.voll.api.patient.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.patient.model.dto.PatientListDTO;
import med.voll.api.patient.model.dto.PatientSaveDTO;
import med.voll.api.patient.model.dto.PatientUpdateDTO;
import med.voll.api.patient.model.dto.PatientResponseDTO;
import med.voll.api.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/patient")
public class PatientAPI {

    private final PatientService patientService;

    @Autowired
    public PatientAPI(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<PatientResponseDTO> save(@RequestBody @Valid PatientSaveDTO patientSaveDTO, UriComponentsBuilder uriComponentsBuilder) {
        PatientResponseDTO patientResponseDTO = this.patientService.save(patientSaveDTO);
        URI uri = uriComponentsBuilder.path("patient/{id}").buildAndExpand(patientResponseDTO.id()).toUri();
        return ResponseEntity.created(uri).body(patientResponseDTO);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<PatientListDTO>> getAll(@PageableDefault(sort = {"name"}) Pageable pageable) {
        return ResponseEntity.ok(this.patientService.getAll(pageable));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<PatientResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(this.patientService.getPatientResponseDTOById(id));
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<PatientResponseDTO> update(@RequestBody PatientUpdateDTO patientUpdateDTO) {
        return ResponseEntity.ok(this.patientService.update(patientUpdateDTO));
    }

    @DeleteMapping("delete/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        this.patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
