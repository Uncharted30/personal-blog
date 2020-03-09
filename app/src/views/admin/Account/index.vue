<template>
    <div id="app-container">
        <div id="user-card">
            <div class="grid-content">
                <user-card :userInfo="userInfo" @setAvatar="setAvatar" :admin="true"></user-card>
            </div>
        </div>
        <div id="profile">
            <el-card>
                <div slot="header" class="clear-fix">
                    <span>Edit Profile</span>
                </div>
                <div class="grid-content bg-purple-light">
                    <el-form label-width="80px" :model="userInfo" label-position="left" :rules="rules">
                        <el-form-item label="Username:" prop="content">
                            <el-input v-model="userInfo.username" disabled></el-input>
                        </el-form-item>
                        <el-form-item label="Nickname:" prop="nickname">
                            <el-input v-model="userInfo.nickname"></el-input>
                        </el-form-item>
                        <el-form-item label="Password:" prop="password">
                            <el-input v-model="userInfo.password"></el-input>
                        </el-form-item>
                        <el-form-item label="Email:" prop="email">
                            <el-input v-model="userInfo.email"></el-input>
                        </el-form-item>
                        <el-form-item label="Bio:" prop="bio">
                            <markdown-editor ref="markdownEditor" @editor="editor"
                                             :content="userInfo.bioMd"></markdown-editor>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="warning" @click="update">Update</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
    import MarkdownEditor from "@/components/Editor/MarkdownEditor";
    import UserCard from "@/views/admin/Account/component/UserCard";
    import Validator from "@/utils/Validator";

    export default {
        name: "index",
        data() {
            return {
                userInfo: {
                    // username: null,
                    // nickname: null,
                    // password: null,
                    // bio: null,
                    // bioMd: null,
                    // avatarUrl: null,
                    // email: null,
                },
                rules: Validator.rules,
            }
        },
        created() {
            this.getUserInfo();
        },
        methods: {
            editor(val) {
                this.userInfo.bioMd = val.md;
                this.userInfo.bio = val.html;
            },
            update() {
                this.userInfo.bio = this.$refs.markdownEditor.getLatestHtml();
                if (this.userInfo.bio === null || this.userInfo.bio === '') {
                    this.$message.warning('Bio cannot be empty!');
                } else {
                    console.log(this.userInfo.avatarUrl);
                    this.$axios.put('api/admin/user', this.userInfo).then(response => {
                        if (response.data.code === 200) {
                            this.$message.success('Succeed!');
                            this.$router.go(0);
                        } else {
                            this.$message.error('Failed update user info, please try again later. ' + response.data.message);
                        }
                    }).catch(e => {
                        this.$message.error('Failed update user info, please try again later. ' + e);
                    })
                }
            },
            getUserInfo() {
                this.$axios.get('/api/user').then(response => {
                    if (response.data.code === 200) {
                        this.userInfo = response.data.data;
                    } else {
                        this.$message.error('Failed to get user info, please refresh and try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get user info, please refresh and try again. ' + e);
                })
            },
            setAvatar(url) {
                this.userInfo.avatarUrl = url;
                console.log(this.userInfo.avatarUrl);
            },
        },
        components: {
            MarkdownEditor, UserCard
        }
    }
</script>

<style scoped>
    .clear-fix:before,
    .clear-fix:after {
        display: table;
        content: "";
    }

    .clear-fix:after {
        clear: both
    }

    #user-card {
        padding-right: 30px;
        width: 350px;
    }

    #profile {
        width: 100%;
    }

    #app-container {
        display: flex;
    }

</style>