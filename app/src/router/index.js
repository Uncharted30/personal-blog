import Vue from 'vue'
import VueRouter from 'vue-router'
import AdminIndex from '@/views/admin/layout'
import Dashboard from "@/views/admin/Dashboard";
import NewPost from '@/views/admin/ManagePosts/NewPost'
import EditPost from '@/views/admin/ManagePosts/EditPost'
import PostList from '@/views/admin/ManagePosts/PostList'
import ManagePosts from '@/views/admin/ManagePosts'
import ManageBlog from '@/views/admin/ManageBlog'
import ManageCategories from '@/views/admin/ManageBlog/ManageCategories'
import ManageTags from '@/views/admin/ManageBlog/ManageTags'
import ManageBlogrolls from '@/views/admin/ManageBlog/ManageBlogrolls'
import ManageLogs from '@/views/admin/ManageLogs'
import ManageComments from '@/views/admin/ManageBlog/ManageComments'
import VisitLogs from '@/views/admin/ManageLogs/VisitLogs'
import OperationLogs from '@/views/admin/ManageLogs/OprationLogs'
import Account from '@/views/admin/Account'
import Login from '@/views/admin/Login'
import NotFound from '@/error-pages/404'
import Site from '@/views/site/layout'
import BlogList from '@/views/site/BlogList'
import Article from '@/views/site/Article'
import Archives from '@/views/site/Archives'
import About from '@/views/site/About'
import Tag from '@/views/site/Tag'
import Category from '@/views/site/Category'
import Search from '@/views/site/Search';

Vue.use(VueRouter);

const routes = [
    {
        path: '/', component: Site, redirect: '/blog', children: [
            {path: 'blog', component: BlogList},
            {path: 'blog/:id', component: Article},
            {path: 'archives', component: Archives},
            {path: 'about', component: About},
            {path: 'tag/:id', component: Tag},
            {path: 'category/:id', component: Category},
            {path: 'search/:keyword', component: Search}
        ]
    },
    {path: '/admin/login', component: Login, name: 'Login'},
    {
        path: '/admin',
        name: 'Admin',
        redirect: '/admin/dashboard',
        component: AdminIndex,
        children: [
            {
                path: '/admin/dashboard',
                component: Dashboard,
                name: 'Dashboard',
                meta: {title: 'Dashboard', noCache: true, requiresAuth: true}
            },
            {
                path: '/admin/managePosts',
                component: ManagePosts,
                name: 'My Posts',
                children: [{
                    path: 'edit/:id(\\d+)',
                    meta: {title: 'Edit Post', noCache: true, requiresAuth: true},
                    component: EditPost,
                    name: 'Edit Post',
                    hidden: true
                }, {
                    path: 'new',
                    meta: {title: 'New Post', noCache: true, requiresAuth: true},
                    component: NewPost,
                    name: 'New Post',
                    hidden: true
                }, {
                    path: 'postList',
                    meta: {title: 'Post List', noCache: true, requiresAuth: true},
                    component: PostList,
                    name: 'Post List',
                    hidden: true
                }]
            },
            {
                path: '/admin/manageBlog',
                component: ManageBlog,
                name: 'Manage Blog',
                children: [{
                    path: 'category',
                    component: ManageCategories,
                    name: 'Manage Categories',
                    meta: {title: 'Manage Categories', noCache: true, requiresAuth: true}
                }, {
                    path: 'tag',
                    component: ManageTags,
                    name: 'Manage Tags',
                    meta: {title: 'Manage Tags', noCache: true, requiresAuth: true}
                }, {
                    path: 'blogroll',
                    component: ManageBlogrolls,
                    name: 'Manage Blogrolls',
                    meta: {title: 'Manage Blogrolls', noCache: true, requiresAuth: true}
                }, {
                    path: 'comment',
                    component: ManageComments,
                    name: 'Manage Comments',
                    meta: {title: 'Manage Comments', noCache: true, requiresAuth: true}
                }]
            },
            {
                path: '/admin/manageLogs',
                component: ManageLogs,
                name: 'Manage Logs',
                meta: {title: 'Manage Logs'}, noCache: true, requiresAuth: true,
                children: [
                    {
                        path: 'visit',
                        component: VisitLogs,
                        name: 'Visit Logs',
                        meta: {title: 'Manage Logs', noCache: true, requiresAuth: true},
                    },
                    {
                        path: 'operation',
                        component: OperationLogs,
                        name: 'Operation Logs',
                        meta: {title: 'Operation Logs', noCache: true, requiresAuth: true}
                    }
                ]
            },
            {
                path: '/admin/account',
                component: Account,
                name: 'Account',
                meta: {title: 'Account', noCache: true, requiresAuth: true}
            }
        ]
    },
    {path: '/404', component: NotFound},
    {path: '*', redirect: '/404'},
];

const router = new VueRouter({
    routes
});

router.beforeEach((to, from, next) => {
    let token = window.sessionStorage.getItem('token');
    if (to.matched.some(record => record.meta.requiresAuth) && token === null) {
        next({
            path: '/admin/login',
            query: {redirect: to.fullPath}
        });
    } else {
        next();
    }
});

export default router
