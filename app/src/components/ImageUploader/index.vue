<template>
    <div id="app-container">
        <el-upload
                :class="{hide:hideUpload}"
                ref="uploader"
                :limit="1"
                :multiple="false"
                action="api/upload/covers"
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :on-remove="onRemove"
                :on-change="onChange"
                :before-upload="beforeUpload"
                :on-success="onSuccess"
                :on-error="onError"
                :file-list="list">
            <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "index",
        methods: {
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },
            onChange(file, fileList) {
                this.hideUpload = fileList.length >= this.limitCount;
                this.$emit('onChange')
            },
            onRemove(file, fileList) {
                this.hideUpload = fileList.length >= this.limitCount
                this.$axios.delete('api/admin/delete', {
                    params: {
                        fileName: this.imgUrl
                    }
                });
                this.$emit('setImgUrl', '');
            },
            onSuccess(response) {
                let imgUrl = response.data;
                this.$emit('setImgUrl', imgUrl);
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
            onError() {
                this.$message.error('Failed to upload the image, please try again.')
            },
            setImage(imgUrl) {
                let item = {
                    name: imgUrl,
                    url: 'api' + imgUrl
                };
                this.list.push(item);
                this.hideUpload = true;
            }
        },
        data() {
            return {
                dialogImageUrl: '',
                dialogVisible: false,
                hideUpload: false,
                limitCount: 1,
                list: []
            }
        },
        props: ['imgUrl']
    }
</script>

<style scoped>

</style>

<style>
    .hide .el-upload--picture-card {
        display: none;
    }
</style>