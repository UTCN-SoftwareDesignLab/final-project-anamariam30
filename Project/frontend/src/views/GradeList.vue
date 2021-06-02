<template>
  <v-card>
    <v-card-title>
      Grades
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="courses"
      :search="search"
      @click:row="seeStudents"
    ></v-data-table>
    <GradeDialog
      :opened="dialogVisible"
      :course="selectedCourse"
      :students="enrolledStudents"
      @refresh="refreshList"
    ></GradeDialog>
  </v-card>
</template>

<script>
import api from "../api";
import GradeDialog from "../components/GradeDialog";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "GradeList",
  components: { GradeDialog },
  data() {
    return {
      courses: [],
      enrolledStudents: [],
      search: "",
      headers: [
        {
          text: "Course",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Day", value: "day" },
        { text: "Hour", value: "hour" },
      ],
      dialogVisible: false,
      selectedCourse: {},
      connected: false,
    };
  },
  methods: {
    connect() {
      if (this.connected == false) {
        this.socket = new SockJS("http://localhost:8088/gs-guide-websocket");
        this.stompClient = Stomp.over(this.socket);
        this.stompClient.connect(
          {},
          (frame) => {
            this.connected = true;
            console.log("Connected" + frame);
            this.stompClient.subscribe("/connect/mes", (tick) => {
              console.log(tick.body);
              alert(JSON.parse(tick.body).content);
              this.received_messages.push(JSON.parse(tick.body).content);
            });
          },
          (error) => {
            console.log(error);
            this.connected = false;
          }
        );
      }
    },
    async seeStudents(course) {
      this.selectedCourse = course;
      this.enrolledStudents = await api.students.allStudentsEnrolled(
        this.selectedCourse.id
      );
      this.dialogVisible = true;
    },

    async refreshList() {
      this.dialogVisible = false;
      this.courses = await api.courses.allTeacherCourses(
        this.$store.state.auth.user.id
      );
      this.selectedCourse = {};
    },
  },
  created() {
    this.refreshList();
    this.connect();
  },
};
</script>

<style scoped></style>
