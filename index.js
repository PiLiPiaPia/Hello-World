import Vue from 'vue';
import Router from 'vue-router';
import Hello from '@/components/Hello';
import registMain from '../components/regist/registMain.vue';
import registMain1 from '../components/regist/registMain1.vue';
import registSuccess from '../components/regist/registSuccess.vue';
import loginFin from '../components/login/loginFin.vue';
import loginFac from '../components/login/loginFac.vue';
import customerOriented from '../components/homepage/customerOriented.vue';
import network from '../components/homepage/network.vue';
import fit from '../components/homepage/fit.vue';
import efficiency from '../components/homepage/efficiency.vue';
Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/regist',
      name: 'registMain',
      component: registMain
    },
    {
      path: '/success',
      name: 'registSuccess',
      component: registSuccess
    },
    {
      path: '/loginfin',
      name: 'loginFin',
      component: loginFin
    },
    {
      path: '/loginfac',
      name: 'loginfac',
      component: loginFac
    },
    {
      path: '/registMain1',
      name: 'loginfac1',
      component: registMain1
    },
    {
      path: '/co',
      name: 'customerOriented',
      component: customerOriented
    },
    {
      path: '/network',
      name: 'network',
      component: network
    },
    {
      path: '/fit',
      name: 'fit',
      component: fit
    },
    {
      path: '/efficiency',
      name: 'efficiency',
      component: efficiency
    }
  ]
});
