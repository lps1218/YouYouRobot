import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
//import './assets/theme/theme-green/index.css'
import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
//import NProgress from 'nprogress'
//import 'nprogress/nprogress.css'
import routes from './routes'
import Mock from './mock'
Mock.bootstrap();
import 'font-awesome/css/font-awesome.min.css'
import VueResource from 'vue-resource'
// 引入登录滑块验证插件
import SlideVerify from 'vue-monoplasty-slide-verify';
//引入video视频播放
import Video from 'video.js'
import 'video.js/dist/video-js.css'
import VueDirectiveImagePreviewer from 'vue-directive-image-previewer'
import 'vue-directive-image-previewer/dist/assets/style.css'
import VueVideoPlayer from 'vue-video-player';
import 'video.js/dist/video-js.css';
Vue.use(VueVideoPlayer);
Vue.use(VueDirectiveImagePreviewer)
Vue.use(SlideVerify)
Vue.use(VueResource);
Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)
//NProgress.configure({ showSpinner: false });

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  //NProgress.start();
  if (to.path == '/login') {
    sessionStorage.removeItem('user');
  }
  let user = JSON.parse(sessionStorage.getItem('user'));
  if (!user && to.path != '/login') {
    next({ path: '/login' })
  } else {
    next()
  }
})

//router.afterEach(transition => {
//NProgress.done();
//});

new Vue({
  /* el: '#app',
  template: '<App/>', */
  router,
  store,
  //components: { App }
  render: h => h(App)
}).$mount('#app')

