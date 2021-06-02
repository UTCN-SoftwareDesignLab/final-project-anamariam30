import authHeader, { BASE_URL, HTTP } from "../http";
import axios from "axios";
export default {


  allGradesForStudent(name) {
    return HTTP.post(BASE_URL + "/grade/names", name, { headers: authHeader() }).then(
      (response) => {
        console.log(response.data)
        return response.data;
      }
    );
  },

  create(grade) {

    console.log(grade)
    return HTTP.post(BASE_URL + "/grade", grade, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  download(id) {
    axios({
      url: 'http://localhost:8088/api/grade/file/' + id,
      method: 'GET',
      responseType: 'blob',
    }).then((response) => {
      var fileURL = window.URL.createObjectURL(new Blob([response.data]));
      var fileLink = document.createElement('a');
      fileLink.href = fileURL;
      fileLink.setAttribute('download', 'Report.pdf');
      document.body.appendChild(fileLink);

      fileLink.click();
    });

  },
  generate(id) {
    return HTTP.get(BASE_URL + "/grade/export/" + id, { headers: authHeader() })
  },
  remove(id) {
    return HTTP.delete(BASE_URL + "/grade/" + id, { headers: authHeader() });
  },
};
