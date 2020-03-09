<template>
    <el-card>
        <el-date-picker
                format="MM/dd/yyyy"
                v-model="startDate"
                type="date"
                placeholder="Select start date">
        </el-date-picker>
        <el-date-picker
                format="MM/dd/yyyy"
                v-model="endDate"
                type="date"
                placeholder="Select end date">
        </el-date-picker>
        <el-button type="primary" @click="getLogList">Search</el-button>
        <el-table
                :data="logList"
                border
                style="width: 100%">
            <el-table-column
                    prop="id"
                    label="ID"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="time"
                    label="Time">
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
                startDate: null,
                endDate: null,
                pageSize: 10,
                pageNumber: 0,
                totalElements: 0,
                logList: [],
            }
        },
        created() {
            this.getLogList();
        },
        methods: {
            getLogList() {
                let params = {};
                if (this.startDate != null) {
                    params.startYear = this.startDate.getFullYear();
                    params.startMonth = this.startDate.getMonth();
                    params.startDay = this.startDate.getDate();
                }
                if (this.endDate != null) {
                    params.endYear = this.endDate.getFullYear();
                    params.endMonth = this.endDate.getMonth();
                    params.endDay = this.endDate.getDate();
                }
                this.$axios.get('api/admin/visitLog/' + this.pageSize + '/' + this.pageNumber, {
                    params: params,
                }).then(response => {
                    if (response.data.code === 200) {
                        this.logList = response.data.data.content;
                        this.totalElements = response.data.data.totalElements;
                    } else {
                        this.$message.error("Failed getting visit logs, please try again later. " + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error("Failed getting visit logs, please try again later. " + e);
                });
            },
            handleDelete(index, row) {
                this.$axios.delete("api/admin/visitLog/" + row.id).then(response => {
                    if (response.data.code === 200) {
                        this.$message.success('Succeed!');
                        this.getLogList();
                    } else {
                        this.$message.error('Failed to delete the log, please try again later.' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to delete the log, please try again later.' + e);
                })
            },
            handleSizeChange(newSize) {
                this.pageSize = newSize;
                this.getLogList();
            },
            handleCurrentChange(newCurrent) {
                this.pageNumber = newCurrent - 1;
                this.getLogList();
            },
        }
    }
</script>

<style scoped>
    .el-date-editor {
        margin-right: 20px;
    }

    .el-table {
        margin-top: 30px;
    }
</style>