package med.voll.api.doctor.repository;

import med.voll.api.doctor.model.Doctor;
import med.voll.api.doctor.model.enums.DocRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByActiveTrue(Pageable pageable);

    @Query(value = """
            SELECT d
            FROM Doctor d
            WHERE d.active = true
            AND d.docRole = :docRole
            AND d.id NOT IN (
                SELECT a.doctor.id
                FROM Appointment a
                WHERE a.date = :date
            )
            ORDER BY RAND()
            LIMIT 1
            """)
    Doctor getRandomAvaiblByRole(@Param("docRole") DocRoles docRole,
                                 @Param("date") LocalDateTime date);

    @Query("""
            SELECT d.active
            FROM Doctor d
            WHERE d.id = :id
            """)
    Boolean findActiveById(@Param("id") Long id);
}
