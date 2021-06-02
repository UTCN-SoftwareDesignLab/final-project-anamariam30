import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allCourses() {

    return HTTP.get(BASE_URL + "/courses", { headers: authHeader() }).then(
      (response) => {
        console.log(response.data)
        return response.data;
      }
    );
  },

  allTeacherCourses(id) {
    console.log(id)
    return HTTP.get(BASE_URL + "/courses/fetch/" + id, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  allStudentCourses(id) {
    console.log(id)
    return HTTP.get(BASE_URL + "/courses/fetchCourses/" + id, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(course) {
    return HTTP.post(BASE_URL + "/courses", course, { headers: authHeader() }).then(
      console.log("status "),
      (response) => {

        if (response.status == 202) {
          return response.data;
        }
        else
          alert("Teacher is not available")

      }
    );

  },
  edit(course) {

    return HTTP.patch(BASE_URL + "/courses", course, { headers: authHeader() }).then(
      console.log("status "),
      (response) => {
        if (response.status == 202) {
          return response.data;
        }
        else
          alert("Teacher is not available")

      }
    );
  },
  remove(id) {
    return HTTP.delete(BASE_URL + "/courses/" + id, { headers: authHeader() }).then(
      console.log("status "),
      (response) => {

        if (response.status == 202)
          return response.data;
        else
          alert("Can't delete course! Students are enrolled")

      }
    );
  },
  addStudentToCourse(courseStudent) {
    return HTTP.post(BASE_URL + "/courses/adds", courseStudent, { headers: authHeader() }).then(
      console.log("status "),
      (response) => {

        if (response.status == 202)
          return response.data;
        else
          alert("Student is not available!")

      }
    );
  },
  removeStudentFromCourse(courseStudent) {
    console.log(courseStudent)
    return HTTP.post(BASE_URL + "/courses/remove", courseStudent, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },


};
