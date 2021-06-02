<template>
  <v-card>
    <v-card-title>
      Courses
      <v-spacer></v-spacer>
      <router-link to="users">
        <v-btn outlined color="blue"> Teachers </v-btn>
      </router-link>
      <router-link to="students">
        <v-btn outlined color="blue"> Students </v-btn>
      </router-link>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addCourse">Add Course</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="courses"
      :search="search"
      @click:row="editCourse"
    ></v-data-table>
    <CourseDialog
      :opened="dialogVisible"
      :course="selectedCourse"
      :teachers="teachers"
      :students="students"
      :studentsEnrolled="studentsEnrolled"
      @close="close"
      @openDetails="openTheCourseStudentDialog"
      @refresh="refreshList"
    ></CourseDialog>
    <CourseStudents
      :opened="dialogVisible2"
      :students="students"
      @close="close"
      :studentsEnrolled="studentsEnrolled"
      :course="this.selectedCourse"
      @refresh="refreshList1"
    >
    </CourseStudents>
  </v-card>
</template>

<script>
import api from "../api";
import CourseDialog from "../components/CourseDialog";
import CourseStudents from "../components/CourseStudents";

export default {
  name: "CourseList",
  components: { CourseDialog, CourseStudents },
  data() {
    return {
      courses: [],
      teachers: [],
      studentsEnrolled: [],
      students: [],
      search: "",
      headers: [
        {
          text: "Course",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Teacher", value: "teacher" },
        { text: "Day", value: "day" },
        { text: "Hour", value: "hour" },
      ],
      dialogVisible: false,
      dialogVisible2: false,

      selectedCourse: {},
    };
  },
  methods: {
    editCourse(course) {
      this.selectedCourse = course;
      console.log(this.selectedCourse.id);
      this.dialogVisible = true;
    },
    async openTheCourseStudentDialog() {
      this.dialogVisible = false;
      this.dialogVisible2 = true;

      this.students = await api.students.allStudentsNotEnrolled(
        this.selectedCourse.id
      );
      this.studentsEnrolled = await api.students.allStudentsEnrolled(
        this.selectedCourse.id
      );
    },
    addCourse() {
      console.log(this.$store.state.auth.user.id);
      this.dialogVisible = true;
    },

    close() {
      this.dialogVisible = false;
      this.dialogVisible2 = false;
    },
    async refreshList() {
      this.dialogVisible2 = false;
      this.courses = await api.courses.allCourses();
      this.teachers = await api.users.allTeachers();
      this.selectedCourse = {};
    },

    async refreshList1() {
      this.dialogVisible2 = false;
      this.students = await api.students.allStudentsNotEnrolled(
        this.selectedCourse.id
      );
      this.studentsEnrolled = await api.students.allStudentsEnrolled(
        this.selectedCourse.id
      );
      this.selectedCourse = {};
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
