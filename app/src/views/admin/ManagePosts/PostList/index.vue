<template>
    <el-card>
        <el-form ref="form" :model="query" label-width="80px">
            <el-row align="start">
                <el-col :span="8">
                    <div class="grid-content">
                        <el-form-item label="Search: ">
                            <el-input v-model="query.title" placeholder="Title or content"></el-input>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="6">
                    <div class="grid-content">
                        <el-form-item>
                            <category-selector @setCategoryId="setCategoryId"
                                               ref="categorySelector"></category-selector>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="10">
                    <div class="grid-content" id="buttons">
                        <el-button type="primary" @click="getBlogList">Search Blog</el-button>
                        <el-button type="warning" @click="clear">Clear</el-button>
                    </div>
                </el-col>
            </el-row>


        </el-form>

        <el-table
                :data="blogList"
                style="width: 100%"
                border
                class="blog-table">
            <el-table-column
                    label="ID"
                    width="100"
                    align="center"
                    class="blog-table-column">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.id }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="Title"
                    width="200"
                    align="center"
                    class="blog-table-column">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.title }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="Create Date"
                    width="180"
                    align="center"
                    class="blog-table-column">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="Update Date"
                    width="180"
                    align="center"
                    class="blog-table-column">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.updateTime }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="Category"
                    width="120"
                    align="center"
                    class="blog-table-column">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.category.name }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="Tags"
                    width="150"
                    align="center"
                    class="blog-table-column">
                <template slot-scope="scope">
                    <el-tag v-for="tag in scope.row.tags" :key="tag.id" class="tag-tag">{{tag.name}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                    label="Status"
                    width="120"
                    align="center"
                    class="blog-table-column">
                <template slot-scope="scope">
                    <draft v-if="scope.row.draft"></draft>
                    <posted v-else></posted>
                </template>
            </el-table-column>
            <el-table-column label="Options" align="center" class="blog-table-column">
                <template slot-scope="scope">
                    <router-link :to="'/admin/managePosts/edit/' + scope.row.id">
                        <el-button
                                id="edit"
                                size="small">Edit
                        </el-button>
                    </router-link>
                    <el-popconfirm title="Do you really want to delete this post?"
                                   @onConfirm="handleDelete(scope.index, scope.row)" confirmButtonText="Confirm"
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
    import CategorySelector from '@/components/CategorySelector/index'
    import Draft from "@/components/Draft";
    import Posted from "@/components/Posted";

    export default {
        name: "index",
        created() {
            this.getBlogList();
        },
        data() {
            return {
                query: {
                    title: '',
                    categoryId: -1,
                },
                totalElements: 0,
                blogList: [],
                categories: null,
                pageNumber: 0,
                pageSize: 10,
            }
        },
        methods: {
            getBlogList() {
                let url = 'api/admin/blog/list/' + this.pageSize + '/' + this.pageNumber;
                this.$axios.get(url, {
                    params: this.query
                },).then((response) => {
                    if (response.data.code === 200) {
                        this.blogList = response.data.data.content;
                        this.totalElements = response.data.data.totalElements;
                    } else {
                        this.$message.error('Failed to fetch blog list, please try again. ' + response.data.message);
                    }
                }).catch((e) => {
                    this.$message.error('Failed to fetch blog list, please try again. ' + e);
                });

                console.log(this.blogList);
            },
            setCategoryId(id) {
                this.query.categoryId = id;
            },
            handleEdit(index, row) {
                this.$router.push({
                    path: '/admin/myPosts/edit',
                    query: {
                        id: row.id
                    }
                })
            },
            handleDelete(index, row) {
                this.$axios.delete('api/admin/blog/' + row.id).then((response) => {
                    if (response.data.code === 200) {
                        this.$message.success('Succeed!')
                        this.getBlogList();
                    } else {
                        this.$message.error('Failed to delete the post. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to delete the post. ' + e);
                })
            },
            clear() {
                this.query.categoryId = -1;
                this.query.title = '';
                this.$refs.categorySelector.categoryId = null;
            },
            handleSizeChange(newSize) {
                this.pageSize = newSize;
                this.getBlogList();
            },
            handleCurrentChange(newCurrent) {
                this.pageNumber = newCurrent - 1;
                this.getBlogList();
            }
        },
        components: {
            CategorySelector, Draft, Posted
        }
    }
</script>

<style scoped>
    #buttons {
        padding-left: 15px;
    }

    .tag-tag {
        font-weight: 600;
        margin-bottom: 3px;
        margin-left: 2px;
        margin-right: 2px;
    }

    #edit {
        margin-right: 10px;
    }

</style>