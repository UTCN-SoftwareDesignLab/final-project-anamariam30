
<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Add student" : "Edit student" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="student.name" label="Name" />
          <v-text-field v-model="student.parentName" label="Parent" />
          <v-text-field v-model="student.address" label="Address" />
          <v-text-field v-model="student.phoneNo" label="Phone" />
          <v-text-field v-model="student.emailAddress" label="Email" />
        </v-form>
        <ul v-show="showCourses">
          <li v-for="course in courses" v-bind:key="course.id">
            {{ course.title }} - {{ course.teacher }} : {{ course.day }} -
            {{ course.hour }}
          </li>
        </ul>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="seeGrades"> Download Grades </v-btn>
          <v-btn @click="sendMessage"> Send Email </v-btn>
          <v-btn @click="viewCourses">Courses </v-btn>
          <v-btn @click="close">Close </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "StudentDialog",
  props: {
    student: Object,
    opened: Boolean,
    showCourses: Boolean,
  },
  data() {
    return {
      courses: [],
    };
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.students
          .create({
            name: this.student.name,
            parentName: this.student.parentName,
            address: this.student.address,
            phoneNo: this.student.phoneNo,
            emailAddress: this.student.emailAddress,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.students
          .edit({
            id: this.student.id,
            name: this.student.name,
            parentName: this.student.parentName,
            address: this.student.address,
            phoneNo: this.student.phoneNo,
            emailAddress: this.student.emailAddress,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    close() {
      this.$emit("close");
    },
    async viewCourses() {
      this.courses = await api.courses.allStudentCourses(this.student.id);
      this.showCourses = true;
    },
    removes() {
      api.students.remove(this.student.id).then(() => this.$emit("refresh"));
    },
    async seeGrades() {
      await api.grade.generate(this.student.id);
      await api.grade.download(this.student.id);
    },
    sendMessage() {
      api.students.sendEmail(this.student);
    },
  },
  computed: {
    isNew: function () {
      return !this.student.id;
    },
  },
};
</script>

<style scoped></style>
