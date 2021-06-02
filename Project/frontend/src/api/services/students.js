import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allStudents() {
    return HTTP.get(BASE_URL + "/students", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(student) {
    return HTTP.post(BASE_URL + "/students", student, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  sendEmail(student, message) {
    return HTTP.post(BASE_URL + "/students/email", student, message, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(student) {
    return HTTP.patch(BASE_URL + "/students", student, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  allStudentsEnrolled(id) {
    return HTTP.get(BASE_URL + "/students/enrolled/" + id, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  allStudentsNotEnrolled(id) {
    return HTTP.get(BASE_URL + "/students/notEnrolled/" + id, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  remove(id) {
    return HTTP.delete(BASE_URL + "/students/" + id, { headers: authHeader() });
  },
};
