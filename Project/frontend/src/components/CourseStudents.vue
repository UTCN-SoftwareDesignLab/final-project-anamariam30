
<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Students </v-toolbar>

        <v-form>
          <label>Enrolled</label>
          <v-autocomplete
            v-model="selectedStudent.name"
            label="Students  Enrolled"
            solo
            :items="studentsEnrolled"
          ></v-autocomplete>
        </v-form>
        <v-form>
          <label>Not Enrolled</label>

          <v-autocomplete
            v-model="selectedStudent.name"
            label="Students not Enrolled"
            solo
            :items="students"
          ></v-autocomplete>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist"> Add Student </v-btn>

          <v-btn @click="removes"> Remove Student </v-btn>

          <v-btn @click="close"> Close </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "CourseStudents",
  props: {
    students: Array,
    studentsEnrolled: Array,
    course: Object,
    studentCourse: Object,
    opened: Boolean,
  },
  data() {
    return {
      dialogVisible: false,
      selectedStudent: {},
    };
  },
  methods: {
    close() {
      console.log("a plecat");
      this.$emit("close");
    },
    persist() {
      api.courses
        .addStudentToCourse({
          course: this.course.title,
          student: this.selectedStudent.name,
        })
        .then(() => this.$emit("refresh"));
    },
    removes() {
      api.courses
        .removeStudentFromCourse({
          course: this.course.title,
          student: this.selectedStudent.name,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
