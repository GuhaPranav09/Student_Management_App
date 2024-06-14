package com.cn.studentportal.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cn.studentportal.dto.DashBoardDto;
import com.cn.studentportal.dto.StudentDto;
import com.cn.studentportal.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	Student getByEmail(String email);

	@Query("""
			select new com.cn.studentportal.dto.StudentDto(
			s.stdId, s.stdName, s.yop, s.streamId , s.specializationId, s.qualPercentage, s.contactNo,
			s.email, s.referedBy,s.address, s.stdStatusId, s.amountPaid,
			st.streamName, st.cmnStatusId,
			sp.specializationName, sp.cmnStatusId,
			stds.stdStatusName,
			inn.interestedInId,
			         s.dateOfJoin
			)
			from Student s
			left join Stream st on s.streamId = st.streamId
			left join Specialization sp on s.specializationId = sp.specializationId
			left join StdStatus stds on s.stdStatusId = stds.stdStatusId
			left join InterestedIn inn on s.interestedInId = inn.interestedInId
			""")
	List<StudentDto> findAllDtos();

	@Query("""
			select new com.cn.studentportal.dto.StudentDto(
			s.stdId, s.stdName, s.yop, s.streamId , s.specializationId, s.qualPercentage, s.contactNo,
			s.email, s.referedBy,s.address, s.stdStatusId, s.amountPaid,
			st.streamName, st.cmnStatusId,
			sp.specializationName, sp.cmnStatusId,
			stds.stdStatusName,
			inn.interestedInId,
			s.dateOfJoin
			)
			from Student s
			left join Stream st on s.streamId = st.streamId
			left join Specialization sp on s.specializationId = sp.specializationId
			left join StdStatus stds on s.stdStatusId = stds.stdStatusId
			left join InterestedIn inn on s.interestedInId = inn.interestedInId
			order by s.stdId desc
			""")
	Page<StudentDto> findAllDtosByPage(Pageable pageable);

	@Query("""
			select new com.cn.studentportal.dto.StudentDto(
			s.stdId, s.stdName, s.yop, s.streamId , s.specializationId, s.qualPercentage, s.contactNo,
			s.email, s.referedBy,s.address, s.stdStatusId, s.amountPaid,
			st.streamName, st.cmnStatusId,
			sp.specializationName, sp.cmnStatusId,
			stds.stdStatusName,
			inn.interestedInId,
			s.dateOfJoin

			)
			from Student s
			left join Stream st on s.streamId = st.streamId
			left join Specialization sp on s.specializationId = sp.specializationId
     		left join StdStatus stds on s.stdStatusId = stds.stdStatusId
			left join InterestedIn inn on s.interestedInId = inn.interestedInId
			 where s.referedBy like concat('%', :referedStdName, '%')
			""")
	Page<StudentDto> getStudentsByReferedName(@Param("referedStdName") String referedStdNamecore, Pageable pageable);

	@Query("""
			SELECT new com.cn.studentportal.dto.DashBoardDto(s.stdStatusId, COUNT(s))
			FROM Student s
			GROUP BY s.stdStatusId
			""")
	List<DashBoardDto> findAllDashBoardDtos();

	@Query("""
			select new com.cn.studentportal.dto.StudentDto(
			s.stdId, s.stdName, s.yop, s.streamId , s.specializationId, s.qualPercentage, s.contactNo,
			s.email, s.referedBy,s.address, s.stdStatusId, s.amountPaid,
			st.streamName, st.cmnStatusId,
			sp.specializationName, sp.cmnStatusId,
			stds.stdStatusName,
			inn.interestedInId,
			s.dateOfJoin
			)
			from Student s
			left join Stream st on s.streamId = st.streamId
			left join Specialization sp on s.specializationId = sp.specializationId
 			left join StdStatus stds on s.stdStatusId = stds.stdStatusId
			left join InterestedIn inn on s.interestedInId = inn.interestedInId
			 where s.stdName like concat('%', :stdName, '%')
			""")
	Page<StudentDto> getStudentsByStdName(@Param("stdName") String stdName, Pageable pageable);

	@Query("SELECT new com.cn.studentportal.dto.StudentDto("
			+ "s.stdId, s.stdName, s.yop, s.streamId, s.specializationId, s.qualPercentage, s.contactNo, "
			+ "s.email, s.referedBy, s.address, s.stdStatusId, s.amountPaid, " + "st.streamName, st.cmnStatusId, "
			+ "sp.specializationName, sp.cmnStatusId, stds.stdStatusName, "
			+ "inn.interestedInId, " + "s.dateOfJoin) " + "FROM Student s "
			+ "LEFT JOIN Stream st ON s.streamId = st.streamId "
			+ "LEFT JOIN Specialization sp ON s.specializationId = sp.specializationId "
			+ "LEFT JOIN StdStatus stds ON s.stdStatusId = stds.stdStatusId "
			+ "LEFT JOIN InterestedIn inn ON s.interestedInId = inn.interestedInId "
			+ "WHERE DATE(s.dateOfJoin) >= :startDate AND DATE(s.dateOfJoin) <= :endDate")

	List<StudentDto> getStudentDetailsBetweenTheDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

 

	@Query(value = "SELECT * FROM student WHERE student.status='N'", nativeQuery = true)
	List<Student> getStudentsWhomNeedtoSendMail();

	@Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
	int getStudentsCount();
}
