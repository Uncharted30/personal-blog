<template>
    <el-card class="box-card">
        <el-input
                placeholder="Please enter keyword..."
                v-model="blogrollSearch"
                clearable>
        </el-input>
        <el-button type="primary" icon="el-icon-search" @click="getBlogrollList">Search</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="newOrEditBlogroll(null, '', '')">New</el-button>
        <el-table
                :data="blogrollList"
                border
                style="width: 100%">
            <el-table-column
                    prop="id"
                    label="ID"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="Blogroll Name"
                    width="200">
            </el-table-column>
            <el-table-column
                    prop="url"
                    label="Url"
                    width="500">
            </el-table-column>
            <el-table-column
                    label="Options">
                <template slot-scope="scope">
                    <el-button
                            id="edit"
                            size="small"
                            @click="newOrEditBlogroll(scope.row.id, scope.row.name, scope.row.url)">Edit
                    </el-button>
                    <el-dialog title="New/Edit Blogroll" :visible.sync="dialogFormVisible">
                        <el-form :model="editBlogroll" label-position="left" :rules="rules">
                            <el-form-item label="Name" label-width="80px" prop="name">
                                <el-input v-model="editBlogroll.name" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="Url" label-width="80px" prop="url">
                                <el-input v-model="editBlogroll.url" autocomplete="off"></el-input>
                            </el-form-item>
                        </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button @click="dialogFormVisible = false">Cancel</el-button>
                            <el-button type="primary" @click="onSubmit">Submit</el-button>
                        </div>
                    </el-dialog>
                    <el-popconfirm title="Do you really want to delete this blogroll?"
                                   @onConfirm="handleDelete(scope.index, scope.row)"
                                   confirmButtonText="Confirm"
                                   cancelButtonText="Cancel">
                        <el-button
                                slot="reference"
                                size="small"
                                type="danger">Delete
                        </el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
</template>

<script>
    export default {
        name: "index",
        data() {
            return {
                blogrollSearch: '',
                blogrollList: null,
                dialogFormVisible: false,
                editBlogroll: {
                    name: '',
                    url: '',
                    id: null,
                },
                rules: {
                    url: [{
                        required: true,
                        message: 'Please enter a valid url',
                        trigger: 'blur',
                    }, {
                        pattern: /(https?|ftp|file):\/\/[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]/,
                        message: 'Please enter a valid url',
                        trigger: 'blur'
                    }],
                    name:[{
                        required: true,
                        message: 'Please enter a valid name',
                        trigger: 'blur',
                    }]
                }
            }
        },
        created() {
            this.getBlogrollList();
        },
        methods: {
            getBlogrollList() {
                this.$axios.get('api/blogroll/' + this.blogrollSearch).then(response => {
                    if (response.data.code === 200) {
                        this.blogrollList = response.data.data;
                    } else {
                        this.$message.error('Failed to get blogroll list. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get blogroll list. ' + e);
                })
            },
            newOrEditBlogroll(id, name, url) {
                this.editBlogroll.id = id;
                this.editBlogroll.name = name;
                this.editBlogroll.url = url;
                this.dialogFormVisible = true;
            },
            handleDelete(index, row) {
                this.$axios.delete('api/admin/blogroll/' + row.id).then(response => {
                    if (response.data.code === 200) {
                        this.$message.success('Succeed!');
                        this.getBlogrollList();
                    } else {
                        this.$message.error('Failed to delete the blogroll.' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to delete the blogroll.' + e)
                })
            },
            onSubmit() {
                this.$axios.post('api/admin/blogroll', this.editBlogroll).then(response => {
                    if (response.data.code === 200) {
                        this.$message.success('Succeed!');
                        this.getBlogrollList();
                    } else {
                        this.$message.error('Failed to submit blogroll. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to submit blogroll. ' + e);
                });
                this.dialogFormVisible = false;
            }
        },
    }
</script>

<style scoped>
    .el-button {
        margin-left: 20px;
    }

    .el-table {
        margin-top: 20px;
    }

    .el-input {
        width: 200px;
    }
</style>