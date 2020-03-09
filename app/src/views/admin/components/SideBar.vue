<template>
    <el-menu class="el-menu-vertical-demo" :default-openeds="[]" :default-active="active"
             background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" :collapse="isCollapsed">
        <router-link to="/admin/dashboard">
            <menu-item index="1" icon="el-icon-menu" title="Dashboard"></menu-item>
        </router-link>
        <el-submenu index="2">
            <template slot="title">
                <i class="el-icon-edit-outline icon"></i>
                <span slot="title">Manage Posts</span>
            </template>
            <router-link to="/admin/managePosts/new">
                <menu-item index="2-1" icon="el-icon-plus" title="New Post"></menu-item>
            </router-link>
            <router-link to="/admin/managePosts/postList">
                <menu-item index="2-2" icon="el-icon-document" title="My Posts"></menu-item>
            </router-link>
        </el-submenu>
        <el-submenu index="3">
            <template slot="title">
                <i class="el-icon-setting icon"></i>
                <span slot="title">Manage Blog</span>
            </template>
            <router-link to="/admin/manageBlog/category">
                <menu-item index="3-1" icon="el-icon-files" title="Manage Categories"></menu-item>
            </router-link>
            <router-link to="/admin/manageBlog/tag">
                <menu-item index="3-2" icon="el-icon-price-tag" title="Manage Tags"></menu-item>
            </router-link>
            <router-link to="/admin/manageBlog/blogroll">
                <menu-item index="3-3" icon="el-icon-postcard" title="Manage Blogrolls"></menu-item>
            </router-link>
            <router-link to="/admin/manageBlog/comment">
                <menu-item index="3-4" icon="el-icon-chat-dot-square" title="Manage Comments"></menu-item>
            </router-link>
        </el-submenu>
        <el-submenu index="4">
            <template slot="title">
                <i class="el-icon-monitor icon"></i>
                <span slot="title">Manage Logs</span>
            </template>
            <router-link to="/admin/manageLogs/visit">
                <menu-item index="4-1" icon="el-icon-s-order" title="Visit Logs"></menu-item>
            </router-link>
            <router-link to="/admin/manageLogs/operation">
                <menu-item index="4-2" icon="el-icon-s-data" title="Operation Logs"></menu-item>
            </router-link>
        </el-submenu>
        <menu-item index="5" icon="el-icon-guide" title="Api Documents"></menu-item>
        <menu-item index="6" icon="el-icon-link" title="Github"></menu-item>
    </el-menu>
</template>

<script>
    import menuItem from "./MenuItem"

    export default {
        name: "SideBar",
        components: {
            menuItem
        },
        created() {
            switch (this.$route.name) {
                case 'Dashboard':
                    this.active = '1';
                    break;
                case 'New Post':
                    this.active = '2-1';
                    break;
                case 'Edit Post':
                    this.active = '2-2';
                    break;
                case 'Post List':
                    this.active = '2-2';
                    break;
                case 'Manage Categories':
                    this.active = '3-1';
                    break;
                case 'Manage Tags':
                    this.active = '3-2';
                    break;
                case 'Manage Blogrolls':
                    this.active = '3-3';
                    break;
                case 'Manage Comments':
                    this.active = '3-4';
                    break;
                case 'Visit Logs':
                    this.active = '4-1';
                    break;
                case 'Operation Logs':
                    this.active = '4-2';
                    break;
                default:
                    this.active = '1';
            }
        },
        data() {
            return {
                isCollapsed: false,
                active: '1',
            }
        },
        methods: {
            toggle() {
                this.isCollapsed = !this.isCollapsed;
            }
        }
    }
</script>

<style scoped>
    .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 230px;
        min-height: 400px;
    }

    .el-menu-vertical-demo {
        border: 0;
    }

    li {
        text-align: left;
        /*width: 230px;*/
        color: white;
    }

    .el-menu {
        height: 100%;
        padding-top: 30%;
        padding-right: 0;
    }

    .icon {
        color: white;
        padding-bottom: 4px;
    }

    .el-submenu .el-menu-item {
        background-color: #4a5359 !important;
    }

    .sub-menu-item:hover {
        background-color: #3c4247 !important;
    }

    .el-main {
        padding: 0;
    }

    a:visited {
        text-decoration: none;
    }

    a {
        text-decoration: none;
    }
</style>