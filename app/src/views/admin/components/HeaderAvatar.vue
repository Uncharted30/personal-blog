<template>
    <el-dropdown trigger="click">
        <span class="el-dropdown-link">
            <el-avatar shape="square" :size="35" :src="avatarUrl"></el-avatar>
            <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
            <router-link to="/admin/account"><el-dropdown-item>Account Settings</el-dropdown-item></router-link>
            <router-link to="/"><el-dropdown-item>Home</el-dropdown-item></router-link>
            <a @click.prevent="handleLogout"><el-dropdown-item divided>Log out</el-dropdown-item></a>
        </el-dropdown-menu>
    </el-dropdown>
</template>

<script>
    export default {
        name: "HeaderAvatar",
        data() {
            return {
                avatarUrl: ''
            }
        },
        created() {
          this.getAvatar();
        },
        methods: {
            getAvatar() {
                this.$axios.get('/api/user').then(response => {
                    if (response.data.code === 200) {
                        this.avatarUrl = 'api' + response.data.data.avatarUrl;
                    } else {
                        this.$message.error('Failed to get user info, please refresh and try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get user info, please refresh and try again. ' + e);
                })
            },
            handleLogout() {
                this.$axios.get('api/admin/logout').then(response => {
                    if (response.data.code === 200) {
                        window.sessionStorage.removeItem('token');
                        this.$message.success('Success!');
                        this.$router.replace('/admin/login');
                    } else {
                        this.$message.error('Failed to logout, please try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to logout, please try again. ' + e);
                });
            }
        }
    }
</script>

<style scoped>
    .el-dropdown-link:focus {
        outline: 0;
    }

    a {
        text-decoration: none;
    }

</style>