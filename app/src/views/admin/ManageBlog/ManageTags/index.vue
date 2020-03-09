<template>
    <el-card class="box-card">
        <el-input
                placeholder="Please enter keyword..."
                v-model="tagSearch"
                clearable>
        </el-input>
        <el-button type="primary" icon="el-icon-search" @click="getTagList">Search</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="newOrEditTag(null, '')">New</el-button>
        <el-table
                :data="tagList"
                border
                style="width: 100%">
            <el-table-column
                    prop="id"
                    label="ID"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="Tag Name">
            </el-table-column>
            <el-table-column
                    label="Options">
                <template slot-scope="scope">
                    <el-button
                            id="edit"
                            size="small"
                            @click="newOrEditTag(scope.row.id, scope.row.name)">Edit
                    </el-button>
                    <el-popconfirm title="Do you really want to delete this tag?"
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
                tagSearch: '',
                tagList: null,
                pageSize: 10,
                pageNumber: 0,
                totalElements: 0,
            }
        },
        created() {
            this.getTagList();
        },
        methods: {
            onChange() {
            },
            getTagList() {
                console.log('api/tags/list/' + this.pageSize + '/' + this.pageNumber + '/' + this.tagSearch);
                this.$axios.get('api/tags/list/' + this.pageSize + '/' + this.pageNumber + '/' + this.tagSearch).then(response => {
                    if (response.data.code === 200) {
                        this.tagList = response.data.data.content;
                        this.totalElements = response.data.data.totalElements
                    } else {
                        this.$message.error('Failed to get tag list. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get tag list. ' + e);
                })
            },
            newOrEditTag(id, oldVal) {
                this.$prompt('Please enter name of the tag', 'New/Edit Tag', {
                    confirmButtonText: 'Submit',
                    cancelButtonText: 'Cancel',
                    inputPattern: /./,
                    inputErrorMessage: 'Please enter at least one letter',
                    inputValue: oldVal,
                }).then(({value}) => {
                    this.$axios.post('api/admin/tags', {
                        name: value,
                        id: id
                    }).then((response) => {
                        if (response.data.code === 200) {
                            this.$message.success('Succeed!');
                            this.getTagList();
                        } else {
                            this.$message.success('Failed to submit tag. ' + response.data.message);
                        }
                    }).catch(e => {
                        this.$message.success('Failed to submit tag. ' + e);
                    });
                }).catch(() => {
                    console.log('cancel')
                })
            },
            handleDelete(index, row) {
                this.$axios.delete('api/admin/tags/' + row.id).then(response => {
                    if (response.data.code === 200) {
                        this.$message.success('Succeed!');
                        this.getTagList();
                    } else {
                        this.$message.error('Failed to delete the tag.' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to delete the tag.' + e)
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
</style>