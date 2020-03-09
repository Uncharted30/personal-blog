<template>
    <el-card class="box-card">
        <el-input
                placeholder="Please enter keyword..."
                v-model="query.content"
                clearable>
        </el-input>
        <el-input
                placeholder="Please enter nickname"
                v-model="query.nickname"
                class="nicknameInput"
                clearable>
        </el-input>
        <el-button type="primary" icon="el-icon-search" @click="getCommentList">Search</el-button>
        <el-table
                :data="commentList"
                border
                style="width: 100%">
            <el-table-column
                    prop="id"
                    label="ID"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="blog.title"
                    label="Blog Title">
            </el-table-column>
            <el-table-column
                    prop="nickname"
                    label="Nickname">
            </el-table-column>
            <el-table-column
                    prop="content"
                    label="Content">
            </el-table-column>
            <el-table-column
                    prop="createTime"
                    label="Create Time">
            </el-table-column>
            <el-table-column
                    prop="device"
                    label="Device">
            </el-table-column>
            <el-table-column
                    prop="ip"
                    label="IP">
            </el-table-column>
            <el-table-column
                    prop="location"
                    label="Location">
            </el-table-column>
            <el-table-column
                    label="Options">
                <template slot-scope="scope">
                    <el-popconfirm title="Do you really want to delete this comment?"
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
        <br>
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNumber + 1"
                :page-sizes="[10, 20, 30]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="totalElements">
        </el-pagination>
    </el-card>
</template>

<script>
    export default {
        name: "index",
        data() {
            return {
                query: {
                    content: '',
                    nickname: '',
                },
                commentList: null,
                pageSize: 10,
                pageNumber: 0,
                totalElements: 0,
            }
        },
        created() {
            this.getCommentList();
        },
        methods: {
            getCommentList() {
                this.$axios.get('api/comment/list/' + this.pageSize + '/' + this.pageNumber, {
                    params: this.query
                }).then(response => {
                    if (response.data.code === 200) {
                        this.commentList = response.data.data.content;
                        this.totalElements = response.data.data.totalElements;
                    } else {
                        this.$message.error('Failed to get comment list. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get comment list. ' + e);
                })
            },
            handleDelete(index, row) {
                this.$axios.delete('api/admin/comment/' + row.id).then(response => {
                    if (response.data.code === 200) {
                        this.$message.success('Succeed!');
                        this.getCommentList();
                    } else {
                        this.$message.error('Failed to delete the comment.' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to delete the comment.' + e)
                })
            },
            handleSizeChange(newSize) {
                this.pageSize = newSize;
                this.getBlogList();
            },
            handleCurrentChange(newCurrent) {
                this.pageNumber = newCurrent - 1;
                this.getBlogList();
            },
        },
        components: {}
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

    .nicknameInput {
        margin-left: 20px;
    }
</style>