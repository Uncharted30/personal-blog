<template>
    <el-container>
        <el-header>
            <site-header></site-header>
        </el-header>
        <el-main id="main-content">
            <router-view :key="key"></router-view>
        </el-main>
        <el-footer>
            <site-footer></site-footer>
        </el-footer>
    </el-container>
</template>

<script>
    import SiteHeader from "@/views/site/component/SiteHeader";
    import SiteFooter from "@/views/site/component/SiteFooter";

    export default {
        name: "index",
        components: {
            SiteHeader, SiteFooter
        },
        computed: {
            key() {
                return this.$route.path + Math.random();
            }
        }
    }
</script>

<style scoped>
    @import '../../assets/styles/style.min.css';
    .el-header {
        width: 100%;
        padding: 0;
    }

    .index-page {
        padding-top: 40px;
    }

    .el-main {
        flex: 1;
        /*height: 100%;*/
        background-color: rgb(246, 246, 246);
        padding: 0;
    }


</style>