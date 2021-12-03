import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/bookSearch'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: 'library-system' },
            children: [
                {
                    path: '/bookSearch',
                    component: () => import(/* webpackChunkName: "result" */ '../components/page/BookSearch.vue'),
                    meta: { title: 'Book Search' }
                },
				{
				    path: '/personalInformation',
				    component: () => import(/* webpackChunkName: "result" */ '../components/page/personalInformation.vue'),
				    meta: { title: 'Personal Information' }
				},
				{
				    path: '/bookReturn',
				    component: () => import(/* webpackChunkName: "result" */ '../components/page/BookReturn.vue'),
				    meta: { title: 'Book Return' }
				},
                {
                    path: '/404',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/404.vue'),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: () => import(/* webpackChunkName: "403" */ '../components/page/403.vue'),
                    meta: { title: '403' }
                }
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});
