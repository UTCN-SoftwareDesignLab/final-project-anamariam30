import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/user", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  allTeachers() {
    return HTTP.get(BASE_URL + "/user/teacher", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(user) {
    return HTTP.patch(BASE_URL + "/user", user, { headers: authHeader() }).then(
      (response) => {
        console.log(response.data)
        return response.data;
      }
    );
  },
  create(user) {
    return HTTP.post(BASE_URL + "/user", user, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  remove(id) {
    return HTTP.delete(BASE_URL + "/user/" + id, { headers: authHeader() }).then(
      console.log("status "),
      (response) => {

        if (response.status == 202)
          return response.data;
        else
          alert("Can't delete teacher!")

      }
    );
  },
};
