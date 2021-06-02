<template>
  <v-card>
    <v-btn color="red" outlined plain @click="sendMess"> Messages </v-btn>
    <v-card-title>
      Teachers
      <v-spacer></v-spacer>
      <router-link to="students">
        <v-btn outlined color="blue"> Students </v-btn>
      </router-link>
      <router-link to="courses">
        <v-btn outlined color="blue"> Courses </v-btn>
      </router-link>
      <v-btn color="blue" outlined plain @click="addUser"> Add Teacher </v-btn>

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
      :items="users"
      :search="search"
      @click:row="editUser"
    ></v-data-table>
    <MessageDialog
      :opened="dialogVisible1"
      :message="message"
      @sendMsg="sendMsg"
    ></MessageDialog>

    <UserDialog
      :opened="dialogVisible"
      :user="selectedUser"
      :showCourses="showCourses"
      @refresh="refreshList"
      @closeTheDialog="closeDialog"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
import MessageDialog from "../components/MessageDialog";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
export default {
  name: "UserList",
  components: { UserDialog, MessageDialog },
  data() {
    return {
      users: [],
      search: "",
      message: {},
      showCourses: false,
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", value: "email" },
      ],
      dialogVisible: false,
      dialogVisible1: false,
      selectedUser: {},
      connected: false,
    };
  },
  methods: {
    sendMess() {
      this.connect();
      this.dialogVisible1 = true;
    },
    sendMsg() {
      this.send(this.message.messageContent);
      this.dialogVisible1 = false;
      this.disconnect();
      this.message = {};
    },

    send(msg) {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send(
          "/app/mes",
          JSON.stringify({ messageContent: msg }),
          {}
        );
      }
    },
    connect() {
      console.log("dadada");
      this.socket = new SockJS("http://localhost:8088/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        { "Access-Control-Allow-Origin": "*" },
        (frame) => {
          this.connected = true;
          console.log("Connected" + frame);
        },
        (error) => {
          console.log(error);
          this.connected = false;
        }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },

    addUser() {
      this.dialogVisible = true;
    },
    editUser(user) {
      this.selectedUser = user;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      (this.showCourses = false), (this.selectedUser = {});
      this.users = await api.users.allTeachers();
    },
    closeDialog() {
      this.dialogVisible = false;
      this.selectedUser = {};
    },
  },
  created() {
    this.refreshList();
    this.connect();
  },
};
</script>

<style scoped></style>
