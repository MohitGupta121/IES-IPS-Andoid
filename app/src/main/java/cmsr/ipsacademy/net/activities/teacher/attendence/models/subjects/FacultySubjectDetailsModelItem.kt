package cmsr.ipsacademy.net.activities.teacher.attendence.models.subjects

data class FacultySubjectDetailsModelItem(
    val academic_session_id: String,
    val batch: String,
    val batch_id: String,
    val clg_sub_code: String,
    val department: String,
    val dept_code: String,
    val flag: String,
    val ip: String,
    val max_student: String,
    val name: String,
    val remark: Any,
    val semester: String,
    val subject_name: String,
    val time_stamp: String,
    val university_sub_code: String,
    val user_stamp: String
)