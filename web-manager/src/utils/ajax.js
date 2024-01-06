import Vue from "vue";
import axios from "axios";

const ajax = axios.create({
  baseURL: 'http://localhost:8000'
});

Vue.prototype.$ajax = ajax;

