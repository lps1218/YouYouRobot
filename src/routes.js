import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'

import Form from './views/manage/Form.vue'
//管理:教师、课程、机构
import Course from './views/manage/Course.vue'
import Organization from './views/manage/Organization.vue'
import Teacher from './views/manage/Teacher.vue'
//订单
import Order from './views/order/Order.vue'
//活动
import Poster from './views/activity/Poster.vue'
import Video from './views/activity/Video.vue'
import Image from './views/activity/Image.vue'
//报表
import echarts from './views/charts/echarts.vue'

let routes = [
	
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: Home,
        name: '',
        hidden: true
    },
    //{ path: '/main', component: Main },
    {
        path: '/',
        component: Home,
        name: '信息管理',
        iconCls: 'el-icon-message',//图标样式class
        children: [
            { path: '/main', component: Main, name: '主页', hidden: true },
            { path: '/teacher', component: Teacher, name: '教师' },
			{ path: '/course', component: Course, name: '课程' },
			{ path: '/organization', component: Organization, name: '机构' },

        ]
    },
    {
        path: '/',
        component: Home,
        name: '订单管理',
        iconCls: 'fa fa-id-card-o',
        children: [
            { path: '/order', component: Order, name: '订单' }
        ]
    },
	{
	    path: '/',
	    component: Home,
	    name: '上传管理',
	    iconCls: 'fa fa-address-card',
	    //leaf: true,//只有一个节点
		children: [
		    { path: '/video', component: Video, name: '视频资源' },
		    { path: '/poster', component: Poster, name: '海报资源' },
			{ path: '/image', component: Image, name: '海报资源' }
		]
	},
    {
        path: '/',
        component: Home,
        name: 'Charts',
        iconCls: 'fa fa-bar-chart',
        children: [
            { path: '/echarts', component: echarts, name: 'echarts' }
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;