<template>
    <div>
        <welcome-card :generalStats="generalStats"></welcome-card>
        <br>
        <el-row type="flex" justify="space-between">
            <el-col :span="12" id="recent-card">
                <div class="grid-content">
                    <recent-card></recent-card>
                </div>
            </el-col>
            <el-col :span="12" id="version-info">
                <div class="grid-content">
                    <version-info-card :generalStats="generalStats"></version-info-card>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import WelcomeCard from "@/views/admin/Dashboard/components/WelcomeCard";
    import RecentCard from "@/views/admin/Dashboard/components/RecentCard";
    import VersionInfoCard from "@/views/admin/Dashboard/components/VersionInfoCard";

    export default {
        name: "Dashboard",
        created() {
            this.getGeneralStats();
        },
        components: {
            WelcomeCard,
            RecentCard,
            VersionInfoCard
        },
        data() {
          return {
              generalStats: {
                  todayVisits: null,
                  posts: null,
                  comments: null,
                  lastLogin: null,
                  nickname: null,
                  email: null,
                  version: null,
              }
          }
        },
        methods: {
            getGeneralStats() {
                this.$axios.get('api/admin/stats/general').then(response => {
                    if (response.data.code === 200) {
                        this.generalStats = response.data.data;
                    } else {
                        this.$message.error("Error getting general stats, please refresh the page and try again. " + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error("Error getting general stats, please refresh the page and try again. " + e);
                })
            }
        }
    }
</script>

<style scoped>
    #recent-card {
        margin-right: 10px;
    }

    #version-info {
        margin-left: 10px;
    }
</style>