import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import "material-design-icons-iconfont/dist/material-design-icons.css";
import "./api";
import store from "./store";
import VueSimpleAlert from "vue-simple-alert";

Vue.config.productionTip = false;

new Vue({
  router,
  vuetify,
  store,
  VueSimpleAlert,
  render: (h) => h(App),
}).$mount("#app");
