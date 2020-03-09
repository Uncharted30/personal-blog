<template>
    <div class="login-container">
        <el-form ref="loginForm" :model="loginForm" :rules="rules" class="login-form" autocomplete="on"
                 label-position="left">

            <div class="title-container">
                <h3 class="title">Admin login</h3>
            </div>

            <el-form-item prop="username">
                <div class="icon-container">
                    <i class="el-icon-user"></i>
                </div>
                <el-input
                        ref="username"
                        v-model="loginForm.username"
                        placeholder="Username"
                        name="username"
                        type="text"
                        tabindex="1"
                        autocomplete="on"
                        prop="username"
                />
            </el-form-item>

            <el-tooltip v-model="capsTooltip" content="Caps lock is On" placement="right" manual>
                <el-form-item prop="password">
                        <span class="icon-container">
                            <i class="el-icon-key"></i>
                        </span>
                    <el-input
                            :key="passwordType"
                            ref="password"
                            v-model="loginForm.password"
                            :type="passwordType"
                            placeholder="Password"
                            name="password"
                            tabindex="2"
                            autocomplete="on"
                            @keyup.native="checkCapsLock"
                            @blur="capsTooltip = false"
                            @keyup.enter.native="handleLogin"
                            prop="password"
                    />
                </el-form-item>
            </el-tooltip>
            <el-form-item>
                <el-button type="primary" style="width:100%;" @click.native.prevent="handleLogin">
                    Login
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import Validator from "@/utils/Validator";

    export default {
        name: "index",
        methods: {
            checkCapsLock(e) {
                const {key} = e;
                this.capsTooltip = key && key.length === 1 && (key >= 'A' && key <= 'Z')
            },
            handleLogin() {
                this.$axios.post('api/admin/login', this.loginForm).then(response => {
                    if (response.data.code === 200) {
                        window.sessionStorage.setItem('token', response.data.data.token);
                        this.$router.replace('/admin/dashboard');
                    } else {
                        this.$message.error('Login failed for user, please try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Login failed for user, please try again. ' + e);
                })
            }
        },
        data() {
            return {
                capsTooltip: false,
                passwordType: 'password',
                rules: Validator.rules,
                loginForm: {
                    username: '',
                    password: '',
                }
            }
        }
    }
</script>

<style scoped>
    .login-form {
        width: 400px;
        max-width: 100%;
        padding: 35px 35px 15px 35px;
        margin: 120px auto;
        background-color: rgba(255, 255, 255, 0.9);
        position: absolute;
        top: 10%;
        left: 0;
        right: 0;
        border-radius: 8px;
    }

    .el-input {
        display: inline-block;
        width: 85%;
    }

    .login-container {
        min-height: 100%;
        width: 100%;
        background-color: #2d3a4b;
        overflow: hidden;
        position: fixed;
        background-size: cover;
        background-image: linear-gradient( rgba(0, 0, 0, 0.35), rgba(0, 0, 0, 0.35) ),url('../../../assets/images/wallpaper.jpg');
    }

    .el-card {
        width: 40%;
        height: 20%;
    }

    .icon-container {
        padding: 5px 0 5px 10px;
        vertical-align: middle;
        width: 30px;
        display: inline-block;
    }

    .el-form-item {
        border: 1px solid rgba(255, 255, 255, 0.1);
        border-radius: 5px;
        color: #454545;
    }

    .title {
        font-size: 26px;
        color: #f72525;
        margin: 0 auto 20px auto;
        text-align: center;
        font-weight: 500;
    }

    .el-button {
        background-color: #f72525;
        border: none;
    }

    .el-button:hover {
        background-color: #f72525;
    }
</style>