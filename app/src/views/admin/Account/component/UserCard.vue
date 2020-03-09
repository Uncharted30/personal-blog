<template>
    <el-card style="margin-bottom:20px;" v-if="userInfo !== null">
        <div slot="header" class="clearfix" v-if="admin">
            <span>About me</span>
        </div>

        <div class="user-profile">
            <div class="box-center">
                <pan-thumb v-if="avatar" :image="'api' + avatar" :height="'100px'" :width="'100px'" :hoverable="false">
                    <el-button @click="dialogVisible = true" size="mini" style="margin: 17px 0 0 -10px;" v-if="admin">Change
                    </el-button>
                </pan-thumb>
            </div>
            <div class="box-center">
                <div class="user-name text-center">{{ userInfo.nickname }}</div>
            </div>
        </div>


        <div class="user-bio">
            <div class="user-skills user-bio-section">
                <div class="user-bio-section-header">
                    <span>Skills</span></div>
                <div class="user-bio-section-body">
                    <div class="progress-item">
                        <span>Java</span>
                        <el-progress :percentage="60"/>
                    </div>
                    <div class="progress-item">
                        <span>C++</span>
                        <el-progress :percentage="40"/>
                    </div>
                    <div class="progress-item">
                        <span>Javascript</span>
                        <el-progress :percentage="40"/>
                    </div>
                    <div class="progress-item">
                        <span>HTML/CSS</span>
                        <el-progress :percentage="20"/>
                    </div>
                </div>
            </div>
        </div>
        <el-dialog
                title="Avatar Upload"
                :visible.sync="dialogVisible"
                width="30%"
                :show-close="false">
            <el-upload
                    class="avatar-uploader"
                    action="api/upload/avatars"
                    :show-file-list="false"
                    :on-success="onSuccess"
                    :before-upload="beforeUpload">
                <img v-if="avatar" :src="'api' + newAvatar" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <span slot="footer" class="dialog-footer">
            <el-button @click="cancel">Cancel</el-button>
            <el-button type="primary" @click="upload">Upload</el-button>
          </span>
        </el-dialog>
    </el-card>
</template>

<script>
    import PanThumb from '@/components/PanThumb'

    export default {
        components: {
            PanThumb,
        },
        created() {
            setTimeout(() => {
                this.avatar = this.userInfo.avatarUrl;
                this.newAvatar = this.avatar;
            }, 1000)
        },
        data() {
            return {
                status: false,
                avatar: null,
                newAvatar: null,
                dialogVisible: false,
                key: 1
            }
        },
        methods: {
            onSuccess(response) {
                this.newAvatar = response.data;
            },
            beforeUpload(file) {
                if (file.type === 'image/png' || file.type === 'image/jpeg' || file.type === 'image/gif') {
                    if (file.size / 1024 / 1024 > 2) {
                        this.$message.error('Size of the file exceeds 2MB!');
                        return false;
                    }
                    return true;
                } else {
                    this.$message.error('Only support image of format jpg, png and gif. Please select image again');
                    return false;
                }
            },
            cancel() {
                if (this.newAvatar !== this.avatar) {
                    this.$axios.delete('api/admin/delete', {
                        params: {
                            fileName: this.newAvatar
                        }
                    });
                }
                this.dialogVisible = false;
            },
            upload() {
              if (this.newAvatar !== this.avatar) {
                this.avatar = this.newAvatar;
                this.$emit('setAvatar', this.newAvatar);
              }
              this.dialogVisible = false;
            }
        },
        props: ['userInfo', 'admin']
    }
</script>

<style scoped>
    .box-center {
        margin: 0 auto;
        display: table;
    }

    .text-muted {
        color: #777;
    }

    .user-profile .user-name {
        font-weight: 500;
    }

    .user-profile .box-center {
        padding-top: 10px;
    }

    .user-profile .user-role {
        padding-top: 10px;
        font-weight: 400;
        font-size: 14px;
    }

    .user-profile .box-social {
        padding-top: 30px;
    }

    .user-profile .el-table {
        border-top: 1px solid #dfe6ec;
    }

    .user-bio {
        margin-top: 20px;
        color: #606266;
    }

    .user-bio span {
        padding-left: 4px;
    }

    .user-bio-section {
        font-size: 14px;
        padding: 15px 0;
    }

    .user-bio-section-header {
        border-bottom: 1px solid #dfe6ec;
        padding-bottom: 10px;
        margin-bottom: 10px;
        font-weight: 500;
    }

    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }

    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>
