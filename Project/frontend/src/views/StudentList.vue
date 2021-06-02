<template>
  <v-card>
    <v-card-title>
      Students
      <v-spacer></v-spacer>
      <router-link to="users">
        <v-btn outlined color="blue"> Teachers </v-btn>
      </router-link>
      <router-link to="courses">
        <v-btn outlined color="blue"> Courses </v-btn>
      </router-link>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>

      <v-btn @click="addStudent">Add Student</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="students"
      :search="search"
      @click:row="editStudent"
    ></v-data-table>
    <StudentDialog
      :opened="dialogVisible"
      :student="selectedStudent"
      :showCourses="showCourses"
      @close="refreshList"
      @refresh="refreshList"
    ></StudentDialog>
  </v-card>
</template>

<script>
import api from "../api";
import StudentDialog from "../components/StudentDialog";

export default {
  name: "StudentList",
  components: { StudentDialog },
  data() {
    return {
      students: [],
      search: "",
      showCourses: false,
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", value: "emailAddress" },
      ],
      dialogVisible: false,
      selectedStudent: {},
    };
  },
  methods: {
    editStudent(student) {
      this.selectedStudent = student;
      this.dialogVisible = true;
    },
    addStudent() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.showCourses = false;
      this.selectedStudent = {};
      this.students = await api.students.allStudents();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
