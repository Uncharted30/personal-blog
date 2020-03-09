<template>
    <el-table
            :data="tableData"
            border
            style="width: 100%">
        <el-table-column
                prop="title"
                label="Title"
                width="200">
        </el-table-column>
        <el-table-column
                prop="createTime"
                label="Create Date"
                width="180">
        </el-table-column>
        <el-table-column
                prop="views"
                label="Views"
                width="60">
        </el-table-column>
        <el-table-column
                prop="status"
                label="Status">
            <template slot-scope="scope">
                <div v-if="scope.row.draft">
                    <draft></draft>
                </div>
                <div v-else>
                    <posted></posted>
                </div>
            </template>
        </el-table-column>
    </el-table>
</template>

<script>
    import Posted from "@/components/Posted";
    import Draft from "@/components/Draft";

    export default {
        name: "RecentPosts",
        components: {
            Posted,
            Draft
        },
        created() {
            this.getRecentBlog();
        },
        data() {
            return {
                tableData: []
            }
        },
        methods: {
            filterTag(value, row) {
                return row.status === true;
            },
            getRecentBlog() {
                this.$axios.get('api/admin/blog/list/5/0').then(response => {
                    if (response.data.code === 200) {
                        this.tableData = response.data.data.content;
                    } else {
                        this.$message.error('Failed to load recent blog list, please try again.' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to load recent blog list, please try again.' + e);
                })
            }
        }
    }
</script>

<style scoped>

</style>