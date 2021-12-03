import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import VueI18n from 'vue-i18n';
import { messages } from './components/common/i18n';
import 'element-ui/lib/theme-chalk/index.css';
// import './assets/css/theme-green/index.css';
import './assets/css/icon.css';
import './components/common/directives';
import 'babel-polyfill';

Vue.config.productionTip = false;
Vue.use(VueI18n);
Vue.use(ElementUI, {
    size: 'small'
});
const i18n = new VueI18n({
    locale: 'en',
    messages
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | library-manament-system`;
    const role = localStorage.getItem("ms_username");
    if (!role && to.path !== '/login' && to.path !== '/register') {
        next('/login');
    } else if (to.meta.permission) {
        role === 'admin' ? next() : next('/403');
    } else {
        if (navigator.userAgent.indexOf('MSIE') > -1 && to.path === '/editor') {
            Vue.prototype.$alert('not support ie10 below', 'error', {
                confirmButtonText: 'confirm'
            });
        } else {
            next();
        }
    }
});

new Vue({
    router,
    i18n,
    render: h => h(App)
}).$mount('#app');
