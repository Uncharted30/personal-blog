<template>
    <el-table
            :data="tableData"
            border
            style="width: 100%">
        <el-table-column
                prop="nickname"
                label="Nickname"
                width="90">
        </el-table-column>
        <el-table-column
                prop="content"
                label="Content"
                width="230">
        </el-table-column>
        <el-table-column
                prop="createTime"
                label="Time">
        </el-table-column>
    </el-table>
</template>

<script>
    export default {
        data() {
            return {
                tableData: []
            }
        },
        created() {
            this.getRecentComments();
        },
        methods: {
            getRecentComments() {
                this.$axios.get('api/comment/list/5/0').then(response => {
                    if (response.data.code === 200) {
                        this.tableData = response.data.data.content;
                    } else {
                        this.$message.error('Failed to load recent comments, please try again.' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to load recent comments, please try again.' + e);
                })
            }
        }
    }
</script>

<style scoped>
</style>