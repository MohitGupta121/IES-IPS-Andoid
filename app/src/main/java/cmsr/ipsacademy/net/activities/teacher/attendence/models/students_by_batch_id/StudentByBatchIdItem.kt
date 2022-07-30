package cmsr.ipsacademy.net.activities.teacher.attendence.models.students_by_batch_id

data class StudentByBatchIdItem(
    val academic_session: String,
    val batch_id: String,
    val batch_name: String,
    val computer_code: String,
    val course: String,
    val enrollment_no: String,
    val home_dept: String,
    val id: String,
    val ip: String,
    val lab_group_name: String,
    val remark: String,
    val semester: String,
    val show_attendance: String,
    val student_name: String,
    val student_session: String,
    val time_stamp: String,
    val user_stamp: String
)