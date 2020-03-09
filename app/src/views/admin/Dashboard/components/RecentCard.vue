<template>
    <el-card class="box-card">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal">
            <el-menu-item index="1" @click="recentPosts">Recent Posts</el-menu-item>
            <el-menu-item index="2" @click="recentComments">Recent Comments</el-menu-item>
            <div v-show="activeIndex === '1'" class="tables">
                <recent-posts></recent-posts>
            </div>
            <div v-show="activeIndex === '2'" class="tables">
                <recent-comments></recent-comments>
            </div>
        </el-menu>
    </el-card>
</template>

<script>
    import RecentPosts from "@/views/admin/Dashboard/components/RecentPosts";
    import RecentComments from "@/views/admin/Dashboard/components/RecentComments";

    export default {
        name: "RecentCard",
        components: {
            RecentPosts, RecentComments
        },
        data() {
            return {
                activeIndex: "1"
            }
        },
        methods: {
            recentPosts() {
                this.activeIndex = "1";
            },
            recentComments() {
                this.activeIndex = "2";
            }
        }
    }
</script>

<style scoped>
    .tables:focus {
        outline: 0;
    }
</style>