package com.cn.studentportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cn.studentportal.dto.SpecializationDto;
import com.cn.studentportal.entity.Specialization;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {
	
	Specialization getBySpecializationName(String name);
	
	Specialization getBySpecializationNameAndStreamId(String name, Integer streamId);

	
	@Query("""
	select new com.cn.studentportal.dto.SpecializationDto(
	sp.specializationId, sp.specializationName, 
    sp.streamId, sp.cmnStatusId, st.streamName
    )
	from Specialization sp
	left join Stream st on sp.streamId = st.streamId
     """)
	List<SpecializationDto> findAllDtos();

	@Query("""
			select new com.cn.studentportal.dto.SpecializationDto(
			sp.specializationId, sp.specializationName, 
		    sp.streamId, sp.cmnStatusId, st.streamName
		    )
			from Specialization sp
			left join Stream st on sp.streamId = st.streamId
			where sp.streamId = ?1 and sp.cmnStatusId='A'
		     """)
	List<SpecializationDto> findAllByStreamId( Integer streamId);
}
