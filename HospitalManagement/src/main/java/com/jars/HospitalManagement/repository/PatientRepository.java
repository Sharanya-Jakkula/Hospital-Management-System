package com.jars.HospitalManagement.repository;

import com.jars.HospitalManagement.entity.Patient;
import com.jars.HospitalManagement.entity.dto.BloodGroupCountResponseEntity;
import com.jars.HospitalManagement.entity.type.BloodGroupType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


public interface PatientRepository extends JpaRepository<Patient,Long> {

    Patient findByName(String name);

    List<Patient> findByBirthDateOrEmail(LocalDate birthdate, String email);

    List<Patient> findByBirthDateBetween(LocalDate startDate,LocalDate endDate);

//    List<Patient> findByNameContaining(String query);
    List<Patient> findByNameContainingOrderByIdDesc(String query);

    //entity name used directly
    @Query("SELECT p FROM Patient p WHERE p.bloodGroup=?1")
    List<Patient> findByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroup);

    //using parameter injection
    @Query("SELECT p FROM Patient p WHERE p.birthDate>:birthDate")
    List<Patient> findByBornAfterDate(@Param("birthDate") LocalDate birthDate);

    @Query("select new com.jars.HospitalManagement.entity.dto.BloodGroupCountResponseEntity(p.bloodGroup,count(p)) from Patient p group by p.bloodGroup")
//    List<Object[]> countEachBloodGroupType();
    List<BloodGroupCountResponseEntity> countEachBloodGroupType();

    //Native Query
    @Query(value = "select * from patient",nativeQuery = true)
    List<Patient> findAllPatients();

    @Modifying
    @Transactional
    @Query("UPDATE Patient p SET p.name=:name where p.id=:id")
    int updateNameWithId(@Param("name") String name,@Param("id") Long id);


}
