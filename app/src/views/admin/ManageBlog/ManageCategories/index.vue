<template>
    <el-card class="box-card">
        <el-input
                placeholder="Please enter keyword..."
                v-model="categorySearch"
                clearable>
        </el-input>
        <el-button type="primary" icon="el-icon-search" @click="getCategoryList">Search</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="newOrEditCategory(null, '')">New</el-button>
        <el-table
                :data="categoryList"
                border
                style="width: 100%">
            <el-table-column
                    prop="id"
                    label="ID"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="Category Name">
            </el-table-column>
            <el-table-column
                    label="Options">
                <template slot-scope="scope">
                    <el-button
                            id="edit"
                            size="small"
                            @click="newOrEditCategory(scope.row.id, scope.row.name)">Edit
                    </el-button>
                    <el-popconfirm title="Do you really want to delete this category?"
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
                categorySearch: '',
                categoryList: null,
                pageSize: 10,
                pageNumber: 0,
                totalElements: 0,
            }
        },
        created() {
            this.getCategoryList();
        },
        methods: {
            onChange() {
            },
            getCategoryList() {
                console.log('api/categories/list/' + this.pageSize + '/' + this.pageNumber + '/' + this.categorySearch);
                this.$axios.get('api/categories/list/' + this.pageSize + '/' + this.pageNumber + '/' + this.categorySearch).then(response => {
                    if (response.data.code === 200) {
                        this.categoryList = response.data.data.content;
                        this.totalElements = response.data.data.totalElements
                    } else {
                        this.$message.error('Failed to get category list. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get category list. ' + e);
                })
            },
            newOrEditCategory(id, oldVal) {
                this.$prompt('Please enter name of the category', 'New/Edit Category', {
                    confirmButtonText: 'Submit',
                    cancelButtonText: 'Cancel',
                    inputPattern: /./,
                    inputErrorMessage: 'Please enter at least one letter',
                    inputValue: oldVal,
                }).then(({value}) => {
                    this.$axios.post('api/admin/category', {
                        name: value,
                        id: id
                    }).then((response) => {
                        if (response.data.code === 200) {
                            this.$message.success('Succeed!');
                            this.getCategoryList();
                        } else {
                            this.$message.success('Failed to submit category. ' + response.data.message);
                        }
                    }).catch(e => {
                        this.$message.success('Failed to submit category. ' + e);
                    });
                }).catch(() => {
                    console.log('cancel')
                })
            },
            handleDelete(index, row) {
                this.$axios.delete('api/admin/category/' + row.id).then(response => {
                    if (response.data.code === 200) {
                        this.$message.success('Succeed!');
                        this.getCategoryList();
                    } else {
                        this.$message.error('Failed to delete the category.' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to delete the category.' + e)
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