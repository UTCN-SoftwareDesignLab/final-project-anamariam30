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
                <label>Student Name </label>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="24" sm="12" md="8">
                <v-autocomplete
                  v-model="selectedStudent"
                  label="Student"
                  solo
                  :items="students"
                ></v-autocomplete>
              </v-col>
            </v-row>
            <v-row>
              <v-text-field v-model="grade.data" label="Date" />
            </v-row>
            <v-row>
              <v-col cols="24" sm="12" md="8">
                <v-autocomplete
                  v-model="grade.grade"
                  label="Grade"
                  solo
                  :items="[4, 5, 6, 7, 8, 9, 10]"
                ></v-autocomplete>
              </v-col>
            </v-row>

            <v-row v-show="show">
              <v-col cols="24" sm="12" md="8">
                <v-autocomplete
                  v-model="selectedGrade"
                  label="Grades"
                  solo
                  item-text="grade"
                  item-value="id"
                  :items="grades"
                ></v-autocomplete>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-btn @click="persist"> Add </v-btn>
          <v-btn @click="seeGrades"> Student Grades </v-btn>
          <v-btn @click="disabled"> Close Grades </v-btn>
          <v-btn @click="deleteGrade">Delete Grade</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "GradeDialog",
  props: {
    course: Object,
    opened: Boolean,
    students: Array,
    show: Boolean,
  },
  data() {
    return {
      grade: {},
      grades: [],
      dialogVisible: false,
      selectedStudent: {},
      selectedGrade: {},
    };
  },
  methods: {
    disabled() {
      (this.grades = []), this.$emit("refresh");
    },
    persist() {
      this.grades = [];
      var dates = new String(this.grade.data + "T" + "00:00");
      api.grade.create({
        course: this.course.title,
        student: this.selectedStudent,
        grade: this.grade.grade,
        data: dates,
      });
    },

    async seeGrades() {
      this.grades = await api.grade.allGradesForStudent({
        course: this.course.title,
        student: this.selectedStudent,
      });
      this.show = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedStudent = {};
      this.grades = [];
      this.show = false;
      this.students = await api.students.allStudentsEnrolled(this.course.id);
    },
    deleteGrade() {
      this.connect();
    },
  },
};
</script>

<style scoped></style>
