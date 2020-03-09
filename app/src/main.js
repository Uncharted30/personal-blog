import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import './plugins/element.js'
import './assets/fonts/font.css'
import store from './store'

Vue.config.productionTip = false;
Vue.prototype.$axios = axios;

axios.interceptors.request.use(
    config => {
      const token = window.sessionStorage.getItem('token');
      if (token) {
        config.headers.Authorization = token;
      }
      return config;
    },
    error => Promise.reject(error)
);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
