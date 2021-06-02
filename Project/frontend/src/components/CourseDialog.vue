<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="24" sm="12" md="8">
                <v-text-field v-model="course.title" label="Course Name" />
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="24" sm="12" md="8">
                <v-autocomplete
                  v-model="course.teacher"
                  label="Teacher"
                  solo
                  item-text="name"
                  item-value="id"
                  :items="teachers"
                ></v-autocomplete>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="24" sm="12" md="8">
                <v-autocomplete
                  v-model="course.day"
                  label="Day"
                  solo
                  :items="[
                    'Monday',
                    'Thuesday',
                    'Wednesday',
                    'Tuesday',
                    'Friday',
                  ]"
                ></v-autocomplete>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="24" sm="12" md="8">
                <v-autocomplete
                  v-model="course.hour"
                  label="Hour"
                  solo
                  :items="[
                    '8:00',
                    '9:00',
                    '10:00',
                    '11:00',
                    '12:00',
                    '13:00',
                    '14:00',
                    '15:00',
                  ]"
                ></v-autocomplete>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNew" @click="removes"> Delete </v-btn>

          <v-btn @click="seeDetails"> Details </v-btn>
          <v-btn @click="close"> Close </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "CourseDialog",
  props: {
    course: Object,
    opened: Boolean,
    teachers: Array,
  },
  data() {
    return {
      dialogVisible: false,
      selectedStudent: {},
    };
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.courses
          .create({
            title: this.course.title,
            teacher: this.course.teacher,
            day: this.course.day,
            hour: this.course.hour,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.courses
          .edit({
            id: this.course.id,
            title: this.course.title,
            teacher: this.course.teacher,
            day: this.course.day,
            hour: this.course.hour,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    close() {
      this.$emit("close");
    },
    seeDetails() {
      this.$emit("openDetails");
    },
    removes() {
      api.courses.remove(this.course.id).then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.course.id;
    },
  },
};
</script>

<style scoped></style>
