<template>
    <el-container id="container">
        <el-scrollbar id="aside">
            <side-bar ref="sideBar"></side-bar>
        </el-scrollbar>
        <el-container>
            <el-header>
                <admin-header @toggleSideBar="toggleSideBar" :currentRoute="currentRoute"></admin-header>
            </el-header>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import adminHeader from "@/views/admin/components/AdminHeader";
    import sideBar from "@/views/admin/components/SideBar";

    export default {
        name: "index",
        components: {
            adminHeader,
            sideBar
        },
        data() {
            return {
                currentRoute: null,
            }
        },
        updated() {
            this.getCurrentRoute()
        },
        created() {
          this.getCurrentRoute()
        },
        methods: {
            toggleSideBar() {
                this.$refs.sideBar.toggle();
            },
            getCurrentRoute() {
                let currentRoute = this.$route.name;
                currentRoute = currentRoute.replace("/#", "");
                this.currentRoute = currentRoute;
            }
        },
    }
</script>

<style scoped>
    .el-header {
        width: 100%;
        padding: 0;
    }

    .el-scrollbar {
        display: block;
        position: relative;
        overflow-y: scroll;
        overflow-x: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        background-color: #545c64;
    }
    
    .el-main {
        background-color: #eef0f3;
    }
</style>