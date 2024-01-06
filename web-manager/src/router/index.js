import Vue from "vue";
import VueRouter from "vue-router";
import Login from '@/views/login';
import Home from '@/views/Home';


Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
  },
  {
    path: '/home',
    name: 'Home',
    meta: {title: '首页'},
    component: Home,
    children:[
      {
        path: '/center',
        name: 'Center',
        meta: {title: '个人中心',keepAlive: true},
        component: () => import('@/views/Center.vue')
      },

      {
        path: '/attdcheck',
        name: 'AttdCheck',
        meta: {title: '考勤查看'},
        component: () => import('@/views/AttdCheck.vue')
      },
      {
        path: '/attdpublish',
        name: 'AttdPublish',
        meta: {title: '考勤发布'},
        component: () => import('@/views/AttdPublish.vue')
      },
      {
        path: '/teachmanage',
        name: 'TeachManage',
        meta: {title: '教务管理'},
        component: () => import('../views/TeachManage.vue'),
        children:[]
      },
      {
        path: '/teachermanage',
        name: 'TeacherManage',
        meta: {title: '教师管理'},
        component: () => import('@/views/TeacherManage.vue')
      },

      {
        path: '/subjectmanage',
        name: 'SubjectManage',
        meta: {title: '学科管理'},
        component: () => import('@/views/SubjectManage.vue')
      },
      {
        path: '/classmanage',
        name: 'ClassManage',
        meta: {title: '教师管理'},
        component: () => import('@/views/ClassManage.vue')
      },
    ]
  },


];

const router = new VueRouter({
  routes,
});


export default router


